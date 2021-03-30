package com.br.gutjahr.despesas.resourses;

import com.br.gutjahr.despesas.dto.LancamentoDTO;
import com.br.gutjahr.despesas.model.Lancamento;
import com.br.gutjahr.despesas.services.LancamentoService;
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
@RequestMapping(value = "/lancamentos")
public class LancamentoResourse {

    @Autowired
    private LancamentoService lancamentoService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(){
        List<Lancamento> lancamentoList = lancamentoService.findAll();
        return ResponseEntity.ok().body(lancamentoList);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody LancamentoDTO lancamentoDTO){
        Lancamento lancamento = lancamentoService.fromDTO(lancamentoDTO);
        lancamento = lancamentoService.insert(lancamento);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(lancamento.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
