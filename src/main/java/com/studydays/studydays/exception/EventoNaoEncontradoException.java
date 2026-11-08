package com.studydays.studydays.exception;

public class EventoNaoEncontradoException extends RuntimeException{
    public EventoNaoEncontradoException(){
        super("EVENTO_NAO_ENCONTRADO");
    }
}
