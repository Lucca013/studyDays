package com.studydays.studydays.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity 

public class Melhoria {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @ManyToMany 
    @JoinTable (
        name = "Exige",
        joinColumns = @JoinColumn(name = "melhoria_id"),
        inverseJoinColumns = @JoinColumn(name = "requisito_id")
    )
    List<Requisito> listaRequisitos = new ArrayList<>();
}
