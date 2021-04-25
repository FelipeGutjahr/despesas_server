package com.br.gutjahr.despesas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class UsuarioConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer primeiroDiaMes;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private Boolean darkTheme;

    public UsuarioConfig(){}

    public UsuarioConfig(Integer id, Integer primeiroDiaMes, Boolean darkTheme) {
        this.id = id;
        this.primeiroDiaMes = primeiroDiaMes;
        this.darkTheme = darkTheme;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrimeiroDiaMes() {
        return primeiroDiaMes;
    }

    public void setPrimeiroDiaMes(Integer primeiroDiaMes) {
        this.primeiroDiaMes = primeiroDiaMes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getDarkTheme() {
        return darkTheme;
    }

    public void setDarkTheme(Boolean darkTheme) {
        this.darkTheme = darkTheme;
    }
}
