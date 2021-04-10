package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.model.PlanoSaldo;
import com.br.gutjahr.despesas.model.Portador;
import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.repositories.PlanoSaldoRepository;
import com.br.gutjahr.despesas.repositories.PortadorRepository;
import com.br.gutjahr.despesas.repositories.UsuarioRepository;
import com.br.gutjahr.despesas.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PortadorService {

    @Autowired
    private PortadorRepository portadorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanoSaldoRepository planoSaldoRepository;

    public List<Portador> findAll(){
        UserSS userSS = UserService.authencated();
        if (userSS == null){
            throw new ArithmeticException("Acesso negado");
        }
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
        List<Portador> portadores = portadorRepository.findByUsuario(usuario);

        /* percorre todos os portadores e adicionao o saldo da data atual a conta do portador */
        for (Portador portador : portadores) {
            Date dataAtual = new Date(System.currentTimeMillis());
            Plano plano = portador.getPlano();

            Optional<PlanoSaldo> planoSaldo = planoSaldoRepository
                    .findByDataAndPlanoId(dataAtual, portador.getPlano().getId());

            // se o plano saldo na data de hoje for nulo, busca o registro com a maior data
            if(planoSaldo.isEmpty()){
                planoSaldo = planoSaldoRepository
                        .findTopByDataBeforeAndPlanoIdOrderByDataDesc(dataAtual, portador.getPlano().getId());
                if(!planoSaldo.isEmpty()){
                    plano.setSaldoAtual(planoSaldo.get().getSaldo());
                }
            }

            // se o plano saldo continuar nullo, atribui 0
            if(plano.getSaldoAtual() == null){
                plano.setSaldoAtual(0.0);
            }
            portador.setPlano(plano);
        }
        return portadores;
    }
}
