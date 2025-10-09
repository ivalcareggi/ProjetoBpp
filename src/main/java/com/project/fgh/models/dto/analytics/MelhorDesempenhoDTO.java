package com.project.fgh.models.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MelhorDesempenhoDTO {

    private String melhorDiaDaSemana; // Ex: "QUARTA-FEIRA"
    private String melhorPeriodoDoDia; // Ex: "MANHÃ"
}