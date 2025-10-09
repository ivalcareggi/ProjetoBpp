package com.project.fgh.models.dto;

import com.project.fgh.models.entity.Habito;
import com.project.fgh.models.enums.TipoFrequencia;
import com.project.fgh.models.enums.TipoHabito;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitoRequestDTO {

    @Schema(description = "Nome do hábito.", example = "Ler 20 páginas", required = true)
    private String nome;

    @Schema(description = "Descrição detalhada do hábito.", example = "Ler um livro de não-ficção")
    private String descricao;

    @Schema(description = "Tipo do hábito.", example = "QUANTIDADE", required = true)
    private TipoHabito tipoHabito;

    @Schema(description = "Frequência com que o hábito deve ser realizado.", example = "DIARIAMENTE", required = true)
    private TipoFrequencia tipoFrequencia;

    @Schema(description = "Configuração específica da frequência. Ex: 'SEG,QUA,SEX' para DIAS_ESPECIFICOS, ou '3' para VEZES_POR_SEMANA.", example = "SEG,QUA,SEX")
    private String configFrequencia;

    public Habito toEntity() {
        Habito habito = new Habito();
        habito.setNome(this.nome);
        habito.setDescricao(this.descricao);
        habito.setTipoHabito(this.tipoHabito);
        habito.setTipoFrequencia(this.tipoFrequencia);
        habito.setConfigFrequencia(this.configFrequencia);
        return habito;
    }
}