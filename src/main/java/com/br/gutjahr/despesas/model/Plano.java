package com.br.gutjahr.despesas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Plano implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String codContabil;
    private String nome;
    private boolean dre;

    @Transient
    private Double saldoAtual;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "planoCredito")
    private List<Lancamento> lancamentosCredito = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "plano_debito")
    private List<Lancamento> lancamentosDebito = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "plano")
    private List<Portador> portadores = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "plano")
    private List<PlanoSaldo> planoSaldoList = new ArrayList<>();

    public Plano(){}

    public Plano(Integer id, String cod_contabil, String nome, boolean dre, Double saldoAtual) {
        this.id = id;
        this.codContabil = cod_contabil;
        this.nome = nome;
        this.dre = dre;
        this.saldoAtual = saldoAtual;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCod_contabil() {
        return codContabil;
    }

    public void setCod_contabil(String cod_contabil) {
        this.codContabil = cod_contabil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean setDre() {
        return dre;
    }

    public void setDre(boolean dre) {
        this.dre = dre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(Double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Código: ");
        builder.append(getId());
        builder.append(", Código contábil: ");
        builder.append(getCod_contabil());
        builder.append(", Nome: ");
        builder.append(getNome());
        return builder.toString();
    }
}
