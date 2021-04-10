package com.br.gutjahr.despesas.resourses;

import com.br.gutjahr.despesas.model.ItemCard;
import com.br.gutjahr.despesas.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/home")
public class HomeResourse {

    @Autowired
    private HomeService homeService;

    @RequestMapping(value = "/pago", method = RequestMethod.GET)
    public ResponseEntity<?> findTotalPago(){
        List<ItemCard> itemCardList = homeService.findTotalPago();
        return ResponseEntity.ok().body(itemCardList);
    }

    @RequestMapping(value = "/recebido", method = RequestMethod.GET)
    public ResponseEntity<?> findTotalRecebido(){
        List<ItemCard> itemCardList = homeService.findTotalRecebido();
        return ResponseEntity.ok().body(itemCardList);
    }

    @RequestMapping(value = "/portadores", method = RequestMethod.GET)
    public ResponseEntity<?> findSaldoPortadores(){
        List<ItemCard> itemCardList = homeService.findSaldoPortadores();
        return ResponseEntity.ok().body(itemCardList);
    }
}
