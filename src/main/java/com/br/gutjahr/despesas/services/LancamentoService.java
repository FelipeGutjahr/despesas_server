package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.dto.LancamentoDTO;
import com.br.gutjahr.despesas.model.Lancamento;
import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.repositories.LancamentoRepository;
import com.br.gutjahr.despesas.repositories.PlanoRepository;
import com.br.gutjahr.despesas.repositories.UsuarioRepository;
import com.br.gutjahr.despesas.security.UserSS;
import com.br.gutjahr.despesas.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanoRepository planoRepository;

    public List<Lancamento> findAll(){
        UserSS userSS = UserService.authencated();
        if(userSS == null) {
            throw new ArithmeticException("Acesso negado");
        }
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
        List<Lancamento> lancamentos = lancamentoRepository.findByUsuario(usuario);
        return lancamentos;
    }

    public Lancamento insert(Lancamento lancamento){
        UserSS userSS = UserService.authencated();
        if(userSS == null){
            throw new ArrayStoreException("Ocorreu um erro ao obter o código do usuário");
        }
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
        lancamento.setId(null);
        lancamento.setUsuario(usuario);
        // se a quantidade de parcelas for maior que 1, serão gerados lançamentos de acordo com as parcelas
        if(lancamento.getQtd_parcelas() > 1){
            lancamento.setValor(lancamento.getValor()/lancamento.getQtd_parcelas());
            for(int i=0;i<lancamento.getQtd_parcelas();i++){
                // cada parcela ficará em um mês
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(lancamento.getData());
                calendar.add(Calendar.MONTH, i);
                Lancamento lancamento1 = new Lancamento(null, calendar.getTime(), lancamento.getValor(),
                        lancamento.getHistorico(), lancamento.getPlano_credito(), lancamento.getPlano_debito(),
                        lancamento.getCredito(), lancamento.getFaturado(), lancamento.getQtd_parcelas());
                lancamento1.setUsuario(usuario);
                lancamentoRepository.save(lancamento1);
            }
        } else {
            lancamentoRepository.save(lancamento);
        }
        return lancamento;
    }

    public Lancamento fromDTO(LancamentoDTO lancamentoDTO){
        Plano planoCred = planoRepository.getOne(lancamentoDTO.getPlano_credito_id());
        Plano planoDeb = planoRepository.getOne(lancamentoDTO.getPlano_debito_id());
        if(planoCred == null){
            throw new ObjectNotFoundException("Conta de crédito não encontrada");
        }
        if(planoDeb == null){
            throw new ObjectNotFoundException("Conta de débito não encontrada");
        }

        /* ao salvar o lançamento, a data estava atrasando em um dia, não foi encontrada uma forma de ajustar
        * o erro, então está sendo adicionado um dia antes de salvar o lançamento, mais tarde o problema pode ser
        * analisado novamente para encontrar uma lolução mais amigável */
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lancamentoDTO.getData());
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return new Lancamento(lancamentoDTO.getId(), calendar.getTime(), lancamentoDTO.getValor(),
                lancamentoDTO.getHistorico(), planoCred, planoDeb, lancamentoDTO.getIs_credito(), false,
                lancamentoDTO.getQtd_parcelas());
    }
}
