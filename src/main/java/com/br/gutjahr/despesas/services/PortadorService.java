package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.dto.PortadorDTO;
import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.model.Portador;
import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.repositories.PlanoRepository;
import com.br.gutjahr.despesas.repositories.PortadorRepository;
import com.br.gutjahr.despesas.repositories.UsuarioRepository;
import com.br.gutjahr.despesas.security.UserSS;
import com.br.gutjahr.despesas.services.exceptions.DataIntegrityExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortadorService {

    @Autowired
    private PortadorRepository portadorRepository;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Portador> findAll(){
        UserSS userSS = UserService.authencated();
        if (userSS == null){
            throw new ArithmeticException("Acesso negado");
        }
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
        Optional<List<Portador>> portadores = portadorRepository.findByUsuario(usuario);
        return portadores.get();
    }

    public Portador insert(Portador portador){
        UserSS userSS = UserService.authencated();
        if(userSS == null){
            throw new ArrayStoreException("Ocorreu um erro ao obter o código do usuário");
        }
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
        portador.setId(null);
        portador.setUsuario(usuario);
        portadorRepository.save(portador);
        return portador;
    }

    public void delete(Integer id){
        try {
            Portador portador = portadorRepository.getOne(id);
            portadorRepository.delete(portador);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityExeption("Ocorreu um erro no servidor");
        }
    }

    public Portador fromDTO(PortadorDTO portadorDTO){
        Plano plano = planoRepository.getOne(portadorDTO.getPlano_id());
        return new Portador(portadorDTO.getId(), portadorDTO.getNome(), plano,portadorDTO.getCredito(), portadorDTO.getLimite());
    }
}
