package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.dto.LancamentoDTO;
import com.br.gutjahr.despesas.model.*;
import com.br.gutjahr.despesas.repositories.*;
import com.br.gutjahr.despesas.services.exceptions.DataIntegrityExeption;
import com.br.gutjahr.despesas.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private UserService userService;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private DuplicataRepository duplicataRepository;

    public List<Lancamento> findAll(){
        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        Optional<List<Lancamento>> lancamentos = Optional.ofNullable(lancamentoRepository
                .findByUsuario(usuario.get()).get());
        return lancamentos.get();
    }

    public List<Lancamento> findBetweenLancamentos(String dataInicial, String dataFinal){
        Date dataI = new Date();
        Date dataF = new Date();
        try {
            dataI = new SimpleDateFormat("yyyy-MM-dd").parse(dataInicial);
            dataF = new SimpleDateFormat("yyyy-MM-dd").parse(dataFinal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        Optional<List<Lancamento>> lancamentos = Optional.ofNullable(lancamentoRepository
                .findByDataBetweenAndUsuario(dataI, dataF, usuario.get())).get();
        return lancamentos.get();
    }

    public List<Lancamento> findBetweenLancamentosAndPlanoId(String dataInicial, String dataFinal, Integer planoId){
        Date dataI = new Date();
        Date dataF = new Date();
        try {
            dataI = new SimpleDateFormat("yyyy-MM-dd").parse(dataInicial);
            dataF = new SimpleDateFormat("yyyy-MM-dd").parse(dataFinal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Optional<Plano> plano = Optional.ofNullable(planoRepository.getOne(planoId));

        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        Optional<List<Lancamento>> lancamentos = Optional.ofNullable(lancamentoRepository
                .findByDataBetweenAndUsuarioAndPlanoCredito(dataI, dataF, usuario.get(), plano.get()).get());

        lancamentos.get().addAll(Optional.ofNullable(lancamentoRepository
                .findByDataBetweenAndUsuarioAndPlanoDebito(dataI, dataF, usuario.get(), plano.get()).get()).get());

        return lancamentos.get();
    }

    public Lancamento insert(Lancamento lancamento){
        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lancamento.getData());
        lancamento.setId(null);
        lancamento.setUsuario(usuario.get());
        // se a quantidade de parcelas for maior que 1, serão gerados lançamentos de acordo com as parcelas
        if(lancamento.getQtdParcelas() > 1){
            lancamento.setValor(lancamento.getValor()/lancamento.getQtdParcelas());
            for(int i=0;i<lancamento.getQtdParcelas();i++){
                // cada parcela ficará em um mês
                calendar.add(Calendar.MONTH, i);
                Lancamento lancamento1 = new Lancamento(null, calendar.getTime(), lancamento.getValor(),
                        lancamento.getHistorico(), lancamento.getPlanoCredito(), lancamento.getPlanoDebito(),
                        lancamento.getCredito(), lancamento.getFaturado(), lancamento.getQtdParcelas(),
                        lancamento.getDuplicata(), lancamento.getPessoa(), calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.YEAR));
                lancamento1.setUsuario(usuario.get());
                lancamentoRepository.save(lancamento1);
            }
        } else {
            lancamento.setMesLancamento(calendar.get(Calendar.MONTH) + 1);
            lancamento.setAnoLancamento(calendar.get(Calendar.YEAR));
            lancamentoRepository.save(lancamento);
        }
        return lancamento;
    }

    public void delete(Integer id){
        try {
            Optional<Lancamento> lancamento = Optional.ofNullable(lancamentoRepository.getOne(id));
            lancamentoRepository.delete(lancamento.get());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityExeption("Ocorreu um erro no servidor");
        }
    }

    public Lancamento fromDTO(LancamentoDTO lancamentoDTO){
        Optional<Plano> planoCred = Optional.ofNullable(planoRepository.getOne(lancamentoDTO.getPlanoCreditoId()));
        Optional<Plano> planoDeb = Optional.ofNullable(planoRepository.getOne(lancamentoDTO.getPlanoDebitoId()));

        if(!planoCred.isPresent()) throw new ObjectNotFoundException("Conta de crédito não encontrada");
        if(!planoDeb.isPresent()) throw new ObjectNotFoundException("Conta de débito não encontrada");

        Optional<Duplicata> duplicata = Optional.empty();
        if(lancamentoDTO.getDuplicataId() != null) {
            duplicata = Optional.ofNullable(duplicataRepository.getOne(lancamentoDTO.getDuplicataId()));
        }

        Pessoa pessoa = pessoaService.findPessoa(lancamentoDTO.getNomePessoa(), lancamentoDTO.getPessoaId());

        return new Lancamento(lancamentoDTO.getId(), lancamentoDTO.getData(), lancamentoDTO.getValor(),
                lancamentoDTO.getHistorico(), planoCred.get(), planoDeb.get(), lancamentoDTO.getCredito(), false,
                lancamentoDTO.getQtdParcelas(), duplicata.orElse(null), pessoa, null,
                null);
    }
}
