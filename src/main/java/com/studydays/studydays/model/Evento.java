package com.studydays.studydays.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table 
@Entity 
@NoArgsConstructor 
@AllArgsConstructor
@Getter 
@Setter  
public class Evento {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String titulo;

    @OneToMany(mappedBy = "eventoAtual")
    @JsonIgnore 
    private List<Jogo> jogos = new ArrayList<>();

    @ManyToMany 
    @JoinTable (
        name = "RequisitosEvento",
        joinColumns = @JoinColumn(name = "evento_id"),
        inverseJoinColumns = @JoinColumn(name = "requisito_id")
    )
    List<Requisito> listaRequisitos = new ArrayList<>();
}
