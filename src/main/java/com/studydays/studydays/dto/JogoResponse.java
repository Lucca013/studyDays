package com.studydays.studydays.dto;

import java.util.List;

import com.studydays.studydays.model.Evento;
import com.studydays.studydays.model.Melhoria;

public record JogoResponse(
        Evento eventoAtual,
        int pontoMelhoria,
        int pontuacaoAtual,
        List<Melhoria> listaMelhorias
) {}
