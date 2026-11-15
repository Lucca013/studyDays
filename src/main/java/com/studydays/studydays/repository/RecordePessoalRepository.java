package com.studydays.studydays.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.studydays.studydays.model.RecordePessoal;
import com.studydays.studydays.model.Usuario;

public interface RecordePessoalRepository extends JpaRepository<RecordePessoal, Long>{
    List<RecordePessoal> findAllByOrderByValorPontuacaoDesc();
    Optional<RecordePessoal> findByJogador(Usuario jogador);
}
