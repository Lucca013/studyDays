package com.studydays.studydays.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studydays.studydays.model.RecordePessoal;
import com.studydays.studydays.repository.RecordePessoalRepository;
import com.studydays.studydays.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/RecordePessoal")
public class RecordePessoalController {
    private final RecordePessoalRepository repository;
    private final UsuarioRepository usuarioRepository;

    public RecordePessoalController(RecordePessoalRepository repository, UsuarioRepository usuarioRepository){
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/listar")
    public List<RecordePessoal> listarRecordes(){
        return repository.findAll();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<RecordePessoal> cadastrarRecorde(@RequestBody RecordePessoal recordePessoal){
        recordePessoal.setId(null);
        RecordePessoal salva = repository.save(recordePessoal);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/{recordePessoalId}/jogador/{usuarioId}")
    public ResponseEntity<RecordePessoal> adicionarUsuario(
        @PathVariable Long recordePessoalId,
        @PathVariable Long usuarioId){
            var usuario = usuarioRepository.findById(usuarioId);
            var recordePessoal = repository.findById(recordePessoalId);

            if (usuario.isEmpty() || recordePessoal.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            
            recordePessoal.get().setJogador(usuario.get());
            RecordePessoal atualizado = repository.save(recordePessoal.get());
            return ResponseEntity.ok(atualizado);
        }
}
