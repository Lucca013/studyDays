package com.studydays.studydays.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.studydays.studydays.exception.UsuarioNaoEncontradoException;
import com.studydays.studydays.model.RecordePessoal;
import com.studydays.studydays.repository.RecordePessoalRepository;
import com.studydays.studydays.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class RecordePessoalService {
    private final RecordePessoalRepository repository;
    private final UsuarioRepository usuarioRepository;

    public RecordePessoalService(RecordePessoalRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public RecordePessoal cadastrarRecorde(RecordePessoal recordePessoal, Long usuarioId){
        var usuario = usuarioRepository.findById(usuarioId).orElseThrow(UsuarioNaoEncontradoException::new);

        recordePessoal.setId(null);
        recordePessoal.setJogador(usuario);

        RecordePessoal salvo = repository.save(recordePessoal);
        return salvo;
    }

    public List<RecordePessoal> gerarRanking(){
        return repository.findAllByOrderByValorPontuacaoDesc();
    }


}
