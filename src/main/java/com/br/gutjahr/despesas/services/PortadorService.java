package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.dto.PortadorDTO;
import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.model.Portador;
import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.repositories.PlanoRepository;
import com.br.gutjahr.despesas.repositories.PortadorRepository;
import com.br.gutjahr.despesas.services.exceptions.DataIntegrityExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortadorService {

    @Autowired
    private UserService userService;

    @Autowired
    private PortadorRepository portadorRepository;

    @Autowired
    private PlanoRepository planoRepository;

    public List<Portador> findAll(){
        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        Optional<List<Portador>> portadores = portadorRepository.findByUsuario(usuario.get());
        return portadores.get();
    }

    public Portador insert(Portador portador){
        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        portador.setId(null);
        portador.setUsuario(usuario.get());
        return portadorRepository.save(portador);
    }

    public void delete(Integer id){
        try {
            Optional<Portador> portador = Optional.ofNullable(portadorRepository.getOne(id));
            portadorRepository.delete(portador.get());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityExeption("Ocorreu um erro no servidor");
        }
    }

    public Portador update(Portador portador){
        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        portador.setUsuario(usuario.get());
        return portadorRepository.save(portador);
    }

    public Portador fromDTO(PortadorDTO portadorDTO){
        Optional<Plano> plano = Optional.ofNullable(planoRepository.getOne(portadorDTO.getPlanoId()));
        return new Portador(portadorDTO.getId(), portadorDTO.getNome(), plano.get(), portadorDTO.getCredito(),
                portadorDTO.getLimite(), portadorDTO.getDiaFechamento());
    }
}
