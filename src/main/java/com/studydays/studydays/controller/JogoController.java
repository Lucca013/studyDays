package com.studydays.studydays.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.studydays.studydays.model.Jogo;
import com.studydays.studydays.repository.JogoRepository;
import com.studydays.studydays.repository.UsuarioRepository;

@RestController 
@RequestMapping("/jogo")
public class JogoController {
    private final JogoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public JogoController(JogoRepository repository, UsuarioRepository usuarioRepository){
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/cadastrar/usuario/{usuarioId}")
    public ResponseEntity<Jogo> cadastrarJogo(@RequestBody Jogo jogo, @PathVariable Long usuarioId){
        var usuario = usuarioRepository.findById(usuarioId);

        if(usuario.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        jogo.setId(null);
        jogo.setJogador(usuario.get());
        Jogo salvo = repository.save(jogo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/listar")
    public List<Jogo> listarJogos(){
        return repository.findAll();
    }
}
