package com.br.gutjahr.despesas.repositories;

import com.br.gutjahr.despesas.model.Portador;
import com.br.gutjahr.despesas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PortadorRepository extends JpaRepository<Portador, Integer> {

    @Transactional(readOnly = true)
    List<Portador> findByUsuario(Usuario usuario);
}
