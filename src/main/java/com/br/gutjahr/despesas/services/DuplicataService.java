package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.dto.DuplicataDTO;
import com.br.gutjahr.despesas.model.*;
import com.br.gutjahr.despesas.repositories.*;
import com.br.gutjahr.despesas.services.exceptions.DataIntegrityExeption;
import com.br.gutjahr.despesas.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DuplicataService {

    @Autowired
    private UserService userService;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private DuplicataRepository duplicataRepository;

    @Autowired
    private PortadorRepository portadorRepository;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public List<Duplicata> findAll(){
        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        Optional<List<Duplicata>> duplicatas = Optional.ofNullable(duplicataRepository.findByUsuario(usuario.get()).get());
        return duplicatas.get();
    }

    public List<Duplicata> findByFilters(String dataInicial, String dataFinal, Boolean receber, Boolean pagar){
        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        Date dataI = new Date();
        Date dataF = new Date();
        try {
            dataI = new SimpleDateFormat("yyyy-MM-dd").parse(dataInicial);
            dataF = new SimpleDateFormat("yyyy-MM-dd").parse(dataFinal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(dataI == null) dataI = new Date(System.currentTimeMillis());
        if(dataF == null) dataF = new Date(System.currentTimeMillis());

        // a lista precisa conter algum valor inicialmente para poder utilizar o get
        Optional<List<Duplicata>> duplicatas = Optional.empty();
        if(receber && pagar) {
            duplicatas = duplicataRepository.findByDataInclusaoBetweenAndSaldoNotAndUsuarioOrderByDataVencimento(dataI, dataF, 0.0, usuario.get());
        } else if (receber) {
            duplicatas = duplicataRepository.findByDataInclusaoBetweenAndSaldoNotAndReceberTrueAndUsuarioOrderByDataVencimento(dataI, dataF, 0.0, usuario.get());
        } else if (pagar) {
            duplicatas = duplicataRepository.findByDataInclusaoBetweenAndSaldoNotAndReceberFalseAndUsuarioOrderByDataVencimento(dataI, dataF, 0.0, usuario.get());
        }
        return duplicatas.get();
    }


    public Duplicata insert(Duplicata duplicata) {
        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        duplicata.setId(null);
        duplicata.setUsuario(usuario.get());
        duplicataRepository.save(duplicata);
        return duplicata;
    }

    public Duplicata update(Duplicata duplicata){
        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        duplicata.setUsuario(usuario.get());
        Optional<Duplicata> duplicataAtual = Optional.ofNullable(duplicataRepository.getOne(duplicata.getId()));

        // valida se a duplicata possí baixas e se está sendo alterado o valor
        if(!lancamentoRepository.findByDuplicata(duplicataAtual.get()).get().isEmpty()
                && duplicata.getValor() != duplicataAtual.get().getValor())
            throw new DataIntegrityExeption("A duplicata possuí baixas, exclua as baixas antes de alterar " +
                    "o valor");

        return duplicataRepository.save(duplicata);
    }

    public void delete(Integer id){
        try {
            Optional<Duplicata> duplicata = Optional.ofNullable(duplicataRepository.getOne(id));
            duplicataRepository.delete(duplicata.get());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityExeption("Ocorreu um erro no servidor");
        }
    }


    public Duplicata fromDTO(DuplicataDTO duplicataDTO){
        Portador portador = portadorRepository.getOne(duplicataDTO.getPortadorId());
        Plano plano = planoRepository.getOne(duplicataDTO.getPlanoId());
        if(portador == null) throw new ObjectNotFoundException("Portador não encontrado");
        if(plano == null) throw new ObjectNotFoundException("Conta não encontrada");

        Pessoa pessoa = pessoaService.findPessoa(duplicataDTO.getPessoaNome(), duplicataDTO.getPessoaId());

        return new Duplicata(duplicataDTO.getId(), duplicataDTO.getDataInclusao(), duplicataDTO.getDataVencimento(),
                duplicataDTO.getValor(), duplicataDTO.getObservacao(), duplicataDTO.getReceber(), portador, plano,
                duplicataDTO.getValor(), pessoa);
    }
}
