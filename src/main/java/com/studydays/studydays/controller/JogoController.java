package com.studydays.studydays.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.studydays.studydays.model.Jogo;
import com.studydays.studydays.repository.JogoRepository;

@RestController 
@RequestMapping("/jogo")
public class JogoController {
    private final JogoRepository repository;

    public JogoController(JogoRepository repository){
        this.repository = repository;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Jogo> cadastrarJogo(@RequestBody Jogo jogo){
        jogo.setId(null);
        Jogo salvo = repository.save(jogo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/listar")
    public List<Jogo> listarJogos(){
        return repository.findAll();
    }
}
