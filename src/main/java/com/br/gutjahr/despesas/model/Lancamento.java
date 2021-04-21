package com.br.gutjahr.despesas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Lancamento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(scale = 2)
    private Double valor;
    private String historico;
    private Boolean credito;
    private Boolean faturado;

    @Transient
    private Integer qtd_parcelas;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "plano_credito_id")
    private Plano planoCredito;

    @ManyToOne
    @JoinColumn(name = "plano_debito_id")
    private Plano planoDebito;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "duplicata_id")
    private Duplicata duplicata;

    public Lancamento(){}

    public Lancamento(Integer id, Date data, Double valor, String historico, Plano plano_credito, Plano plano_debito,
                      Boolean credito, Boolean faturado, Integer qtd_parcelas, Duplicata duplicata) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.historico = historico;
        this.planoCredito = plano_credito;
        this.planoDebito = plano_debito;
        this.credito = credito;
        this.faturado = faturado;
        this.qtd_parcelas = qtd_parcelas;
        this.duplicata = duplicata;
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
        return planoCredito;
    }

    public void setPlano_credito(Plano plano_credito) {
        this.planoCredito = plano_credito;
    }

    public Plano getPlano_debito() {
        return planoDebito;
    }

    public void setPlano_debito(Plano plano_debito) {
        this.planoDebito = plano_debito;
    }

    public Boolean getCredito() {
        return credito;
    }

    public void setCredito(Boolean credito) {
        this.credito = credito;
    }

    public Boolean getFaturado() {
        return faturado;
    }

    public void setFaturado(Boolean faturado) {
        this.faturado = faturado;
    }

    public Integer getQtd_parcelas() {
        return qtd_parcelas;
    }

    public void setQtd_parcelas(Integer qtd_parcelas) {
        this.qtd_parcelas = qtd_parcelas;
    }

    public Plano getPlanoCredito() {
        return planoCredito;
    }

    public void setPlanoCredito(Plano planoCredito) {
        this.planoCredito = planoCredito;
    }

    public Plano getPlanoDebito() {
        return planoDebito;
    }

    public void setPlanoDebito(Plano planoDebito) {
        this.planoDebito = planoDebito;
    }

    public Duplicata getDuplicata() {
        return duplicata;
    }

    public void setDuplicata(Duplicata duplicata) {
        this.duplicata = duplicata;
    }

    @Override
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        StringBuilder builder = new StringBuilder();
        builder.append("Id: ");
        builder.append(getId());
        builder.append(", Valor: ");
        builder.append(getValor());
        builder.append(", data do lançamento: ");
        builder.append(sdf.format(getData()));
        return builder.toString();
    }
}
