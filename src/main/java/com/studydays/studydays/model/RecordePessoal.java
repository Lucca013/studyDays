package com.studydays.studydays.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class RecordePessoal {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private int valorPontuacao;

    /* @Column (nullable = false)
    private Usuario jogador; */

}
