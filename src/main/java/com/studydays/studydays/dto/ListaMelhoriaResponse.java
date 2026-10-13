package com.studydays.studydays.dto;

public record ListaMelhoriaResponse(
    Long id,
    String nome,
    String descricao, 
    String status,
    int ordem) {}
