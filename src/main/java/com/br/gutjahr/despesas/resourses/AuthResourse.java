package com.br.gutjahr.despesas.resourses;

import com.br.gutjahr.despesas.model.Usuario;
import com.br.gutjahr.despesas.security.JWTUtil;
import com.br.gutjahr.despesas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping(value = "/auth")
public class AuthResourse {

    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value="/refresh_token", method= RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        Optional<Usuario> usuario = userService.authencated();
        String token = jwtUtil.generateToken(usuario.get().getEmail());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }
}
