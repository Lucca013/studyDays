package com.studydays.studydays.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // só para testes, definitivamente não preciso de uma função assim depois 
    @GetMapping("/listar")
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody Usuario usuario) {
        usuario.setId(null); 
        Usuario salvo = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
    // não dá para mudar o tipo de retorno dessa função para ser Boolean?
    // vai bater um pouco melhor com o diagrama de classes 

    @PostMapping("/autenticarUsuario")
    public ResponseEntity<Boolean> autenticarUsuario(@RequestBody Usuario usuario){
        Usuario usuarioBanco = usuarioRepository.findByNome(usuario.getNome()).orElse(null);

        if(usuarioBanco == null){
            return ResponseEntity.ok(false);
        }

        boolean autenticado = usuarioBanco.getSenhaHash().equals(usuario.getSenhaHash());
        // stack de funções, .getSenhaHash passa a string para .equals que compara com o resultado de outro .getSenhaHash
        return ResponseEntity.ok(autenticado);
    }
}
