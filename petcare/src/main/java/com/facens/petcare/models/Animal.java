package com.facens.petcare.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especie;
    private String raca;
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @JsonIgnoreProperties("animais")
    private Tutor tutor;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("animal")
    private List<Consulta> consultas = new ArrayList<>();

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("animal")
    private List<Prontuario> prontuarios = new ArrayList<>();

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("animal")
    private List<Vacinacao> vacinacoes = new ArrayList<>();
}
