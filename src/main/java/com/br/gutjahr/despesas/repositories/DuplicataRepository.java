package com.br.gutjahr.despesas.repositories;

import com.br.gutjahr.despesas.model.Duplicata;
import com.br.gutjahr.despesas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DuplicataRepository extends JpaRepository<Duplicata, Integer> {

    @Transactional(readOnly = true)
    List<Duplicata> findByUsuario(Usuario usuario);
}
