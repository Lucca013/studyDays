package com.studydays.studydays.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studydays.studydays.model.Consequencia;

public interface ConsequenciaRepository extends JpaRepository<Consequencia, Long>{
    List<Consequencia> findByEventoId(Long eventoId);
}
