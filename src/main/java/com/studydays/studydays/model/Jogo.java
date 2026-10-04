package com.studydays.studydays.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table 
@Getter 
@Setter 
@AllArgsConstructor 
public class Jogo {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String status;

    @ManyToOne 
    @JoinColumn(name = "usuario_id")
    @JsonIgnore 
    private Usuario jogador;

    @ManyToOne 
    @JoinColumn(name = "evento_id")
    private Evento eventoAtual;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "progresso_id")
    private Progresso progressoAtual;

    public Jogo(){ 
        this.status = "INICIADO";
    }
}
