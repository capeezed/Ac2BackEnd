package com.facens.petcare.dto;

public record ProntuarioRequest(
        String descricao,
        String diagnostico,
        String tratamento
) {
}
