package com.br.gutjahr.despesas.resourses;

import com.br.gutjahr.despesas.dto.UsuarioDTO;
import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResourse {

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> find(){
        Usuario usuario = usuarioService.find();
        return ResponseEntity.ok().body(usuario);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioService.fromDTO(usuarioDTO);
        usuario = usuarioService.insert(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
