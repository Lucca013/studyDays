package com.studydays.studydays.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studydays.studydays.model.RecordePessoal;
import com.studydays.studydays.service.RecordePessoalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/RecordePessoal")
public class RecordePessoalController {
    private final RecordePessoalService service;

    public RecordePessoalController(RecordePessoalService service) {
        this.service = service;
    }

    @GetMapping("/listar")
        public List<RecordePessoal> gerarRanking() {
            return service.gerarRanking();
    }
 
    @PostMapping("/cadastrar/usuario/{usuarioId}")
    public ResponseEntity<RecordePessoal> cadastrarRecorde(@RequestBody RecordePessoal recordePessoal, @PathVariable Long usuarioId) {
        RecordePessoal salvo = service.cadastrarRecorde(recordePessoal, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}
