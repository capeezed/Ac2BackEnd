package com.facens.petcare.dto;

import java.time.LocalDateTime;

public record ConsultaRequest(
        Long animalId,
        Long veterinarioId,
        LocalDateTime dataHora,
        String motivo
) {
}
