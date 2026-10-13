package com.studydays.studydays.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studydays.studydays.dto.ListaMelhoriaResponse;
import com.studydays.studydays.exception.JogoNaoEncontradoException;
import com.studydays.studydays.model.Melhoria;
import com.studydays.studydays.model.Requisito;
import com.studydays.studydays.repository.JogoRepository;
import com.studydays.studydays.repository.MelhoriaRepository;

@RestController
@RequestMapping("/melhoria")
public class MelhoriaController {
    private final MelhoriaRepository repository;
    private final JogoRepository jogoRepository;

    MelhoriaController(MelhoriaRepository repository, JogoRepository jogoRepository) {
        this.repository = repository;
        this.jogoRepository = jogoRepository;
    }

    @GetMapping("/listar/{jogoId}")
    public List<ListaMelhoriaResponse> exibirArvoreMelhoria(@PathVariable Long jogoId) {
        var jogo = jogoRepository.findById(jogoId).orElseThrow(JogoNaoEncontradoException::new);

        List<ListaMelhoriaResponse> resposta = new ArrayList<>();

        for (Melhoria melhoria : repository.findAll()) {
            boolean adquirida = jogo.getListaMelhorias().stream().anyMatch(m -> m.getId().equals(melhoria.getId()));
            String status;

            if (adquirida) {
                status = "ADQUIRIDO";
            } else if (melhoria.verificarRequisitos(jogo)) {
                status = "DISPONIVEL";
            } else {
                status = "BLOQUEADO";
            }

            resposta.add(new ListaMelhoriaResponse(
                            melhoria.getId(),
                            melhoria.getNome(),
                            melhoria.getDescricao(),
                            status,
                            calcularOrdem(melhoria)));
        }
        return resposta;
    }

    private int calcularOrdem(Melhoria melhoria) {

        int ordem = 0;

        for (Requisito requisito : melhoria.getListaRequisitos()) {

            if (requisito.getTipo().equals("MELHORIA_ADQUIRIDA")) {

                var melhoriaAnterior = repository
                        .findById((long) requisito.getValor())
                        .orElse(null);

                if (melhoriaAnterior != null) {
                    ordem = Math.max(
                            ordem,
                            calcularOrdem(melhoriaAnterior) + 1);
                }
            }
        }

        return ordem;
    }
}
