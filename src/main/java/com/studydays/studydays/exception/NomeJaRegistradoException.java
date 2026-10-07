package com.studydays.studydays.exception;

public class NomeJaRegistradoException extends RuntimeException{
    public NomeJaRegistradoException(){
        super("NOME_DE_USUARIO_JA_UTILIZADO");
    }
}
