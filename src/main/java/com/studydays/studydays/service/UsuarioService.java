package com.studydays.studydays.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studydays.studydays.dto.AutenticadoResponse;
import com.studydays.studydays.exception.AutenticacaoException;
import com.studydays.studydays.exception.NomeJaRegistradoException;
import com.studydays.studydays.exception.UsuarioNaoEncontradoException;
import com.studydays.studydays.model.Usuario;
import com.studydays.studydays.repository.UsuarioRepository;

@Service 
public class UsuarioService {
    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    public AutenticadoResponse listarUsuarioEspecifico(Long usuarioId){
        var usuario = repository.findById(usuarioId).orElseThrow(UsuarioNaoEncontradoException::new);
        var resposta = new AutenticadoResponse(usuario.getId(),usuario.getNome());
        return resposta;
    }

    @Transactional 
    public AutenticadoResponse cadastrarUsuario(Usuario usuario){
        var usuarioExistente = repository.findByNome(usuario.getNome());
        if (usuarioExistente.isPresent()){throw new NomeJaRegistradoException();} 

        usuario.setId(null);

        String senha = usuario.getSenhaHash();
        String senhaCriptografada = passwordEncoder.encode(senha);
        usuario.setSenhaHash(senhaCriptografada);

        repository.save(usuario);
        
        var resposta = new AutenticadoResponse(usuario.getId(), usuario.getNome());
        return resposta;
    }

    public AutenticadoResponse autenticarUsuario(Usuario login){
        var usuario = repository.findByNome(login.getNome()).orElseThrow(AutenticacaoException::new);
        boolean senhaCorreta = passwordEncoder.matches(login.getSenhaHash(),usuario.getSenhaHash());

        if (!senhaCorreta) {
            throw new AutenticacaoException();
        }

        var resposta = new AutenticadoResponse(usuario.getId(),usuario.getNome());
        return resposta;
    }

}
