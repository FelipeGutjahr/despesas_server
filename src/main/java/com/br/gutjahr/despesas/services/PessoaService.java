package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.model.Pessoa;
import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private UserService userService;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa findPessoa(String nome, Integer id){

        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        Optional<Pessoa> pessoa;

        // se o id da pessoa for informada, ela é buscada no banco de dados
        if(id != null){
            pessoa = Optional.ofNullable(pessoaRepository.getOne(id));
        } else {
            // se for informado somente o nome, é feita a busca pelo nome
            pessoa = pessoaRepository.findFirst1ByNomeAndUsuario(nome, usuario.get());

            // caso a pessoa não esteja cadastrada, é feito o cadastro
            if(!pessoa.isPresent()) {
                Pessoa pessoa1 = new Pessoa(null, nome);
                pessoa1.setUsuario(usuario.get());
                pessoa = Optional.ofNullable(pessoaRepository.save(pessoa1));
            }
        }

        return pessoa.get();
    }
}
