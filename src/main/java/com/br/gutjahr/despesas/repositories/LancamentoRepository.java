package com.br.gutjahr.despesas.repositories;

import com.br.gutjahr.despesas.model.Lancamento;
import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {

    @Transactional(readOnly = true)
    List<Lancamento> findByUsuario(Usuario usuario);

    @Transactional(readOnly = true)
    List<Lancamento> findByPlanoCreditoInAndData(Collection<Plano> planos, Date data);

    @Transactional
    List<Lancamento> findByPlanoCreditoInAndDataBetween(Collection<Plano> planos, Date data1, Date data2);
}
