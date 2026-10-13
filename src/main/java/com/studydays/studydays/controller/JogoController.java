package com.studydays.studydays.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studydays.studydays.exception.JogoNaoEncontradoException;
import com.studydays.studydays.exception.UsuarioNaoEncontradoException;
import com.studydays.studydays.model.Consequencia;
import com.studydays.studydays.model.Decisao;
import com.studydays.studydays.model.Evento;
import com.studydays.studydays.model.Jogo;
import com.studydays.studydays.dto.ProcessarEscolhaResponse;
import com.studydays.studydays.dto.EventoGeradoResponse;
import com.studydays.studydays.repository.ConsequenciaRepository;
import com.studydays.studydays.repository.EventoRepository;
import com.studydays.studydays.repository.JogoRepository;
import com.studydays.studydays.repository.UsuarioRepository;

@RestController
@RequestMapping("/jogo")
public class JogoController {
    private final JogoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final ConsequenciaRepository consequenciaRepository;

    public JogoController(JogoRepository repository, UsuarioRepository usuarioRepository,
            EventoRepository eventoRepository, ConsequenciaRepository consequenciaRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.consequenciaRepository = consequenciaRepository;
    }

    @PostMapping("/cadastrar/usuario/{usuarioId}")
    public ResponseEntity<Jogo> iniciarNovoJogo(@PathVariable Long usuarioId) {
        var usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNaoEncontradoException::new);
        var evento = eventoRepository.findById(1L);

        if (evento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Jogo jogo = new Jogo();
        jogo.setJogador(usuario);
        jogo.setEventoAtual(evento.get());
        Jogo salvo = repository.save(jogo);

        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/listar")
    public List<Jogo> listarTodosJogos() {
        return repository.findAll();
    }

    @GetMapping("/listar/{jogoId}")
    public ResponseEntity<Jogo> listarJogoEspecifico(@PathVariable Long jogoId) {
        var jogo = repository.findById(jogoId).orElseThrow(JogoNaoEncontradoException::new);
        return ResponseEntity.ok(jogo);
    }

    @GetMapping("/listar/usuario/{usuarioId}")
    public List<Jogo> listarJogos(@PathVariable  Long usuarioId) {
        var usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNaoEncontradoException::new);
        return repository.findByJogador(usuario);
    }

    @PostMapping("/avancarEvento/{jogoId}")
    public ResponseEntity<EventoGeradoResponse> avancarEvento(@PathVariable Long jogoId) {
        var jogo = repository.findById(jogoId).orElseThrow(JogoNaoEncontradoException::new);

        if (jogo.getStatus().equals("FINALIZADO")) {
            return ResponseEntity.badRequest().build();
        }

        List<Evento> todosEventos = eventoRepository.findAll();
        List<Evento> eventosValidos = new ArrayList<>();

        for (Evento evento : todosEventos) {
            if (evento.verificarRequisitos(jogo)) {
                eventosValidos.add(evento);
            }
        }

        if (eventosValidos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Evento eventoSorteado = jogo.sortearEvento(eventosValidos);
        jogo.setEventoAtual(eventoSorteado);

        List<Consequencia> consequencias = consequenciaRepository.findByEventoId(eventoSorteado.getId());

        List<Decisao> decisoesValidas = new ArrayList<>();

        for (Consequencia consequencia : consequencias) {

            Decisao decisao = consequencia.getDecisao();

            if (decisao.verificarRequisitos(jogo)) {
                decisoesValidas.add(decisao);
            }
        }

        List<Decisao> decisoesSorteadas = new ArrayList<>();
        while (!decisoesValidas.isEmpty() && decisoesSorteadas.size() < 3) {

            Decisao decisaoSorteada = jogo.sortearDecisao(decisoesValidas);

            decisoesSorteadas.add(decisaoSorteada);
            decisoesValidas.remove(decisaoSorteada);
        }

        repository.save(jogo);
        EventoGeradoResponse resposta = new EventoGeradoResponse(eventoSorteado, decisoesSorteadas);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{jogoId}/escolha/{escolhaId}")
    public ResponseEntity<ProcessarEscolhaResponse> processarEscolha(@PathVariable Long jogoId, @PathVariable Long escolhaId){
        var jogo = repository.findById(jogoId).orElseThrow(JogoNaoEncontradoException::new);
        var evento = jogo.getEventoAtual();

        var consequencia = consequenciaRepository.findByEventoIdAndDecisaoId(evento.getId(), escolhaId).orElseThrow();
        jogo.setPontuacaoAtual(jogo.getPontuacaoAtual() + consequencia.getValorPontuacao());
        repository.save(jogo);

        var resposta = new ProcessarEscolhaResponse(
            consequencia.getValorPontuacao(),
            consequencia.getDescricao(),
            jogo.getPontuacaoAtual()
        );

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{jogoId}/escolhas")
    public ResponseEntity<List<Decisao>> listarDecisoesValidas(@PathVariable Long jogoId) {

        var jogo = repository.findById(jogoId).orElseThrow(JogoNaoEncontradoException::new);

        var eventoAtual = jogo.getEventoAtual();

        List<Consequencia> consequencias = consequenciaRepository.findByEventoId(eventoAtual.getId());
        List<Decisao> decisoesValidas = new ArrayList<>();

        for (Consequencia consequencia : consequencias) {
            Decisao decisao = consequencia.getDecisao();

            if (decisao.verificarRequisitos(jogo) && !decisoesValidas.contains(decisao)) {
                decisoesValidas.add(decisao);
            }
        }
        return ResponseEntity.ok(decisoesValidas);
    }
}
