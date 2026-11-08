package com.studydays.studydays.exception;

public class JogoFinalizadoException extends RuntimeException{
    public JogoFinalizadoException(){
        super("JOGO_JA_FINALIZADO");
    }
}
