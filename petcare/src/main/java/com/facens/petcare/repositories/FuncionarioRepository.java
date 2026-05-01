package com.facens.petcare.repositories;

import com.facens.petcare.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    boolean existsByEmail(String email);
}
