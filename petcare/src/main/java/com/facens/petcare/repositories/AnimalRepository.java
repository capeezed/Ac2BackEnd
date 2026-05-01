package com.facens.petcare.repositories;

import com.facens.petcare.models.Animal;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    @EntityGraph(attributePaths = {"tutor", "consultas", "prontuarios", "vacinacoes"})
    Optional<Animal> findWithTutorById(Long id);
}
