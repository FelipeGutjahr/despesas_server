package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.dto.DuplicataDTO;
import com.br.gutjahr.despesas.model.Duplicata;
import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.model.Portador;
import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.repositories.DuplicataRepository;
import com.br.gutjahr.despesas.repositories.PlanoRepository;
import com.br.gutjahr.despesas.repositories.PortadorRepository;
import com.br.gutjahr.despesas.repositories.UsuarioRepository;
import com.br.gutjahr.despesas.security.UserSS;
import com.br.gutjahr.despesas.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DuplicataService {

    @Autowired
    private DuplicataRepository duplicataRepository;

    @Autowired
    private PortadorRepository portadorRepository;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Duplicata> findAll(){
        UserSS userSS = UserService.authencated();
        if(userSS == null) throw new ArrayStoreException("Acesso negado");
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
        List<Duplicata> duplicatas = duplicataRepository.findByUsuario(usuario);
        return duplicatas;
    }

    public Duplicata insert(Duplicata duplicata) {
        UserSS userSS = UserService.authencated();
        if(userSS == null) throw new ArrayStoreException("Ocorreu um erro ao obter o código do usuário");
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
        duplicata.setId(null);
        duplicata.setUsuario(usuario);
        duplicataRepository.save(duplicata);
        return duplicata;
    }

    public Duplicata fromDTO(DuplicataDTO duplicataDTO){
        Portador portador = portadorRepository.getOne(duplicataDTO.getPortador_id());
        Plano plano = planoRepository.getOne(duplicataDTO.getPlano_id());
        if(portador == null) throw new ObjectNotFoundException("Portador não encontrado");
        if(plano == null) throw new ObjectNotFoundException("Conta não encontrada");

        return new Duplicata(duplicataDTO.getId(), duplicataDTO.getDataInclusao(), duplicataDTO.getDataVencimento(),
                duplicataDTO.getValor(), duplicataDTO.getObservacao(), duplicataDTO.isaReceber(), portador, plano, duplicataDTO.getValor());
    }
}
