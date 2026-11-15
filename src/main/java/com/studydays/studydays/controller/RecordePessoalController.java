package com.studydays.studydays.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.studydays.studydays.model.RecordePessoal;
import com.studydays.studydays.service.RecordePessoalService;

@RestController
@RequestMapping("/RecordePessoal")
public class RecordePessoalController {
    private final RecordePessoalService service;

    public RecordePessoalController(RecordePessoalService service) {
        this.service = service;
    }

    @GetMapping("/listar")
        public List<RecordePessoal> gerarRanking() {
            return service.gerarRanking();
    }
 
}
