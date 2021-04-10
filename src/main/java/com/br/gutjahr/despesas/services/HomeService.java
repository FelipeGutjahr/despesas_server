package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.model.*;
import com.br.gutjahr.despesas.repositories.LancamentoRepository;
import com.br.gutjahr.despesas.repositories.PortadorRepository;
import com.br.gutjahr.despesas.repositories.UsuarioRepository;
import com.br.gutjahr.despesas.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class HomeService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PortadorRepository portadorRepository;

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
        ItemCard itemCard = new ItemCard();

        // busca do gasto na data atual
        Date dataAtual = new Date(System.currentTimeMillis());
        lancamentos = lancamentoRepository.findByPlanoCreditoInAndData(planos, dataAtual);
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

        lancamentos = lancamentoRepository.findByPlanoCreditoInAndDataBetween(planos, primeiroDiaMes, ultimoDiaMes);
        for (Lancamento lancamento : lancamentos) {
            total = total + lancamento.getValor();
        }
        itemCard = new ItemCard("Este mês", total);
        itemCardList.add(itemCard);

        return itemCardList;
    }
}
