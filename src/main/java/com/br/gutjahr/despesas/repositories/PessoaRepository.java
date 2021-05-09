package com.br.gutjahr.despesas.repositories;

import com.br.gutjahr.despesas.model.Pessoa;
import com.br.gutjahr.despesas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Transactional(readOnly = true)
    Optional<Pessoa> findFirst1ByNomeAndUsuario(String nome, Usuario usuario);
}
