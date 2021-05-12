package com.br.gutjahr.despesas.resourses;

import com.br.gutjahr.despesas.dto.PlanoDTO;
import com.br.gutjahr.despesas.dto.PlanoSaldoPeriodo;
import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.services.LancamentoService;
import com.br.gutjahr.despesas.services.PlanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/planos")
public class PlanoResourse {

    @Autowired
    private PlanoService planoService;

    @Autowired
    private LancamentoService lancamentoService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(){
        List<Plano> planoList = planoService.findAll();
        return ResponseEntity.ok().body(planoList);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/periodo")
    public ResponseEntity<?> findSaldoAndLancamentos(
            @RequestParam(value = "dataInicial") String dataInicial,
            @RequestParam(value = "dataFinal") String dataFinal,
            @RequestParam(value = "planoId") Integer planoId
    ){
        PlanoSaldoPeriodo planoSaldoPeriodo = new PlanoSaldoPeriodo();
        Date dataI = new Date();
        Date dataF = new Date();
        try {
            dataI = new SimpleDateFormat("yyyy-MM-dd").parse(dataInicial);
            dataF = new SimpleDateFormat("yyyy-MM-dd").parse(dataFinal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        planoSaldoPeriodo.setSaldoInicial(planoService.findSaldo(dataI, planoId).getSaldo());
        planoSaldoPeriodo.setSaldoFinal(planoService.findSaldo(dataF, planoId).getSaldo());
        planoSaldoPeriodo.setLancamentos(lancamentoService.findBetweenLancamentosAndPlanoId(dataInicial, dataFinal,
                planoId));
        return ResponseEntity.ok().body(planoSaldoPeriodo);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody PlanoDTO planoDTO){
        Plano plano = planoService.fromDTO(planoDTO);
        plano = planoService.insert(plano);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(plano.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody PlanoDTO planoDTO, @PathVariable Integer id){
        Plano plano = planoService.fromDTO(planoDTO);
        plano.setId(id);
        planoService.update(plano);
        return ResponseEntity.ok().build();
    }
}
