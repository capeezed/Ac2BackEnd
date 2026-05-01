package com.facens.petcare.repositories;

import com.facens.petcare.models.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
}
