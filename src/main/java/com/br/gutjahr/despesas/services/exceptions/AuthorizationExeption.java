package com.br.gutjahr.despesas.services.exceptions;

public class AuthorizationExeption extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public AuthorizationExeption(String message){
        super(message);
    }

    public AuthorizationExeption(String message, Throwable cause){
        super(message, cause);
    }
}