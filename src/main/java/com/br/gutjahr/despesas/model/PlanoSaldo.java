package com.br.gutjahr.despesas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PlanoSaldo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(precision = 2)
    private Double saldo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;

    public PlanoSaldo(){};

    public PlanoSaldo(Date data, Double saldo) {
        this.data = data;
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
