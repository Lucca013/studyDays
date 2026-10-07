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
public class Decisao {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @ManyToMany 
    @JoinTable (
        name = "RequisitosDecisao",
        joinColumns = @JoinColumn(name = "decisao_id"),
        inverseJoinColumns = @JoinColumn(name = "requisito_id")
    )
    List<Requisito> listaRequisitos = new ArrayList<>();

}   
