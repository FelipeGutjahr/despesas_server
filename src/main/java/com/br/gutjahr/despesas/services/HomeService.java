package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.model.*;
import com.br.gutjahr.despesas.repositories.LancamentoRepository;
import com.br.gutjahr.despesas.repositories.PlanoSaldoRepository;
import com.br.gutjahr.despesas.repositories.PortadorRepository;
import com.br.gutjahr.despesas.repositories.UsuarioRepository;
import com.br.gutjahr.despesas.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HomeService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PortadorRepository portadorRepository;

    @Autowired
    private PlanoSaldoRepository planoSaldoRepository;

    public List<ItemCard> findTotalPago(){
        UserSS userSS = UserService.authencated();
        if(userSS == null){
            throw new ArithmeticException("Acesso negado");
        }
        Usuario usuario = usuarioRepository.getOne(userSS.getId());

        List<ItemCard> itemCardList = new ArrayList<>();

        // busca as contas que tem vinculo com portador, que podem ser usadas como despesa
        List<Portador> portadores = portadorRepository.findByUsuario(usuario);
        List<Plano> planos = new ArrayList<>();
        portadores.forEach(portador -> planos.add(portador.getPlano()));
        List<Lancamento> lancamentos;
        Double total = 0.0;
        ItemCard itemCard;
        Date dataAtual = new Date(System.currentTimeMillis());

        // busca do gasto na data atual
        lancamentos = lancamentoRepository.findByPlanoCreditoInAndDataAndPlanoDebitoNotIn(planos, dataAtual, planos);
        for (Lancamento lancamento : lancamentos) {
            total = total + lancamento.getValor();
        }
        itemCard = new ItemCard("Hoje", total);
        itemCardList.add(itemCard);
        total = 0.0;

        // busca o gasto no mês atual
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAtual);

        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date primeiroDiaMes = calendar.getTime();

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date ultimoDiaMes = calendar.getTime();

        lancamentos = lancamentoRepository.findByPlanoCreditoInAndDataBetweenAndPlanoDebitoNotIn(planos, primeiroDiaMes, ultimoDiaMes, planos);
        for (Lancamento lancamento : lancamentos) {
            total = total + lancamento.getValor();
        }
        itemCard = new ItemCard("Este mês", total);
        itemCardList.add(itemCard);

        return itemCardList;
    }

    public List<ItemCard> findTotalRecebido(){
        UserSS userSS = UserService.authencated();
        if(userSS == null){
            throw new ArithmeticException("Acesso negado");
        }
        Usuario usuario = usuarioRepository.getOne(userSS.getId());

        List<ItemCard> itemCardList = new ArrayList<>();

        // busca as contas que tem vinculo com portador, que podem ser usadas como receita
        List<Portador> portadores = portadorRepository.findByUsuario(usuario);
        List<Plano> planos = new ArrayList<>();
        portadores.forEach(portador -> planos.add(portador.getPlano()));
        List<Lancamento> lancamentos;
        Double total = 0.0;
        ItemCard itemCard;
        Date dataAtual = new Date(System.currentTimeMillis());

        // busca as receitas na data atual
        lancamentos = lancamentoRepository.findByPlanoDebitoInAndDataAndPlanoCreditoNotIn(planos, dataAtual, planos);
        for (Lancamento lancamento : lancamentos) {
            total = total + lancamento.getValor();
        }
        itemCard = new ItemCard("Hoje", total);
        itemCardList.add(itemCard);
        total = 0.0;

        // busca o gasto no mês atual
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAtual);

        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date primeiroDiaMes = calendar.getTime();

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date ultimoDiaMes = calendar.getTime();

        lancamentos = lancamentoRepository.findByPlanoDebitoInAndDataBetweenAndPlanoCreditoNotIn(planos, primeiroDiaMes, ultimoDiaMes, planos);
        for (Lancamento lancamento : lancamentos) {
            total = total + lancamento.getValor();
        }
        itemCard = new ItemCard("Este mês", total);
        itemCardList.add(itemCard);

        return itemCardList;
    }

    public List<ItemCard> findSaldoPortadores(){
        UserSS userSS = UserService.authencated();
        if(userSS == null){
            throw new ArithmeticException("Acesso negado");
        }
        Usuario usuario = usuarioRepository.getOne(userSS.getId());

        List<ItemCard> itemCardList = new ArrayList<>();

        List<Portador> portadores = portadorRepository.findByUsuario(usuario);

        for (Portador portador : portadores) {
            Date dataAtual = new Date(System.currentTimeMillis());

            ItemCard itemCard = new ItemCard(portador.getNome(), 0.0);

            Optional<PlanoSaldo> planoSaldo = planoSaldoRepository
                    .findByDataAndPlanoId(dataAtual, portador.getPlano().getId());

            // se o plano saldo na data de hoje for nulo, busca o registro com a maior data
            if(planoSaldo.isEmpty()){
                planoSaldo = planoSaldoRepository
                        .findTopByDataBeforeAndPlanoIdOrderByDataDesc(dataAtual, portador.getPlano().getId());
                if(!planoSaldo.isEmpty()){
                    itemCard.setValue(planoSaldo.get().getSaldo());
                }
            } else {
                itemCard.setValue(planoSaldo.get().getSaldo());
            }
            itemCardList.add(itemCard);
        }

        return itemCardList;
    }
}
