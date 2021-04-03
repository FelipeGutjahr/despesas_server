package com.br.gutjahr.despesas.repositories;

import com.br.gutjahr.despesas.model.PlanoSaldo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PlanoSaldoRepository extends JpaRepository<PlanoSaldo, Integer> {

    @Transactional
    Optional<PlanoSaldo> findByDataAndPlanoId(Date data, Integer plano_id);

    @Transactional
    Optional<PlanoSaldo> findTopByPlanoId(Integer plano_id);
}
