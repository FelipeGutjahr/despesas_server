package com.br.gutjahr.despesas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Lancamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date data;
    private Double valor;
    private String historico;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "plano_credito_id")
    private Plano plano_credito;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "plano_debito_id")
    private Plano plano_debito;

    public Lancamento(){}

    public Lancamento(Integer id, Date data, Double valor, String historico) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.historico = historico;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Plano getPlano_credito() {
        return plano_credito;
    }

    public void setPlano_credito(Plano plano_credito) {
        this.plano_credito = plano_credito;
    }

    public Plano getPlano_debito() {
        return plano_debito;
    }

    public void setPlano_debito(Plano plano_debito) {
        this.plano_debito = plano_debito;
    }
}
