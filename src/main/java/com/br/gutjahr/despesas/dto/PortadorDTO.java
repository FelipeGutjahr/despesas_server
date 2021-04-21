package com.br.gutjahr.despesas.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PortadorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotNull(message = "Informe o nome")
    private String nome;
    @NotNull(message = "Informe a conta do plano")
    private Integer plano_id;
    private Boolean credito = false;
    private Double limite = 0.0;

    public PortadorDTO(){}

    public PortadorDTO(Integer id, String nome, Integer plano_id, Boolean credito, Double limite) {
        this.id = id;
        this.nome = nome;
        this.plano_id = plano_id;
        this.credito = credito;
        this.limite = limite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPlano_id() {
        return plano_id;
    }

    public void setPlano_id(Integer plano_id) {
        this.plano_id = plano_id;
    }

    public Boolean getCredito() {
        return credito;
    }

    public void setCredito(Boolean credito) {
        this.credito = credito;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }
}
