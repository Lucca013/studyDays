package com.studydays.studydays.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class RecordePessoal {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private int valorPontuacao;

    @Column (nullable = false)
    private String dataObtida;

    @OneToOne 
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"jogos", "senhaHash"}) // para não retornar (1) lista de jogos (2) a senha do usuário na listagem de recordes 
    private Usuario jogador; 

}
