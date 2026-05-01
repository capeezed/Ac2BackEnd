package com.facens.petcare.repositories;

import com.facens.petcare.models.Vacinacao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacinacaoRepository extends JpaRepository<Vacinacao, Long> {
    List<Vacinacao> findByAnimalIdOrderByDataAplicacaoDesc(Long animalId);
}
