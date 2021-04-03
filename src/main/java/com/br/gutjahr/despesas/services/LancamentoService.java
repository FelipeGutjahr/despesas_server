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
        System.out.println(lancamento.getData());
        lancamentoRepository.save(lancamento);
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
        return new Lancamento(lancamentoDTO.getId(), lancamentoDTO.getData(), lancamentoDTO.getValor(),
                lancamentoDTO.getHistorico(), planoCred, planoDeb);
    }
}
