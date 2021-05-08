package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.dto.DuplicataDTO;
import com.br.gutjahr.despesas.model.*;
import com.br.gutjahr.despesas.repositories.*;
import com.br.gutjahr.despesas.security.UserSS;
import com.br.gutjahr.despesas.services.exceptions.DataIntegrityExeption;
import com.br.gutjahr.despesas.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DuplicataService {

    @Autowired
    private DuplicataRepository duplicataRepository;

    @Autowired
    private PortadorRepository portadorRepository;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public List<Duplicata> findAll(){
        UserSS userSS = UserService.authencated();
        if(userSS == null) throw new ArrayStoreException("Acesso negado");
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
        List<Duplicata> duplicatas = duplicataRepository.findByUsuario(usuario);
        return duplicatas;
    }

    public List<Duplicata> findByFilters(String dataInicial, String dataFinal, Boolean receber, Boolean pagar){
        UserSS userSS = UserService.authencated();
        if(userSS == null) throw new ArrayStoreException("Acesso negado");
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
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

        List<Duplicata> duplicatas = new ArrayList<>();
        if(receber && pagar) {
            duplicatas = duplicataRepository.findByDataInclusaoBetweenAndSaldoNotAndUsuarioOrderByDataVencimento(dataI, dataF, 0.0, usuario);
        } else if (receber) {
            duplicatas = duplicataRepository.findByDataInclusaoBetweenAndSaldoNotAndReceberTrueAndUsuarioOrderByDataVencimento(dataI, dataF, 0.0, usuario);
        } else if (pagar) {
            duplicatas = duplicataRepository.findByDataInclusaoBetweenAndSaldoNotAndReceberFalseAndUsuarioOrderByDataVencimento(dataI, dataF, 0.0, usuario);
        }
        return duplicatas;
    }


    public Duplicata insert(Duplicata duplicata) {
        UserSS userSS = UserService.authencated();
        if(userSS == null) throw new ArrayStoreException("Ocorreu um erro ao obter o código do usuário");
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
        duplicata.setId(null);
        duplicata.setUsuario(usuario);
        duplicataRepository.save(duplicata);
        return duplicata;
    }

    public Duplicata update(Duplicata duplicata){
        Duplicata newDuplicata = duplicataRepository.getOne(duplicata.getId());

        // valida se a duplicata possí baixas e se está sendo alterado o valor
        if(!lancamentoRepository.findByDuplicata(newDuplicata).get().isEmpty()
                && duplicata.getValor() != newDuplicata.getValor() ){
            System.out.println(lancamentoRepository.findByDuplicata(newDuplicata));
            throw new DataIntegrityExeption("A duplicata possuí baixas, exclua as baixas antes de alterar o valor");
        }

        // se a data de inclusão não foi informada, é sem recebimento previsto
        if(duplicata.getDataVencimento() == null) newDuplicata.setDataVencimento(null);
        else newDuplicata.setDataVencimento(duplicata.getDataVencimento());

        newDuplicata.setDataInclusao(duplicata.getDataInclusao());
        newDuplicata.setValor(duplicata.getValor());
        newDuplicata.setSaldo(duplicata.getSaldo());
        newDuplicata.setObservacao(duplicata.getObservacao());
        newDuplicata.setReceber(duplicata.getReceber());
        newDuplicata.setPortador(duplicata.getPortador());
        newDuplicata.setPlano(duplicata.getPlano());
        newDuplicata.setPessoa(duplicata.getPessoa());

        return duplicataRepository.save(newDuplicata);
    }

    public Duplicata fromDTO(DuplicataDTO duplicataDTO){
        Portador portador = portadorRepository.getOne(duplicataDTO.getPortador_id());
        Plano plano = planoRepository.getOne(duplicataDTO.getPlano_id());
        if(portador == null) throw new ObjectNotFoundException("Portador não encontrado");
        if(plano == null) throw new ObjectNotFoundException("Conta não encontrada");

        UserSS userSS = UserService.authencated();
        if(userSS == null) throw new ArrayStoreException("Ocorreu um erro ao obter o código do usuário");
        Usuario usuario = usuarioRepository.getOne(userSS.getId());

        Pessoa pessoa;

        // se for informado o código da pessoa, o registro é buscado para ser vinculado a duplicata
        if(duplicataDTO.getPessoa_id() != null){
            pessoa = pessoaRepository.getOne(duplicataDTO.getPessoa_id());
        } else {
            // se for informado somente o nome, é feita a busca pelo nome
            pessoa = pessoaRepository.findFirst1ByNomeAndUsuario(duplicataDTO.getPessoa_nome(), usuario);

            // caso a pessoa não esteja cadastrada, é feito o cadastro
            if(pessoa == null) {
                Pessoa pessoa1 = new Pessoa(null, duplicataDTO.getPessoa_nome());
                pessoa1.setUsuario(usuario);
                pessoa = pessoaRepository.save(pessoa1);
            }
        }

        return new Duplicata(duplicataDTO.getId(), duplicataDTO.getDataInclusao(), duplicataDTO.getDataVencimento(),
                duplicataDTO.getValor(), duplicataDTO.getObservacao(), duplicataDTO.getReceber(), portador, plano,
                duplicataDTO.getValor(), pessoa);
    }
}
