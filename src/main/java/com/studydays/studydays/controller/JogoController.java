package com.studydays.studydays.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.studydays.studydays.model.Jogo;
import com.studydays.studydays.model.Progresso;
import com.studydays.studydays.repository.EventoRepository;
import com.studydays.studydays.repository.JogoRepository;
import com.studydays.studydays.repository.UsuarioRepository;

@RestController 
@RequestMapping("/jogo")
public class JogoController {
    private final JogoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public JogoController(JogoRepository repository, UsuarioRepository usuarioRepository, EventoRepository eventoRepository){
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    @PostMapping("/cadastrar/usuario/{usuarioId}")
    public ResponseEntity<Jogo> iniciarNovoJogo(@PathVariable Long usuarioId){
        var usuario = usuarioRepository.findById(usuarioId);
        var evento = eventoRepository.findById(1L);

        if(usuario.isEmpty() || evento.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        // cria um novo jogo, vincula o usuário ao jogo, pega o primeiro evento do banco e vincula, cria um novo objeto progresso e vincula
        Jogo jogo = new Jogo();
        jogo.setId(null);
        jogo.setJogador(usuario.get());
        jogo.setEventoAtual(evento.get());
        Progresso progresso = new Progresso(jogo);
        jogo.setProgressoAtual(progresso);
        Jogo salvo = repository.save(jogo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/listar")
    public List<Jogo> listarJogos(){
        return repository.findAll();
    }
}
