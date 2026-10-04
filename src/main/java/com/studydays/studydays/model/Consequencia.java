package com.studydays.studydays.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class Consequencia {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int valorPontuacao;
    
    @Column(nullable = false)
    private String descricao;

    @ManyToOne 
    @JoinColumn(name = "decisao_id")
    private Decisao decisao;

    @ManyToOne 
    @JoinColumn(name = "evento_id")
    private Evento evento;
}
