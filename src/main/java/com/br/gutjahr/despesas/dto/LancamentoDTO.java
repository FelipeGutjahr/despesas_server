package com.br.gutjahr.despesas.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class LancamentoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotNull(message = "Informe a data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @NotNull(message = "Informe o valor")
    private Double valor;
    @NotNull(message = "Informe o histórico")
    @Length(min = 5, max = 200, message = "Histórico deve conter entre 5 e 200 caracteres")
    private String historico;
    private Boolean isCredito = false;
    private Boolean isParcelado = false;
    private Integer qtdParcelas = 0;
    private Integer duplicataId;

    @NotNull(message = "Informe a conta de débito")
    private Integer planoDebitoId;

    @NotNull(message = "Informe a conta de crédito")
    private Integer planoCreditoId;

    @NotNull(message = "Informe o nome da pessoa")
    private String nomePessoa;
    private Integer pessoaId;

    public LancamentoDTO(){}

    public LancamentoDTO(Integer id, Date data, Double valor, String historico, Integer planoDebitoId,
                         Integer planoCreditoId, Boolean isCredito, Boolean isParcelado, Integer qtdParcelas,
                         Integer duplicataId, String nomePessoa, Integer pessoaId) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.historico = historico;
        this.planoDebitoId = planoDebitoId;
        this.planoCreditoId = planoCreditoId;
        this.isCredito = isCredito;
        this.isParcelado = isParcelado;
        this.qtdParcelas = qtdParcelas;
        this.duplicataId = duplicataId;
        this.nomePessoa = nomePessoa;
        this.pessoaId = pessoaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Boolean getCredito() {
        return isCredito;
    }

    public void setCredito(Boolean credito) {
        isCredito = credito;
    }

    public Boolean getParcelado() {
        return isParcelado;
    }

    public void setParcelado(Boolean parcelado) {
        isParcelado = parcelado;
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

    public Integer getPlanoDebitoId() {
        return planoDebitoId;
    }

    public void setPlanoDebitoId(Integer planoDebitoId) {
        this.planoDebitoId = planoDebitoId;
    }

    public Integer getPlanoCreditoId() {
        return planoCreditoId;
    }

    public void setPlanoCreditoId(Integer planoCreditoId) {
        this.planoCreditoId = planoCreditoId;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public Integer getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Integer pessoaId) {
        this.pessoaId = pessoaId;
    }
}
