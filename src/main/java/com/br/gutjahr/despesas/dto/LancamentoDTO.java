package com.br.gutjahr.despesas.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class LancamentoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotNull(message = "Informe a data")
    private Date data;
    @NotNull(message = "Informe o valor")
    private Double valor;
    @NotNull(message = "Informe o histórico")
    @Length(min = 5, max = 200, message = "Histórico deve conter entre 5 e 200 caracteres")
    private String historico;

    @NotNull(message = "Informe a conta de débito")
    private Integer plano_deb_id;

    @NotNull(message = "Informe a conta de crédito")
    private Integer plano_cre_id;

    public LancamentoDTO(){}

    public LancamentoDTO(Integer id, Date data, Double valor, String historico, Integer plano_deb_id, Integer plano_cre_id) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.historico = historico;
        this.plano_deb_id = plano_deb_id;
        this.plano_cre_id = plano_cre_id;
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

    public Integer getPlano_deb_id() {
        return plano_deb_id;
    }

    public void setPlano_deb_id(Integer plano_deb_id) {
        this.plano_deb_id = plano_deb_id;
    }

    public Integer getPlano_cre_id() {
        return plano_cre_id;
    }

    public void setPlano_cre_id(Integer plano_cre_id) {
        this.plano_cre_id = plano_cre_id;
    }
}
