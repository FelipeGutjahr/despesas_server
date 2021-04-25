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
    List<Lancamento> findByPlanoCreditoInAndDataAndPlanoDebitoNotInAndUsuario(Collection<Plano> planos1, Date data, Collection<Plano> planos2, Usuario usuario);

    @Transactional(readOnly = true)
    List<Lancamento> findByPlanoDebitoInAndDataAndPlanoCreditoNotInAndUsuario(Collection<Plano> planos1, Date data, Collection<Plano> planos2, Usuario usuario);

    @Transactional(readOnly = true)
    List<Lancamento> findByPlanoCreditoInAndDataBetweenAndPlanoDebitoNotInAndUsuario(Collection<Plano> planos1, Date data1, Date data2, Collection<Plano> planos2, Usuario usuario);

    @Transactional(readOnly = true)
    List<Lancamento> findByPlanoDebitoInAndDataBetweenAndPlanoCreditoNotInAndUsuario(Collection<Plano> planos1, Date data1, Date data2, Collection<Plano> planos2, Usuario usuario);

    @Transactional(readOnly = true)
    List<Lancamento> findByDataBetweenAndUsuario(Date data1, Date data2, Usuario usuario);
}
