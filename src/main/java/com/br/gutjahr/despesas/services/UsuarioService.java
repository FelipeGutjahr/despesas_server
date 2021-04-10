package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.dto.UsuarioDTO;
import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.repositories.UsuarioRepository;
import com.br.gutjahr.despesas.security.UserSS;
import com.br.gutjahr.despesas.services.exceptions.DataIntegrityExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private BCryptPasswordEncoder pe;

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

    public Usuario insert(Usuario usuario){
        if(!usuarioRepository.findByEmail(usuario.getEmail()).isEmpty()){
            throw new DataIntegrityExeption("E-mail já cadastrado");
        }
        usuario.setId(null);
        usuario.setSenha(pe.encode(usuario.getSenha()));
        usuario.setDt_cadastro(new Date(System.currentTimeMillis()));
        usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario fromDTO(UsuarioDTO usuarioDTO){
        return new Usuario(usuarioDTO.getId(), usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getSenha());
    }
}
