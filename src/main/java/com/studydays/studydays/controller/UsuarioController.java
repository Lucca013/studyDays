package com.studydays.studydays.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studydays.studydays.dto.AutenticadoResponse;
import com.studydays.studydays.model.Usuario;
import com.studydays.studydays.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/listar/{usuarioId}")
    public ResponseEntity<AutenticadoResponse> listarUsuarioEspecifico(@PathVariable Long usuarioId) {
        AutenticadoResponse resposta = service.listarUsuarioEspecifico(usuarioId);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<AutenticadoResponse> cadastrarUsuario(@RequestBody Usuario usuario) {
        AutenticadoResponse resposta = service.cadastrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PostMapping("/autenticar")
    public ResponseEntity<AutenticadoResponse> autenticarUsuario(@RequestBody Usuario login) {
        AutenticadoResponse resposta = service.autenticarUsuario(login);
        return ResponseEntity.ok(resposta);
    };
}
