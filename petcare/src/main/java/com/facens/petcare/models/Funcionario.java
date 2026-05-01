package com.facens.petcare.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String cargo;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    @JsonIgnoreProperties("funcionarios")
    private Setor setor;

    @ManyToMany(mappedBy = "funcionarios")
    @JsonIgnoreProperties("funcionarios")
    private List<Projeto> projetos = new ArrayList<>();
}
