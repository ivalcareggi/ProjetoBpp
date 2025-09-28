package com.project.fgh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.fgh.models.entity.DiasHabito;

@Repository
public interface DiasHabitoRepository extends JpaRepository<DiasHabito, Long>{

}
