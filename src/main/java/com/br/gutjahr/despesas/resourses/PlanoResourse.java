package com.br.gutjahr.despesas.resourses;

import com.br.gutjahr.despesas.dto.PlanoDTO;
import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.model.PlanoSaldo;
import com.br.gutjahr.despesas.services.PlanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/planos")
public class PlanoResourse {

    @Autowired
    private PlanoService planoService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(){
        List<Plano> planoList = planoService.findAll();
        return ResponseEntity.ok().body(planoList);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody PlanoDTO planoDTO){
        Plano plano = planoService.fromDTO(planoDTO);
        plano = planoService.insert(plano);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(plano.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
