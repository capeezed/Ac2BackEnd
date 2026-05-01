package com.facens.petcare.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjetoRequest(
        String nome,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim,
        BigDecimal orcamento,
        String status
) {
}
