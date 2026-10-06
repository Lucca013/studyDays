package com.studydays.studydays.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.studydays.studydays.model.RecordePessoal;

public interface RecordePessoalRepository extends JpaRepository<RecordePessoal, Long>{
    List<RecordePessoal> findAllByOrderByValorPontuacaoDesc();
}
