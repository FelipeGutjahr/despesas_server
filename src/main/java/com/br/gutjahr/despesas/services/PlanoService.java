package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.dto.PlanoDTO;
import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.model.PlanoSaldo;
import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.repositories.PlanoRepository;
import com.br.gutjahr.despesas.repositories.PlanoSaldoRepository;
import com.br.gutjahr.despesas.repositories.UsuarioRepository;
import com.br.gutjahr.despesas.security.UserSS;
import com.br.gutjahr.despesas.services.exceptions.DataIntegrityExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanoSaldoRepository planoSaldoRepository;

    public List<Plano> findAll(){
        UserSS userSS = UserService.authencated();
        if(userSS == null) {
            throw new ArithmeticException("Acesso negado");
        }
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
        List<Plano> planos = planoRepository.findByUsuarioOrderByCodContabil(usuario);
        return planos;
    }

    public Plano insert(Plano plano){
        UserSS userSS = UserService.authencated();
        if(userSS == null) {
            throw new ArrayStoreException("Ocorreu um erro ao obter o código do usuário");
        }
        Plano plano1 = planoRepository.findByCodContabilAndUsuarioId(plano.getCod_contabil(), userSS.getId());
        if(plano1 != null) {
            throw new DataIntegrityExeption("Código contábil já cadastrado");
        }
        Usuario usuario = usuarioRepository.getOne(userSS.getId());
        plano.setId(null);
        plano.setUsuario(usuario);
        for(int i=1;i<plano.getCod_contabil().length();i++){
            plano.setNome(' ' + plano.getNome());
        }
        planoRepository.save(plano);
        return plano;
    }

    public Plano fromDTO(PlanoDTO planoDTO){
        return new Plano(planoDTO.getId(), planoDTO.getCod_contabil(), planoDTO.getNome(), planoDTO.getDre(),
                getNivel(planoDTO.getCod_contabil()));
    }

    private Integer getNivel(String codContabil){
        Integer nivel = 0;
        for(int i=0;i<codContabil.length();i++){
            if(codContabil.charAt(i) == '.'){
                nivel++;
            }
        }
        return nivel;
    }
}
