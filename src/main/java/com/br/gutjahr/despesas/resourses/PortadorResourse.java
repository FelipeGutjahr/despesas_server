package com.br.gutjahr.despesas.resourses;

import com.br.gutjahr.despesas.model.Portador;
import com.br.gutjahr.despesas.services.PortadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
