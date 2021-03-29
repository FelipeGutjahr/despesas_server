package com.br.gutjahr.despesas.resourses;

import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.services.PlanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
