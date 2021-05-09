package com.br.gutjahr.despesas.repositories;

import com.br.gutjahr.despesas.model.Duplicata;
import com.br.gutjahr.despesas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DuplicataRepository extends JpaRepository<Duplicata, Integer> {

    @Transactional(readOnly = true)
    Optional<List<Duplicata>> findByUsuario(Usuario usuario);

    // duplicatas a receber e a pagar
    @Transactional(readOnly = true)
    Optional<List<Duplicata>> findByDataInclusaoBetweenAndSaldoNotAndUsuarioOrderByDataVencimento(Date dataInicial, Date dataFinal, Double saldo, Usuario usuario);

    // duplicatas a pagar
    @Transactional(readOnly = true)
    Optional<List<Duplicata>> findByDataInclusaoBetweenAndSaldoNotAndReceberFalseAndUsuarioOrderByDataVencimento(Date dataInicial, Date dataFinal, Double saldo, Usuario usuario);

    // duplicatas a receber
    @Transactional(readOnly = true)
    Optional<List<Duplicata>> findByDataInclusaoBetweenAndSaldoNotAndReceberTrueAndUsuarioOrderByDataVencimento(Date dataInicial, Date dataFinal, Double saldo, Usuario usuario);
}
