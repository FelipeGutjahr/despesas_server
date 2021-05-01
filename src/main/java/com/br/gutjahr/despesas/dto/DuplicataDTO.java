package com.br.gutjahr.despesas.dto;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class DuplicataDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotNull(message = "Informe a data de inclusão")
    @Temporal(TemporalType.DATE)
    private Date dataInclusao;

    @Temporal(TemporalType.DATE)
    private Date dataVencimento;

    private Double valor;
    private String observacao;
    private Boolean receber;

    private Integer portador_id;
    private Integer plano_id;

    @NotNull(message = "Informe o nome da pessoa")
    private String pessoaNome;
    private Integer pessoa_id;

    public DuplicataDTO() {}

    public DuplicataDTO(Integer id, Date dataInclusao, Date dataVencimento, Double valor, String observacao,
                        Boolean receber, Integer portador_id, Integer plano_id, Integer pessoa_id, String pessoaNome) {
        this.id = id;
        this.dataInclusao = dataInclusao;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.observacao = observacao;
        this.receber = receber;
        this.portador_id = portador_id;
        this.plano_id = plano_id;
        this.pessoa_id = pessoa_id;
        this.pessoaNome = pessoaNome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getReceber() {
        return receber;
    }

    public void setReceber(Boolean receber) {
        this.receber = receber;
    }

    public Integer getPortador_id() {
        return portador_id;
    }

    public void setPortador_id(Integer portador_id) {
        this.portador_id = portador_id;
    }

    public Integer getPlano_id() {
        return plano_id;
    }

    public void setPlano_id(Integer plano_id) {
        this.plano_id = plano_id;
    }

    public String getPessoaNome() {
        return pessoaNome;
    }

    public void setPessoaNome(String pessoaNome) {
        this.pessoaNome = pessoaNome;
    }

    public Integer getPessoa_id() {
        return pessoa_id;
    }

    public void setPessoa_id(Integer pessoa_id) {
        this.pessoa_id = pessoa_id;
    }
}
