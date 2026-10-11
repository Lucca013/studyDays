package com.studydays.studydays.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@NoArgsConstructor 
@AllArgsConstructor 
@Getter 
@Setter 
@Table 
@Entity 
public class Dialogo {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fala;

    @Column(nullable = false)
    private String nome_personagem;

    @Column (nullable = false)
    private String sprite_personagem;

    @Column (nullable = false)
    private String movimentacao_personagem;

    @Column (nullable = false)
    private int ordem_fala;

    @ManyToOne 
    @JoinColumn(name = "evento_id")
    private Evento evento;

}
