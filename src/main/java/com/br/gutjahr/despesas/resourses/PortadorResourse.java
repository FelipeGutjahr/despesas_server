package com.br.gutjahr.despesas.resourses;

import com.br.gutjahr.despesas.model.Portador;
import com.br.gutjahr.despesas.repositories.PlanoRepository;
import com.br.gutjahr.despesas.services.LancamentoService;
import com.br.gutjahr.despesas.services.PortadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.br.gutjahr.despesas.dto.PortadorDTO;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/portadores")
public class PortadorResourse {

    @Autowired
    private PortadorService portadorService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(){
        List<Portador> portadorList = portadorService.findAll();
        return ResponseEntity.ok().body(portadorList);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody PortadorDTO portadorDTO) {
        Portador portador = portadorService.fromDTO(portadorDTO);
        portador = portadorService.insert(portador);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(portador.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        portadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
