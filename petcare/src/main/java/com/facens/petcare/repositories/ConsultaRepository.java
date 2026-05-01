package com.facens.petcare.repositories;

import com.facens.petcare.models.Consulta;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByVeterinarioIdAndDataHora(Long veterinarioId, LocalDateTime dataHora);
    List<Consulta> findByAnimalIdOrderByDataHoraDesc(Long animalId);
}
