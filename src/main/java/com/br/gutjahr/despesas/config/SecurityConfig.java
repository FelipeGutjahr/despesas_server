package com.br.gutjahr.despesas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // necessário adicionar as dependências spring-boot-starter-security e jjwt no pom.xml

    // endpoints que vão estar liberados por padrão
    private static final String[] PUBLIC_MATCHERS = {
            "/login"
    };


    // exige autenticação para todos os endpoints que não estão em PUBLIC_MATCHERS
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* adiciona o cors e desativa a proteção a ataques CSRF, que é baseado em sessões armazenadas, como não terá
        * sessão armazenada, pode ser desabilitado */
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated();
        // configuração para assegurar que o servidor não vai criar uma sessão de usuário
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    // permitindo acesso básico aos endipoints por múltiplas fontes
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
