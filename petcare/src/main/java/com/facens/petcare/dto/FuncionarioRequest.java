package com.facens.petcare.dto;

public record FuncionarioRequest(
        String nome,
        String email,
        String cargo,
        Long setorId
) {
}
