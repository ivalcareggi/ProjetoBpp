package com.project.fgh.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.fgh.models.entity.DiasHabito;


@Repository
public interface DiasHabitoRepository extends JpaRepository<DiasHabito, Long>{
    Optional<DiasHabito> findByHabitoIdAndData(Long habitoId, LocalDate data);

    List<DiasHabito> findByHabitoIdOrderByDataDesc(Long habitoId);

    List<DiasHabito> findByHabitoIdAndDataBetween(Long habitoId, LocalDate dataInicio, LocalDate dataFim);

    @Query("SELECT COUNT(dh) FROM DiasHabito dh WHERE dh.habito.id = :habitoId AND dh.concluido = true")
    long countDiasConcluidosByHabitoId(Long habitoId);
}
