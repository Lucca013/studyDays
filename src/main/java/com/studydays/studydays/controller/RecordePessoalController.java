package com.studydays.studydays.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studydays.studydays.model.RecordePessoal;
import com.studydays.studydays.repository.RecordePessoalRepository;
import com.studydays.studydays.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/RecordePessoal")
public class RecordePessoalController {
    private final RecordePessoalRepository repository;
    private final UsuarioRepository usuarioRepository;

    public RecordePessoalController(RecordePessoalRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    // Lista todos os recordes de todos os jogadores
    // dava p limitar p top 100 tambem, coisa assim
    @GetMapping("/listar")
        public List<RecordePessoal> listarRecordes() {
            return repository.findAllByOrderByValorPontuacaoDesc();
    }

    // Cadastra um novo recorde pessoal (já vinculando com um ususário) 
    // n da certo p atualizar, uso um novo endpoint ou trato isso aqui? 
    @PostMapping("/cadastrar/usuario/{usuarioId}")
    public ResponseEntity<RecordePessoal> cadastrarRecorde(@RequestBody RecordePessoal recordePessoal, @PathVariable Long usuarioId) {
        var usuario = usuarioRepository.findById(usuarioId);
        if(usuario.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        recordePessoal.setId(null);
        recordePessoal.setJogador(usuario.get());

        RecordePessoal salvo = repository.save(recordePessoal);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}
