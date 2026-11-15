package com.studydays.studydays.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.studydays.studydays.model.Jogo;
import com.studydays.studydays.model.RecordePessoal;
import com.studydays.studydays.repository.RecordePessoalRepository;

@Service
public class RecordePessoalService {

    private final RecordePessoalRepository repository;

    public RecordePessoalService(
            RecordePessoalRepository repository) {

        this.repository = repository;
    }

    @Transactional
    public void atualizarRecorde(Jogo jogo) {
        var usuario = jogo.getJogador();
        int pontuacao = jogo.getPontuacaoAtual();

        var recordeExistente = repository.findByJogador(usuario);
        if (recordeExistente.isPresent()) {
            RecordePessoal recorde = recordeExistente.get();

            if (pontuacao >= recorde.getValorPontuacao()) {
                recorde.setValorPontuacao(pontuacao);
                recorde.setDataObtida(LocalDate.now().toString());
                repository.save(recorde);
            }

        } else {
            RecordePessoal novoRecorde = new RecordePessoal();

            novoRecorde.setJogador(usuario);
            novoRecorde.setValorPontuacao(pontuacao);
            novoRecorde.setDataObtida(LocalDate.now().toString());

            repository.save(novoRecorde);
        }
    }

    public List<RecordePessoal> gerarRanking() {
        return repository.findAllByOrderByValorPontuacaoDesc();
    }
}