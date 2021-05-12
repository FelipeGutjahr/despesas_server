package com.br.gutjahr.despesas.dto;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class BaixaDuplicataDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Informe a data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @NotNull(message = "Informe o valor")
    private Double valor;
    private Boolean credito = false;
    private Boolean parcelado = false;
    private Integer qtdParcelas = 0;
    private Integer duplicataId;
    private Integer pessoaId;
    private String pessoaNome;

    public BaixaDuplicataDTO(){}

    public BaixaDuplicataDTO(Date data, Double valor, Boolean credito, Boolean parcelado, Integer qtdParcelas,
                             Integer duplicataId, Integer pessoaId, String pessoaNome) {
        this.data = data;
        this.valor = valor;
        this.credito = credito;
        this.parcelado = parcelado;
        this.qtdParcelas = qtdParcelas;
        this.duplicataId = duplicataId;
        this.pessoaId = pessoaId;
        this.pessoaNome = pessoaNome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getCredito() {
        return credito;
    }

    public void setCredito(Boolean credito) {
        this.credito = credito;
    }

    public Boolean getParcelado() {
        return parcelado;
    }

    public void setParcelado(Boolean parcelado) {
        this.parcelado = parcelado;
    }

    public Integer getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(Integer qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }

    public Integer getDuplicataId() {
        return duplicataId;
    }

    public void setDuplicataId(Integer duplicataId) {
        this.duplicataId = duplicataId;
    }

    public Integer getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Integer pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getPessoaNome() {
        return pessoaNome;
    }

    public void setPessoaNome(String pessoaNome) {
        this.pessoaNome = pessoaNome;
    }
}
