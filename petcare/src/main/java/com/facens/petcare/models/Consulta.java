package com.facens.petcare.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;
    private String motivo;
    private String status;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    @JsonIgnoreProperties({"consultas", "prontuarios", "vacinacoes"})
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    @JsonIgnoreProperties("consultas")
    private Veterinario veterinario;
}
