package com.studydays.studydays.exception;

public class AutenticacaoException extends RuntimeException{
    public AutenticacaoException(){
        super("AUTENTICACAO_INVALIDA");
    }
}
