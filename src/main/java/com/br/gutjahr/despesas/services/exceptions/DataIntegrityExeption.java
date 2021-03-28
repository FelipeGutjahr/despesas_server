package com.br.gutjahr.despesas.services.exceptions;

public class DataIntegrityExeption extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public DataIntegrityExeption(String mensagem){
        super(mensagem);
    }

    public DataIntegrityExeption(String mensagem, Throwable cause){
        super(mensagem, cause);
    }
}
