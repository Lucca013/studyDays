package com.studydays.studydays.dto;

import java.util.List;

import com.studydays.studydays.model.Decisao;
import com.studydays.studydays.model.Evento;

public record EventoGeradoResponse(
        Evento evento,
        List<Decisao> decisoes
    ) {}
