package com.project.fgh.models.dto;

import com.project.fgh.models.enums.TipoFrequencia;
import com.project.fgh.models.enums.TipoHabito;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Boolean ativo;
    private TipoHabito tipoHabito;
    private TipoFrequencia tipoFrequencia;
    private String configFrequencia;

}