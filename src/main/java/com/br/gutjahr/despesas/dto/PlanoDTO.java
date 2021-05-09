package com.br.gutjahr.despesas.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PlanoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotNull(message = "Informe o código contábil")
    private String codContabil;
    @NotNull(message = "Informe o nome")
    private String nome;
    private boolean dre;

    public PlanoDTO(){}

    public PlanoDTO(Integer id, String codContabil, String nome, boolean dre) {
        this.id = id;
        this.codContabil = codContabil;
        this.nome = nome;
        this.dre = dre;
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

    public boolean isDre() {
        return dre;
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
