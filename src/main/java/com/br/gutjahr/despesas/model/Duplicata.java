package com.br.gutjahr.despesas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Duplicata implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date dataInclusao;
    @Column(columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date dataVencimento;
    @Column(scale = 2)
    private Double valor;
    @Column(scale = 2)
    private Double saldo;
    private String observacao;
    private boolean aReceber;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "portador_id")
    private Portador portador;

    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Plano plano;

    @OneToMany(mappedBy = "duplicata")
    private List<Lancamento> lancamentos = new ArrayList<>();

    public Duplicata(){}

    public Duplicata(Integer id, Date dataInclusao, Date dataVencimento, Double valor, String observacao,
                     boolean aReceber, Portador portador, Plano plano, Double saldo) {
        this.id = id;
        this.dataInclusao = dataInclusao;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.observacao = observacao;
        this.aReceber = aReceber;
        this.portador = portador;
        this.plano = plano;
        this.saldo = saldo;
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

    public boolean isaReceber() {
        return aReceber;
    }

    public void setaReceber(boolean aReceber) {
        this.aReceber = aReceber;
    }

    public Portador getPortador() {
        return portador;
    }

    public void setPortador(Portador portador) {
        this.portador = portador;
    }

    public Plano getPlano() {
        return plano;
    }

    public void setPlano(Plano plano) {
        this.plano = plano;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
