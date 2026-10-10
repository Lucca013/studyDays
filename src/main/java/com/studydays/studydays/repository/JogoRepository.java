package com.studydays.studydays.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.studydays.studydays.model.Jogo;
import com.studydays.studydays.model.Usuario;

public interface JogoRepository extends JpaRepository<Jogo, Long>{
    List<Jogo> findByJogador(Usuario jogador);
}
