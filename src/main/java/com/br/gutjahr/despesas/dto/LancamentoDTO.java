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
    private Boolean is_credito = false;
    private Boolean is_parcelado = false;
    private Integer qtd_parcelas = 0;

    @NotNull(message = "Informe a conta de débito")
    private Integer plano_debito_id;

    @NotNull(message = "Informe a conta de crédito")
    private Integer plano_credito_id;

    public LancamentoDTO(){}

    public LancamentoDTO(Integer id, Date data, Double valor, String historico, Integer plano_debito_id,
                         Integer plano_credito_id, Boolean is_credito, Boolean is_parcelado, Integer qtd_parcelas) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.historico = historico;
        this.plano_debito_id = plano_debito_id;
        this.plano_credito_id = plano_credito_id;
        this.is_credito = is_credito;
        this.is_parcelado = is_parcelado;
        this.qtd_parcelas = qtd_parcelas;
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

    public Integer getPlano_debito_id() {
        return plano_debito_id;
    }

    public void setPlano_debito_id(Integer plano_debito_id) {
        this.plano_debito_id = plano_debito_id;
    }

    public Integer getPlano_credito_id() {
        return plano_credito_id;
    }

    public void setPlano_credito_id(Integer plano_credito_id) {
        this.plano_credito_id = plano_credito_id;
    }

    public Boolean getIs_credito() {
        return is_credito;
    }

    public void setIs_credito(Boolean is_credito) {
        this.is_credito = is_credito;
    }

    public Boolean getIs_parcelado() {
        return is_parcelado;
    }

    public void setIs_parcelado(Boolean is_parcelado) {
        this.is_parcelado = is_parcelado;
    }

    public Integer getQtd_parcelas() {
        return qtd_parcelas;
    }

    public void setQtd_parcelas(Integer qtd_parcelas) {
        this.qtd_parcelas = qtd_parcelas;
    }
}
