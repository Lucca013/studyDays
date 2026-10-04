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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(nullable = false)
    private int pontoMelhoria;

    @Column(nullable = false)
    private int pontuacaoAtual;

    @Column(nullable = false)
    private int faseAtual;

    @Column(nullable = false)
    private int turnoAtual;

    public Jogo(){ // o ponto inicial de todas as partidas é o mesmo, é redundante enviar essas informações via API para cadastro do jogo
        this.status = "INICIADO";
        this.pontoMelhoria = 0;
        this.pontuacaoAtual = 0;
        this.faseAtual = 0;
        this.turnoAtual = 0;
    }

    // futuramente, para implementar a lógica do jogo, também será adicionado:
    // uma referência a classe "Evento" e "Progresso" 
}
