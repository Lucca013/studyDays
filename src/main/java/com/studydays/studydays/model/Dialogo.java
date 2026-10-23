package com.studydays.studydays.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@NoArgsConstructor 
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
    private String nomePersonagem;

    @Column (nullable = false)
    private String spritePersonagem;
    // vou tirar isso aqui eventualmente, não agora

    @Column (nullable = false)
    private String movimentacaoPersonagem;

    @Column (nullable = false)
    private int ordemFala;

    @ManyToOne 
    @JoinColumn(name = "evento_id")
    @JsonIgnore 
    private Evento evento;

}
