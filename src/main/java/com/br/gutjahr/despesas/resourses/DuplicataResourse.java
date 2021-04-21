package com.br.gutjahr.despesas.resourses;

import com.br.gutjahr.despesas.dto.DuplicataDTO;
import com.br.gutjahr.despesas.model.Duplicata;
import com.br.gutjahr.despesas.services.DuplicataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody DuplicataDTO duplicataDTO){
        Duplicata duplicata = duplicataService.fromDTO(duplicataDTO);
        duplicata = duplicataService.insert(duplicata);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(duplicata.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
