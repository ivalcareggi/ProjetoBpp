package com.project.fgh.models.dto;

import com.project.fgh.models.entity.Habito;
import com.project.fgh.models.enums.TipoFrequencia;
import com.project.fgh.models.enums.TipoHabito;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HabitoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Boolean ativo;
    private TipoHabito tipoHabito;
    private TipoFrequencia tipoFrequencia;
    private String configFrequencia;

    public HabitoDTO(Habito habito) {
         this.setId(habito.getId());
         this.setNome(habito.getNome());
         this.setDescricao(habito.getDescricao());
         this.setAtivo(habito.getAtivo());
         this.setTipoHabito(habito.getTipoHabito());
         this.setTipoFrequencia(habito.getTipoFrequencia());
         this.setConfigFrequencia(habito.getConfigFrequencia());
    }
    
    public Habito getHabito() {
        Habito habito = new Habito();
        habito.setId(this.getId());
        habito.setNome(this.getNome());
        habito.setDescricao(this.getDescricao());
        habito.setAtivo(this.getAtivo() != null ? this.getAtivo() : true);
        habito.setTipoHabito(this.getTipoHabito());
        habito.setTipoFrequencia(this.getTipoFrequencia());
        habito.setConfigFrequencia(this.getConfigFrequencia());
        return habito;
    }
}