package com.facens.petcare.repositories;

import com.facens.petcare.models.Projeto;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    @EntityGraph(attributePaths = "funcionarios")
    Optional<Projeto> findWithFuncionariosById(Long id);

    @Query("""
            select p from Projeto p
            where p.dataInicio <= :fim
              and (p.dataFim is null or p.dataFim >= :inicio)
            """)
    List<Projeto> buscarPorPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    @Query("select p from Projeto p join p.funcionarios f where f.id = :funcionarioId")
    List<Projeto> buscarPorFuncionario(@Param("funcionarioId") Long funcionarioId);
}
