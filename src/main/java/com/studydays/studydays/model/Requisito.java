package com.studydays.studydays.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Requisito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private int valor;

    public boolean validar(Jogo jogo) {
        switch (tipo) {
            case "FASE_MINIMA":
                return jogo.getFaseAtual() >= valor;

            case "TURNO_MINIMO":
                return jogo.getTurnoAtual() >= valor;

            case "PONTUACAO_MINIMA":
                return jogo.getPontuacaoAtual() >= valor;

            case "PONTOS_MELHORIA_MINIMO":
                return jogo.getPontoMelhoria() >= valor;

            default:
                return false;
        }
    }
}