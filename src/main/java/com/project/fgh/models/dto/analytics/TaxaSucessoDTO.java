package com.project.fgh.models.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxaSucessoDTO {

    private Long habitoId;
    private String nomeHabito;
    private double taxaDeSucessoPercentual;
    private int diasCompletados;
    private int diasEsperados;
}