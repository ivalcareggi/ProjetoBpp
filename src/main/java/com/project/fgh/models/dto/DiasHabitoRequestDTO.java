package com.project.fgh.models.dto;

import com.project.fgh.models.entity.DiasHabito;
import com.project.fgh.models.entity.Habito;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class DiasHabitoRequestDTO {

    @Schema(description = "Data em que o registro do hábito foi feito.", example = "2025-10-09", required = true)
    private LocalDate data;

    @Schema(description = "ID do hábito ao qual este registro pertence.", example = "1", required = true)
    private Long habitoId;

    @Schema(description = "Indica se o hábito foi concluído neste dia.", example = "true", defaultValue = "false")
    private Boolean concluido;

    @Schema(description = "Para hábitos do tipo DURAÇÃO, a quantidade de minutos realizados.", example = "30")
    private Integer duracaoEmMinutos;

    @Schema(description = "Para hábitos do tipo QUANTIDADE, a quantidade realizada.", example = "20")
    private Integer quantidadeRealizada;

    @Schema(description = "Observações ou anotações sobre o registro do dia.", example = "Senti mais dificuldade hoje.")
    private String observacoes;

    public DiasHabito toEntity() {
        DiasHabito diasHabito = new DiasHabito();
        diasHabito.setData(this.data);
        diasHabito.setHabito(new Habito(this.habitoId)); 
        diasHabito.setConcluido(this.concluido != null ? this.concluido : false);
        diasHabito.setDuracaoEmMinutos(this.duracaoEmMinutos);
        diasHabito.setQuantidadeRealizada(this.quantidadeRealizada);
        diasHabito.setObservacoes(this.observacoes);
        return diasHabito;
    }
}