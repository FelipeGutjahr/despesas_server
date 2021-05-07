package com.br.gutjahr.despesas.repositories;

import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PlanoRepository extends JpaRepository<Plano, Integer> {

    @Transactional(readOnly = true)
    Optional<Plano> findByCodContabilAndUsuarioId(String cod_contabil, Integer usuario_id);

    @Transactional(readOnly = true)
    Optional<List<Plano>> findByUsuarioOrderByCodContabil(Usuario usuario);
}
