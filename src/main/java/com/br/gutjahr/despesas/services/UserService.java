package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.repositories.UsuarioRepository;
import com.br.gutjahr.despesas.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> authencated(){
        try {
            UserSS userSS = (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(userSS == null) throw new ArrayStoreException("Acesso negado");

            return Optional.ofNullable(usuarioRepository.getOne(userSS.getId()));
        } catch (Exception e){
            throw new ArrayStoreException("Ocorreu um erro ao obter o usuário autenticado");
        }
    }
}
