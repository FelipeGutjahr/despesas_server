package com.br.gutjahr.despesas.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PortadorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotNull(message = "Informe o nome")
    private String nome;
    @NotNull(message = "Informe a conta do plano")
    private Integer planoId;
    private Boolean credito = false;
    private Double limite = 0.0;

    public PortadorDTO(){}

    public PortadorDTO(Integer id, String nome, Integer planoId, Boolean credito, Double limite) {
        this.id = id;
        this.nome = nome;
        this.planoId = planoId;
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

    public Integer getPlanoId() {
        return planoId;
    }

    public void setPlanoId(Integer planoId) {
        this.planoId = planoId;
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
