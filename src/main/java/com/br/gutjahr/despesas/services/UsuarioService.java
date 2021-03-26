package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.repositories.UsuarioRepository;
import com.br.gutjahr.despesas.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Usuario find(){
        UserSS userSS = UserService.authencated();
        if(userSS == null){
            throw new ArithmeticException("Acesso negado");
        }
        Optional<Usuario> usuario = usuarioRepository.findById(userSS.getId());
        return usuario.orElse(null);
    }
}
