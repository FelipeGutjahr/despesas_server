package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.model.PlanoSaldo;
import com.br.gutjahr.despesas.model.Portador;
import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.repositories.PlanoSaldoRepository;
import com.br.gutjahr.despesas.repositories.PortadorRepository;
import com.br.gutjahr.despesas.repositories.UsuarioRepository;
import com.br.gutjahr.despesas.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortadorService {

    @Autowired
    private PortadorRepository portadorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Portador> findAll(){
        UserSS userSS = UserService.authencated();
        if (userSS == null){
            throw new ArithmeticException("Acesso negado");
        }
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
        List<Portador> portadores = portadorRepository.findByUsuario(usuario);
        return portadores;
    }
}
