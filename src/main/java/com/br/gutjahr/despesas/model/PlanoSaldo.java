package com.br.gutjahr.despesas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class PlanoSaldo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date data;
    @JsonIgnore
    @Column(precision = 2)
    private Double credito;
    @JsonIgnore
    @Column(precision = 2)
    private Double debito;
    @Column(precision = 2)
    private Double saldo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;

    public PlanoSaldo(){};

    public PlanoSaldo(Date data, Double credito, Double debito, Double saldo) {
        this.data = data;
        this.credito = credito;
        this.debito = debito;
        this.saldo = saldo;
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

    public Double getCredito() {
        return credito;
    }

    public void setCredito(Double credito) {
        this.credito = credito;
    }

    public Double getDebito() {
        return debito;
    }

    public void setDebito(Double debito) {
        this.debito = debito;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Data: ");
        builder.append(getData());
        builder.append(", Saldo: ");
        builder.append(getSaldo());
        builder.append(", plano_id: ");
        builder.append(getPlano().getId());
        return builder.toString();
    }
}
