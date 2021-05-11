package com.br.gutjahr.despesas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Plano implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String codContabil;
    private String nome;
    private boolean dre;
    private Integer nivel;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "planoCredito")
    private List<Lancamento> lancamentosCredito = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "planoDebito")
    private List<Lancamento> lancamentosDebito = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "plano")
    private List<Portador> portadores = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "plano")
    private List<PlanoSaldo> planoSaldoList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "plano")
    private List<PlanoPai> planoPaiList = new ArrayList<>();

    public Plano(){}

    public Plano(Integer id, String cod_contabil, String nome, boolean dre, Integer nivel) {
        this.id = id;
        this.codContabil = cod_contabil;
        this.nome = nome;
        this.dre = dre;
        this.nivel = nivel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodContabil() {
        return codContabil;
    }

    public void setCodContabil(String codContabil) {
        this.codContabil = codContabil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean getDre() {
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

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Código: ");
        builder.append(getId());
        builder.append(", Código contábil: ");
        builder.append(getCodContabil());
        builder.append(", Nome: ");
        builder.append(getNome());
        return builder.toString();
    }
}
