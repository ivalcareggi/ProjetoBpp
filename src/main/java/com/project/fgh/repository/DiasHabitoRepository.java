package com.project.fgh.repository;

import com.project.fgh.models.entity.DiasHabito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiasHabitoRepository extends JpaRepository<DiasHabito, Long> {

    // SEUS MÉTODOS EXISTENTES
    Optional<DiasHabito> findByHabitoIdAndData(Long habitoId, LocalDate data);

    List<DiasHabito> findByHabitoIdOrderByDataDesc(Long habitoId);

    List<DiasHabito> findByHabitoIdAndDataBetween(Long habitoId, LocalDate dataInicio, LocalDate dataFim);

    @Query("SELECT COUNT(dh) FROM DiasHabito dh WHERE dh.habito.id = :habitoId AND dh.concluido = true")
    long countDiasConcluidosByHabitoId(Long habitoId);

    @Query("SELECT dh FROM DiasHabito dh WHERE dh.habito.id = :idHabito")
    List<DiasHabito> findByIdHabito(Long idHabito);


    // MÉTODOS NECESSÁRIOS PARA O ANALYTICS SERVICE
    
    /**
     * Conta o número de registros de um hábito específico que foram marcados como concluídos
     * dentro de um intervalo de datas. Usado para "Taxa de Sucesso".
     */
    int countByHabitoIdAndDataBetweenAndConcluido(Long habitoId, LocalDate inicio, LocalDate fim, boolean concluido);

    /**
     * Busca todos os registros de hábitos concluídos (independente de qual hábito seja)
     * dentro de um intervalo de datas. Essencial para "Melhor Desempenho" e "Correlações".
     */
    List<DiasHabito> findByConcluidoAndDataBetween(boolean concluido, LocalDate inicio, LocalDate fim);
}