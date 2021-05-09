package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.dto.LancamentoDTO;
import com.br.gutjahr.despesas.model.*;
import com.br.gutjahr.despesas.repositories.*;
import com.br.gutjahr.despesas.security.UserSS;
import com.br.gutjahr.despesas.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private DuplicataRepository duplicataRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Lancamento> findAll(){
        UserSS userSS = UserService.authencated();
        if(userSS == null) {
            throw new ArithmeticException("Acesso negado");
        }
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
        Optional<List<Lancamento>> lancamentos = lancamentoRepository.findByUsuario(usuario);
        return lancamentos.get();
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
        if(lancamento.getQtdParcelas() > 1){
            lancamento.setValor(lancamento.getValor()/lancamento.getQtdParcelas());
            for(int i=0;i<lancamento.getQtdParcelas();i++){
                // cada parcela ficará em um mês
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(lancamento.getData());
                calendar.add(Calendar.MONTH, i);
                Lancamento lancamento1 = new Lancamento(null, calendar.getTime(), lancamento.getValor(),
                        lancamento.getHistorico(), lancamento.getPlano_credito(), lancamento.getPlano_debito(),
                        lancamento.getCredito(), lancamento.getFaturado(), lancamento.getQtdParcelas(),
                        lancamento.getDuplicata(), lancamento.getPessoa());
                lancamento1.setUsuario(usuario);
                lancamentoRepository.save(lancamento1);
            }
        } else {
            lancamentoRepository.save(lancamento);
        }
        return lancamento;
    }

    public Lancamento fromDTO(LancamentoDTO lancamentoDTO){
        Plano planoCred = planoRepository.getOne(lancamentoDTO.getPlanoCreditoId());
        Plano planoDeb = planoRepository.getOne(lancamentoDTO.getPlanoDebitoId());
        Duplicata duplicata;
        if(planoCred == null) throw new ObjectNotFoundException("Conta de crédito não encontrada");
        if(planoDeb == null) throw new ObjectNotFoundException("Conta de débito não encontrada");

        if(lancamentoDTO.getDuplicataId() != null) {
            duplicata = duplicataRepository.getOne(lancamentoDTO.getDuplicataId());
        } else {
            duplicata = null;
        }

        UserSS userSS = UserService.authencated();
        if(userSS == null) throw new ArrayStoreException("Ocorreu um erro ao obter o código do usuário");
        Usuario usuario = usuarioRepository.getOne(userSS.getId());

        Optional<Pessoa> pessoa = null;

        // se for informado o código da pessoa, o registro é buscado para ser vinculado a duplicata
        if(lancamentoDTO.getPessoaId() != null){
            Pessoa pessoa1 = pessoaRepository.getOne(lancamentoDTO.getPessoaId());
            pessoa.get().setNome(pessoa1.getNome());
            pessoa.get().setId(pessoa1.getId());
            pessoa.get().setUsuario(pessoa1.getUsuario());
        } else {
            // se for informado somente o nome, é feita a busca pelo nome
            pessoa = pessoaRepository.findFirst1ByNomeAndUsuario(lancamentoDTO.getNomePessoa(), usuario);

            // caso a pessoa não esteja cadastrada, é feito o cadastro
            if(pessoa == null) {
                Pessoa pessoa1 = new Pessoa(null, lancamentoDTO.getNomePessoa());
                pessoa1.setUsuario(usuario);
                pessoa1 = pessoaRepository.save(pessoa1);
                pessoa.get().setId(pessoa1.getId());
                pessoa.get().setNome(pessoa1.getNome());
                pessoa.get().setUsuario(pessoa1.getUsuario());
            }
        }

        return new Lancamento(lancamentoDTO.getId(), lancamentoDTO.getData(), lancamentoDTO.getValor(),
                lancamentoDTO.getHistorico(), planoCred, planoDeb, lancamentoDTO.getCredito(), false,
                lancamentoDTO.getQtdParcelas(), duplicata, pessoa.get());
    }
}
