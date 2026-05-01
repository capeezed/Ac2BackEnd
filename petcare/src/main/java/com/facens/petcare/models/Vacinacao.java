package com.facens.petcare.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vacinacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vacina;
    private LocalDate dataAplicacao;
    private LocalDate proximaDose;
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    @JsonIgnoreProperties({"consultas", "prontuarios", "vacinacoes"})
    private Animal animal;
}
