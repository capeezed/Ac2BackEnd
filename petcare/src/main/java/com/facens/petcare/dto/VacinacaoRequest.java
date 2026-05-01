package com.facens.petcare.dto;

import java.time.LocalDate;

public record VacinacaoRequest(
        String vacina,
        LocalDate dataAplicacao,
        LocalDate proximaDose,
        String observacoes
) {
}
