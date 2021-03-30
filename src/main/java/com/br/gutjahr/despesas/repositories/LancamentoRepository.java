package com.br.gutjahr.despesas.repositories;

import com.br.gutjahr.despesas.model.Lancamento;
import com.br.gutjahr.despesas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {

    @Transactional(readOnly = true)
    List<Lancamento> findByUsuario(Usuario usuario);
}
