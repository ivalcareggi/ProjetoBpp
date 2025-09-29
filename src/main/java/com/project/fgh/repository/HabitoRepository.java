package com.project.fgh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.fgh.models.entity.Habito;

@Repository
public interface HabitoRepository extends JpaRepository<Habito, Long> {
    // Busca um hábito pelo nome, ignorando a diferença entre maiúsculas e minúsculas.
    Optional<Habito> findByNomeIgnoreCase(String nome);
    
    //Retorna uma lista de todos os hábitos que estão marcados como ativos.
    List<Habito> findByAtivoTrue();
}