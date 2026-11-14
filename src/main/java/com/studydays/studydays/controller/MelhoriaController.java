package com.studydays.studydays.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.studydays.studydays.dto.ListaMelhoriaResponse;
import com.studydays.studydays.dto.MelhoriaAdquiridaResponse;
import com.studydays.studydays.service.MelhoriaService;

@RestController
@RequestMapping("/melhoria")
public class MelhoriaController {
    private final MelhoriaService service;

    MelhoriaController(MelhoriaService service) {
        this.service = service;
    } 

    @GetMapping("/listar/{jogoId}")
    public ResponseEntity<List<ListaMelhoriaResponse>> exibirArvoreMelhoria(@PathVariable Long jogoId) {
        return ResponseEntity.ok(service.exibirArvoreMelhoria(jogoId));
    }

    @PostMapping("/liberar/{melhoriaId}/jogo/{jogoId}")
    public ResponseEntity<MelhoriaAdquiridaResponse> liberarMelhoria(@PathVariable Long melhoriaId, @PathVariable Long jogoId){
        MelhoriaAdquiridaResponse resposta = service.liberarMelhoria(melhoriaId, jogoId);
        return ResponseEntity.ok(resposta);
    }
}
