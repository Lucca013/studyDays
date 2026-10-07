package com.studydays.studydays.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studydays.studydays.exception.AutenticacaoException;
import com.studydays.studydays.exception.NomeJaRegistradoException;
import com.studydays.studydays.exception.UsuarioNaoEncontradoException;
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
        var usuario = repository.findById(usuarioId).orElseThrow(UsuarioNaoEncontradoException::new);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody Usuario usuario) {
        var usuarioExistente = repository.findByNome(usuario.getNome());
        if (usuarioExistente.isPresent()){throw new NomeJaRegistradoException();} 
        // Se for um nome já cadastrado antes, joga a exception (retorna um erro do banco se não tratar aqui)

        usuario.setId(null);
        Usuario salvo = repository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // Autentica o usuário 
    @PostMapping("/autenticar")
    public ResponseEntity<Usuario> autenticarUsuario(@Valid @RequestBody Usuario login) {
        var usuario = repository.findByNome(login.getNome()).orElseThrow(AutenticacaoException::new);
        if(!usuario.getSenhaHash().equals(login.getSenhaHash())){throw new AutenticacaoException();}
        return ResponseEntity.ok(usuario);
    };
}
