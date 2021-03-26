package com.br.gutjahr.despesas.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

public class UsuarioDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotNull(message = "Informe o nome de usuário")
    @Length(min = 3, message = "Nome precisa ter mais de dois caracteres")
    private String nome;
    @Email(message = "E-mail inválido")
    private String email;
    private Date dt_cadastro;

    @NotNull(message = "Informe a senha")
    @Size(min = 6,message = "Senha muito curta")
    private String senha;

    public UsuarioDTO(){}

    public UsuarioDTO(Integer id, String nome, String email, Date dt_cadastro, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dt_cadastro = dt_cadastro;
        this.senha = senha;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(Date dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
