package com.facens.petcare.repositories;

import com.facens.petcare.models.Setor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SetorRepository extends JpaRepository<Setor, Long> {
    @Query("select distinct s from Setor s left join fetch s.funcionarios")
    List<Setor> findAll();
}
