package com.project.fgh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.fgh.models.entity.Habito;

@Repository
public interface HabitoRepository extends JpaRepository<Habito, Long> {
    
    Optional<Habito> findByNomeIgnoreCase(String nome);
    
    List<Habito> findByAtivoTrue();
}