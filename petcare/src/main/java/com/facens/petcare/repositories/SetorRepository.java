package com.facens.petcare.repositories;

import com.facens.petcare.models.Setor;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetorRepository extends JpaRepository<Setor, Long> {
    @EntityGraph(attributePaths = "funcionarios")
    List<Setor> findAll();
}
