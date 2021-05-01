package com.br.gutjahr.despesas.resourses;

import com.br.gutjahr.despesas.dto.DuplicataDTO;
import com.br.gutjahr.despesas.model.Duplicata;
import com.br.gutjahr.despesas.services.DuplicataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/duplicatas")
public class DuplicataResourse {

    @Autowired
    private DuplicataService duplicataService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(){
        List<Duplicata> duplicataList = duplicataService.findAll();
        return ResponseEntity.ok().body(duplicataList);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filtros")
    public ResponseEntity<?> findByFilters(
            @RequestParam(value = "dataInicial") String dataInicial,
            @RequestParam(value = "dataFinal") String dataFinal,
            @RequestParam(value = "receber", defaultValue = "false") boolean receber,
            @RequestParam(value = "pagar", defaultValue = "false") boolean pagar
    ){
        List<Duplicata> duplicataList = duplicataService.findByFilters(dataInicial, dataFinal, receber, pagar);
        return ResponseEntity.ok().body(duplicataList);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody DuplicataDTO duplicataDTO){
        Duplicata duplicata = duplicataService.fromDTO(duplicataDTO);
        duplicata = duplicataService.insert(duplicata);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(duplicata.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
