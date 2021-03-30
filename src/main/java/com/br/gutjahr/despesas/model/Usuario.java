package com.br.gutjahr.despesas.model;

import com.br.gutjahr.despesas.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;
    private Date dt_cadastro;

    private Integer perfil;

    //anotação para não mostrar a senha criptografada ao buscar o usuário
    @JsonIgnore
    private String senha;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Plano> planos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Portador> portadores = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Lancamento> lancamentos = new ArrayList<>();

    public Usuario() {
        addPerfil(Perfil.USER_FREE);
    }

    public Usuario(Integer id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        addPerfil(Perfil.USER_FREE);
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

    public Perfil getPerfil() { return Perfil.toEnum(this.perfil); }

    public void addPerfil(Perfil perfil) { this.perfil = perfil.getCod(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        StringBuilder builder = new StringBuilder();
        builder.append("Nome de usuário: ");
        builder.append(getNome());
        builder.append(", E-mail: ");
        builder.append(getEmail());
        builder.append(", cadastrado em: ");
        builder.append(sdf.format(getDt_cadastro()));
        return builder.toString();
    }
}
