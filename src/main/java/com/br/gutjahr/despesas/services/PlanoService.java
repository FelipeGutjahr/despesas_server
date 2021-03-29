package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.repositories.PlanoRepository;
import com.br.gutjahr.despesas.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    public List<Plano> findAll(){
        UserSS userSS = UserService.authencated();
        if(userSS == null) {
            throw new ArithmeticException("Acesso negado");
        }
        List<Plano> planos = planoRepository.findAll();
        return planos;
    }
}
