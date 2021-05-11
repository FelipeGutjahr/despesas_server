package com.br.gutjahr.despesas.services;

import com.br.gutjahr.despesas.dto.PlanoDTO;
import com.br.gutjahr.despesas.model.Plano;
import com.br.gutjahr.despesas.model.PlanoSaldo;
import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.repositories.PlanoRepository;
import com.br.gutjahr.despesas.repositories.PlanoSaldoRepository;
import com.br.gutjahr.despesas.services.exceptions.DataIntegrityExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PlanoService {

    @Autowired
    private UserService userService;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private PlanoSaldoRepository planoSaldoRepository;

    public List<Plano> findAll(){
        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        Optional<List<Plano>> planos = planoRepository.findByUsuarioOrderByCodContabil(usuario.get());
        return planos.orElse(null);
    }

    public PlanoSaldo findSaldo(Date data, Integer plano_id){
        // busca o plano saldo na data informada
        Optional<PlanoSaldo> planoSaldo = Optional.ofNullable(planoSaldoRepository.findByDataAndPlanoId(data, plano_id))
                .get();
        // se o plano saldo na data informada não for encontrado, busca o registro com a maior data
        if(!planoSaldo.isPresent()){
            planoSaldo = planoSaldoRepository.findTopByDataBeforeAndPlanoIdOrderByDataDesc(data, plano_id);
        }
        // caso não seja encontardo um saldo para a conta é retornado saldo 0
        return planoSaldo.orElse(new PlanoSaldo(data, 0.0));
    }

    public Plano insert(Plano plano){
        Optional<Usuario> usuario = Optional.ofNullable(userService.authencated().get());
        Optional<Plano> plano1 = Optional.ofNullable(planoRepository.findByCodContabilAndUsuario(plano.getCodContabil(),
                usuario.get())).get();
        if(plano1.isPresent()) {
            throw new DataIntegrityExeption("Código contábil já cadastrado");
        }
        plano.setId(null);
        plano.setUsuario(usuario.get());
        for(int i=1;i<plano.getCodContabil().length();i++){
            plano.setNome(' ' + plano.getNome());
        }
        return planoRepository.save(plano);
    }

    public Plano update(Plano plano){
        Plano newPlano = planoRepository.getOne(plano.getId());
        newPlano.setCodContabil(plano.getCodContabil());
        newPlano.setNome(plano.getNome());
        newPlano.setDre(plano.getDre());
        newPlano.setNivel(plano.getNivel());
        for(int i=1;i<plano.getCodContabil().length();i++){
            newPlano.setNome(' ' + newPlano.getNome());
        }
        return planoRepository.save(newPlano);
    }

    public Plano fromDTO(PlanoDTO planoDTO){
        return new Plano(planoDTO.getId(), planoDTO.getCodContabil(), planoDTO.getNome(), planoDTO.getDre(),
                getNivel(planoDTO.getCodContabil()));
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
