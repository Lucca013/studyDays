package com.studydays.studydays.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario jogador;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento eventoAtual;

    @Column(nullable = false)
    private int pontoMelhoria;

    @Column(nullable = false)
    private int pontuacaoAtual;

    @Column(nullable = false)
    private int faseAtual;

    @Column(nullable = false)
    private int turnoAtual;

    @ManyToMany 
    @JoinTable (
        name = "Liberou",
        joinColumns = @JoinColumn(name = "jogo_id"),
        inverseJoinColumns = @JoinColumn(name = "melhoria_id")
    )
    private List<Melhoria> listaMelhorias= new ArrayList<>();

    public Evento sortearEvento(List<Evento> eventosValidos){
        if(eventosValidos.isEmpty()){
            return null;
        }
        Random random = new Random();
        int posicao = random.nextInt(eventosValidos.size());
        return eventosValidos.get(posicao);
    }

    public Decisao sortearDecisao(List<Decisao> decisoesValidas) {
        if (decisoesValidas.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int posicao = random.nextInt(decisoesValidas.size());
        return decisoesValidas.get(posicao);
    }

    public Jogo() {
        this.status = "INICIADO";
        this.pontoMelhoria = 0;
        this.pontuacaoAtual = 0;
        this.faseAtual = 0;
        this.turnoAtual = 0;
        this.listaMelhorias = null;
    }

    public record EventoGeradoResponse(
        Evento evento,
        List<Decisao> decisoes
    ) {}
}