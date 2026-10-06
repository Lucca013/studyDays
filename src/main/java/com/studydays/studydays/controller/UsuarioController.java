package com.studydays.studydays.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studydays.studydays.model.Usuario;
import com.studydays.studydays.repository.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioRepository repository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    @GetMapping("/listar/{usuarioId}")
    public ResponseEntity<Usuario> listarUsuarioEspecifico(@PathVariable Long usuarioId) {
        var usuario = repository.findById(usuarioId);

        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario.get());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody Usuario usuario) {
        usuario.setId(null);
        Usuario salvo = repository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // Autentica o usuário (Deixando assim para testes, mas não é uma boa continuar dessa forma)
    @PostMapping("/autenticar")
    public ResponseEntity<Usuario> autenticarUsuario(@Valid @RequestBody Usuario login) {
        var usuario = repository.findByNome(login.getNome());

        if(usuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(!usuario.get().getSenhaHash().equals(login.getSenhaHash())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(usuario.get());
    };
}
