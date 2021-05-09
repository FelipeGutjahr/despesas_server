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

    Calendar calendar = Calendar.getInstance();

    public List<ItemCard> findTotalPago(){
        Usuario usuario = findUsuario();

        List<Plano> planos = findPlanoFromPortador(usuario);
        Date dataAtual = new Date(System.currentTimeMillis());

        Optional<List<Lancamento>> lancamentos = lancamentoRepository.findByPlanoCreditoInAndDataAndPlanoDebitoNotInAndUsuario(
                planos, dataAtual, planos, usuario);

        List<ItemCard> itemCardList = new ArrayList<>();

        itemCardList.add(generateItemCard(lancamentos.get(), "Hoje"));

        calendar.setTime(dataAtual);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date primeiroDiaMes = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date ultimoDiaMes = calendar.getTime();

        lancamentos = lancamentoRepository.findByPlanoCreditoInAndDataBetweenAndPlanoDebitoNotInAndUsuario(
                planos, primeiroDiaMes, ultimoDiaMes, planos, usuario);
        itemCardList.add(generateItemCard(lancamentos.get(), "Este mês"));

        return itemCardList;
    }

    public List<ItemCard> findTotalRecebido(){
        Usuario usuario = findUsuario();

        List<Plano> planos = findPlanoFromPortador(usuario);
        Date dataAtual = new Date(System.currentTimeMillis());

        Optional<List<Lancamento>> lancamentos = lancamentoRepository.findByPlanoDebitoInAndDataAndPlanoCreditoNotInAndUsuario(
                planos, dataAtual, planos, usuario);

        List<ItemCard> itemCardList = new ArrayList<>();

        itemCardList.add(generateItemCard(lancamentos.get(), "Hoje"));

        calendar.setTime(dataAtual);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date primeiroDiaMes = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date ultimoDiaMes = calendar.getTime();

        lancamentos = lancamentoRepository.findByPlanoDebitoInAndDataBetweenAndPlanoCreditoNotInAndUsuario(
                planos, primeiroDiaMes, ultimoDiaMes, planos, usuario);

        itemCardList.add(generateItemCard(lancamentos.get(), "Este mês"));

        return itemCardList;
    }

    public List<ItemCard> findSaldoPortadores(){
        Usuario usuario = findUsuario();

        Optional<List<Portador>> portadores = portadorRepository.findByUsuario(usuario);

        List<ItemCard> itemCardList = new ArrayList<>();

        for (Portador portador : portadores.get()) {
            Date dataAtual = new Date(System.currentTimeMillis());

            ItemCard itemCard = new ItemCard(portador.getNome(), 0.0);

            Optional<PlanoSaldo> planoSaldo = planoSaldoRepository
                    .findByDataAndPlanoId(dataAtual, portador.getPlano().getId());

            // se o plano saldo na data de hoje for nulo, busca o registro com a maior data
            if(!planoSaldo.isPresent()){
                planoSaldo = planoSaldoRepository
                        .findTopByDataBeforeAndPlanoIdOrderByDataDesc(dataAtual, portador.getPlano().getId());
                if(planoSaldo.isPresent()){
                    itemCard.setValue(planoSaldo.get().getSaldo());
                }
            } else {
                itemCard.setValue(planoSaldo.get().getSaldo());
            }
            itemCardList.add(itemCard);
        }

        return itemCardList;
    }

    private List<Plano> findPlanoFromPortador(Usuario usuario){
        Optional<List<Portador>> portadores = portadorRepository.findByUsuario(usuario);
        List<Plano> planos = new ArrayList<>();
        portadores.get().forEach(portador -> planos.add(portador.getPlano()));
        return planos;
    }

    private ItemCard generateItemCard(List<Lancamento> lancamentos, String title){
        Double total = 0.0;
        for (Lancamento lancamento : lancamentos) {
            total = total + lancamento.getValor();
        }
        return new ItemCard(title, total);
    }

    private Usuario findUsuario(){
        UserSS userSS = UserService.authencated();
        if(userSS == null){
            throw new ArithmeticException("Acesso negado");
        }
        return usuarioRepository.getOne(userSS.getId());
    }
}
