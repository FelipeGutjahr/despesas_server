package com.br.gutjahr.despesas.repositories;

import com.br.gutjahr.despesas.model.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PlanoRepository extends JpaRepository<Plano, Integer> {

    @Transactional(readOnly = true)
    Plano findByCodContabilAndUsuarioId(String cod_contabil, Integer usuario_id);
}
