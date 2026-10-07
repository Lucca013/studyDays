package com.studydays.studydays.exception;

public class JogoNaoEncontradoException extends RuntimeException{
    public JogoNaoEncontradoException(){
        super("JOGO_NAO_ENCONTRADO");
    }
}
