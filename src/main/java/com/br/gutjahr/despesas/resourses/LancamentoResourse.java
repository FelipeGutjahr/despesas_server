package com.br.gutjahr.despesas.resourses;

import com.br.gutjahr.despesas.dto.LancamentoDTO;
import com.br.gutjahr.despesas.model.Lancamento;
import com.br.gutjahr.despesas.services.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(method = RequestMethod.GET, value = "/periodo")
    public ResponseEntity<?> findBetweenLancamentos(
            @RequestParam(value = "dataInicial") String dataInicial,
            @RequestParam(value = "dataFinal") String dataFinal
    ){
        List<Lancamento> lancamentoList = lancamentoService.findBetweenLancamentos(dataInicial, dataFinal);
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        lancamentoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
