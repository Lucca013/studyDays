package com.studydays.studydays.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studydays.studydays.dto.EventoGeradoResponse;
import com.studydays.studydays.dto.JogoResponse;
import com.studydays.studydays.dto.ProcessarEscolhaResponse;
import com.studydays.studydays.exception.EventoNaoEncontradoException;
import com.studydays.studydays.exception.JogoFinalizadoException;
import com.studydays.studydays.exception.JogoNaoEncontradoException;
import com.studydays.studydays.exception.UsuarioNaoEncontradoException;
import com.studydays.studydays.model.Consequencia;
import com.studydays.studydays.model.Decisao;
import com.studydays.studydays.model.Evento;
import com.studydays.studydays.model.Jogo;
import com.studydays.studydays.repository.ConsequenciaRepository;
import com.studydays.studydays.repository.EventoRepository;
import com.studydays.studydays.repository.JogoRepository;
import com.studydays.studydays.repository.UsuarioRepository;

@Service 
public class JogoService {
    private final JogoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final ConsequenciaRepository consequenciaRepository;

    public JogoService(JogoRepository repository, UsuarioRepository usuarioRepository, EventoRepository eventoRepository, ConsequenciaRepository consequenciaRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.consequenciaRepository = consequenciaRepository;
    }

    @Transactional 
    public Jogo iniciarNovoJogo(Long usuarioId) {
        var usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNaoEncontradoException::new);
        var evento = eventoRepository.findById(1L).orElseThrow(EventoNaoEncontradoException::new);
        Jogo jogo = new Jogo();
        jogo.setJogador(usuario);
        jogo.setEventoAtual(evento);
        Jogo salvo = repository.save(jogo);
        return salvo;
    }

    public JogoResponse listarJogoEspecifico(Long jogoId) {
        var jogo = repository.findById(jogoId).orElseThrow(JogoNaoEncontradoException::new);

        var resposta = new JogoResponse(
                jogo.getEventoAtual(),
                jogo.getPontoMelhoria(),
                jogo.getPontuacaoAtual(),
                jogo.getListaMelhorias());
        return resposta;
    }

    public List<Jogo> listarJogos(Long usuarioId) {
        var usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNaoEncontradoException::new);
        var todosJogos = repository.findByJogador(usuario);
        List<Jogo> jogosValidos = new ArrayList<>();

        for(Jogo jogo : todosJogos){
            if (!"FINALIZADO".equals(jogo.getStatus())) {
                jogosValidos.add(jogo);
            }
        }

        return jogosValidos;
    }

    @Transactional 
    public EventoGeradoResponse avancarEvento(Long jogoId) {
        var jogo = repository.findById(jogoId).orElseThrow(JogoNaoEncontradoException::new);
        
        if (jogo.getStatus().equals("FINALIZADO")) {
            throw new JogoFinalizadoException();
        }

        Evento evento = jogo.getEventoAtual();

        if (evento.isRecompensaPontoMelhoria()) {
            jogo.setPontoMelhoria(
                jogo.getPontoMelhoria() + 1
            );
        }

        jogo.setTurnoAtual(jogo.getTurnoAtual() + 1);

        List<Evento> todosEventos = eventoRepository.findAll();
        List<Evento> eventosValidos = new ArrayList<>();
        List<Evento> eventosObrigatorios = new ArrayList<>();

        for (Evento eventoLista : todosEventos) {
            if (eventoLista.verificarRequisitos(jogo)) {
                if (eventoLista.isObrigatorio()) {
                    eventosObrigatorios.add(eventoLista);
                } else {
                    eventosValidos.add(eventoLista);
                }
            }
        }

        if (eventosObrigatorios.isEmpty() && eventosValidos.isEmpty()) {
            throw new EventoNaoEncontradoException();
        }

        Evento eventoSorteado;

        if (!eventosObrigatorios.isEmpty()) {
            eventoSorteado = jogo.sortearEvento(eventosObrigatorios);
        } else {
            eventoSorteado = jogo.sortearEvento(eventosValidos);
        }

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

        if (eventoSorteado.isFinalizaJogo()) {
            jogo.setStatus("FINALIZADO");
        }

        repository.save(jogo);

        EventoGeradoResponse resposta = new EventoGeradoResponse(eventoSorteado,decisoesSorteadas);
        return resposta;
    }

    @Transactional 
    public ProcessarEscolhaResponse processarEscolha(Long jogoId, Long escolhaId) {
        var jogo = repository.findById(jogoId).orElseThrow(JogoNaoEncontradoException::new);
        var evento = jogo.getEventoAtual();

        var consequencia = consequenciaRepository.findByEventoIdAndDecisaoId(evento.getId(), escolhaId).orElseThrow();
        jogo.setPontuacaoAtual(jogo.getPontuacaoAtual() + consequencia.getValorPontuacao());
        repository.save(jogo);

        var resposta = new ProcessarEscolhaResponse(
                consequencia.getValorPontuacao(),
                consequencia.getDescricao(),
                jogo.getPontuacaoAtual());

        return resposta;
    }

    public List<Decisao> listarDecisoesValidas(Long jogoId) {
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
        return decisoesValidas;
    }


    public String obterDica(Long jogoId){
        var jogo = repository.findById(jogoId).orElseThrow(JogoNaoEncontradoException::new);
        List<String> dicasPossiveis = new ArrayList<>();

        if (jogo.getPontoMelhoria() > 0) {
            dicasPossiveis.add(
                (
                    "Você possui pontos de melhoria disponíveis. Considere utilizá-los antes de continuar."
                )
            );
        }

        if (jogo.getPontuacaoAtual() < 0) {
            dicasPossiveis.add(
                (
                    "Sua pontuação está baixa. Nas próximas decisões, procure analisar com mais atenção quais estratégias de estudo são mais eficientes."
                )
            );
        }

        if (jogo.getEventoAtual().getDica() != null) {
            dicasPossiveis.add(jogo.getEventoAtual().getDica());
        }

        if (dicasPossiveis.isEmpty()) {
            return null;
        }
    
        Random random = new Random();
        String dica = dicasPossiveis.get(random.nextInt(dicasPossiveis.size()));
        
        return dica;
    }
}
