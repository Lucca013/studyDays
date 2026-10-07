package com.studydays.studydays.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> tratarErro(RuntimeException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<String> tratarUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(AutenticacaoException.class)
    public ResponseEntity<String> tratarAutenticacao(AutenticacaoException ex){
        return ResponseEntity.status(401).body(ex.getMessage());
    }

    @ExceptionHandler(NomeJaRegistradoException.class)
    public ResponseEntity<String> tratarUsuarioJaExistente(NomeJaRegistradoException ex){
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(JogoNaoEncontradoException.class)
    public ResponseEntity<String> tratarJogoNaoEncontrado(JogoNaoEncontradoException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
