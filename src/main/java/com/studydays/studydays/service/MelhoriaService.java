package com.studydays.studydays.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.studydays.studydays.dto.ListaMelhoriaResponse;
import com.studydays.studydays.dto.MelhoriaAdquiridaResponse;
import com.studydays.studydays.exception.JogoNaoEncontradoException;
import com.studydays.studydays.exception.MelhoriaAdquiridaException;
import com.studydays.studydays.exception.MelhoriaBloqueadaException;
import com.studydays.studydays.model.Melhoria;
import com.studydays.studydays.model.Requisito;
import com.studydays.studydays.repository.JogoRepository;
import com.studydays.studydays.repository.MelhoriaRepository;

@Service 
public class MelhoriaService {
    private final MelhoriaRepository repository;
    private final JogoRepository jogoRepository;

    MelhoriaService(MelhoriaRepository repository, JogoRepository jogoRepository) {
        this.repository = repository;
        this.jogoRepository = jogoRepository;
    }

    public List<ListaMelhoriaResponse> exibirArvoreMelhoria(Long jogoId){
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
                var melhoriaAnterior = repository.findById((long) requisito.getValor()).orElse(null);

                if (melhoriaAnterior != null) {
                    ordem = Math.max(ordem, calcularOrdem(melhoriaAnterior) + 1);
                }
            }
        }

        return ordem;
    }

    public MelhoriaAdquiridaResponse liberarMelhoria(Long melhoriaId, Long jogoId){
        var jogo = jogoRepository.findById(jogoId).orElseThrow(JogoNaoEncontradoException::new);
        var melhoria = repository.findById(melhoriaId).orElseThrow();

        boolean melhoriaAdquirida = false;
        for(Melhoria melhoriaBanco : jogo.getListaMelhorias()){
            if(melhoriaBanco.getId().equals(melhoriaId)){
                melhoriaAdquirida = true;
                break;
            }
        }
        if(melhoriaAdquirida){
            throw new MelhoriaAdquiridaException();
        }

        if(!melhoria.verificarRequisitos(jogo)){
            throw new MelhoriaBloqueadaException();
        }

        if(jogo.getPontoMelhoria() >= 1){
            jogo.setPontoMelhoria((jogo.getPontoMelhoria() - 1));
        }
        else{
            throw new MelhoriaBloqueadaException();
        }
        jogo.getListaMelhorias().add(melhoria);
        jogoRepository.save(jogo);

        var resposta = new MelhoriaAdquiridaResponse(
            jogo.getPontoMelhoria(),
            melhoria
        );

        return resposta;
    }

}
