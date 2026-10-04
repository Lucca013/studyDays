package com.studydays.studydays.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table 
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
public class Progresso {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int pontoMelhoria;

    @Column(nullable = false)
    private int pontuacaoAtual;

    @Column(nullable = false)
    private int faseAtual;

    @Column(nullable = false)
    private int turnoAtual;

    @OneToOne(mappedBy = "progressoAtual")
    @JsonIgnore 
    private Jogo jogo;

    public Progresso(Jogo jogo){
        this.pontoMelhoria = 0;
        this.pontuacaoAtual = 0;
        this.faseAtual = 0;
        this.turnoAtual = 0;
        this.jogo = jogo;
    }
}
