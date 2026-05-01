package com.facens.petcare.dto;

public record VeterinarioRequest(
        String nome,
        String crmv,
        String especialidade
) {
}
