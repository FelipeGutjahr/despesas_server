package com.br.gutjahr.despesas.repositories;

import com.br.gutjahr.despesas.model.PlanoSaldo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

public interface PlanoSaldoRepository extends JpaRepository<PlanoSaldo, Integer> {

    @Transactional
    Optional<PlanoSaldo> findByDataAndPlanoId(Date data, Integer plano_id);

    @Transactional
    Optional<PlanoSaldo> findTopByDataBeforeAndPlanoIdOrderByDataDesc(Date data, Integer plano_id);
}
