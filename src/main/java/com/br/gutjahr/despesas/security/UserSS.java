package com.br.gutjahr.despesas.security;

import com.br.gutjahr.despesas.enums.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserSS implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSS() {}

    public UserSS(Integer id, String email, String senha, Perfil perfil) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.authorities = setUserAuthorities(perfil);
    }

    // converte o perfil do usuário para uma lista de Collection<SimpleGrantedAuthority>
    public Collection<SimpleGrantedAuthority> setUserAuthorities(Perfil perfil) {
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority(perfil.getDescricao()));
        return grantedAuthorities;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
