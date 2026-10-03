package com.studydays.studydays.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studydays.studydays.model.RecordePessoal;
import com.studydays.studydays.repository.RecordePessoalRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/RecordePessoal")
public class RecordePessoalController {
    private final RecordePessoalRepository repository;
    
    public RecordePessoalController(RecordePessoalRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<RecordePessoal> listar(){
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<RecordePessoal> cadastrar(@RequestBody RecordePessoal recordePessoal){
        recordePessoal.setId(null);
        RecordePessoal salva = repository.save(recordePessoal);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }
}
