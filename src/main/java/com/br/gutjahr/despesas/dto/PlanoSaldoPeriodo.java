package com.br.gutjahr.despesas.dto;

import com.br.gutjahr.despesas.model.Lancamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlanoSaldoPeriodo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Double saldoInicial;
    private Double saldoFinal;

    private List<Lancamento> lancamentos = new ArrayList<>();

    public PlanoSaldoPeriodo() {}

    public PlanoSaldoPeriodo(Double saldoInicial, Double saldoFinal) {
        this.saldoInicial = saldoInicial;
        this.saldoFinal = saldoFinal;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(Double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }
}
