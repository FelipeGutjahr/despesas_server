package com.br.gutjahr.despesas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class DespesasApplication {

	public static void main(String[] args) {
		SpringApplication.run(DespesasApplication.class, args);
	}

}
