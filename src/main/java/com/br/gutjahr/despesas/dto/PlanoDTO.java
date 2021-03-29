package com.br.gutjahr.despesas.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PlanoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotNull(message = "Informe o código contábil")
    private String cod_contabil;
    @NotNull(message = "Informe o nome")
    private String nome;
    private boolean dre;

    public PlanoDTO(){}

    public PlanoDTO(Integer id, String cod_contabil, String nome, boolean dre) {
        this.id = id;
        this.cod_contabil = cod_contabil;
        this.nome = nome;
        this.dre = dre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCod_contabil() {
        return cod_contabil;
    }

    public void setCod_contabil(String cod_contabil) {
        this.cod_contabil = cod_contabil;
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
}
