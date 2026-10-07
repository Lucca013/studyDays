package com.studydays.studydays.exception;

public class UsuarioNaoEncontradoException extends RuntimeException{
    public UsuarioNaoEncontradoException(){
        super("USUARIO_NAO_ENCONTRADO");
    }
}
