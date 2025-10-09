package com.project.fgh.models.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CorrelacaoDTO {

    private String habitoBase;
    private String habitoCorrelacionado;
    private double aumentoProbabilidadePercentual;
    private String insight; // Ex: "Completar 'Meditar' aumenta em 30% a chance de você completar 'Estudar'."
}