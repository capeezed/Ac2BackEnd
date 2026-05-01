package com.facens.petcare.repositories;

import com.facens.petcare.models.Prontuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    List<Prontuario> findByAnimalIdOrderByDataRegistroDesc(Long animalId);
}
