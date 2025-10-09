package com.project.fgh.models.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.project.fgh.models.entity.DiasHabito;
import com.project.fgh.models.entity.Habito;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiasHabitoDTO {
	
	private Long id;
    private LocalDate data;
    private Long habitoId; // apenas o ID do hábito
    private Boolean concluido;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFim;
    private Integer duracaoEmMinutos;
    private Integer quantidadeRealizada;
    private String observacoes;

    public DiasHabitoDTO(DiasHabito diasHabito) {
		this.setId(diasHabito.getId());
		this.setData(diasHabito.getData());
		this.setHabitoId(diasHabito.getHabito().getId());
		this.setConcluido(diasHabito.getConcluido());
		this.setHoraInicio(diasHabito.getHoraInicio());
		this.setHoraFim(diasHabito.getHoraFim());
		this.setDuracaoEmMinutos(diasHabito.getDuracaoEmMinutos());
		this.setQuantidadeRealizada(diasHabito.getQuantidadeRealizada());
		this.setObservacoes(diasHabito.getObservacoes());
    }
    
    public DiasHabito getDiasHabito() {
	    DiasHabito diasHabito = new DiasHabito();
	   	diasHabito.setId(this.getId());
	   	diasHabito.setData(this.getData());
	   	diasHabito.setHabito(new Habito(this.getHabitoId()));
	   	diasHabito.setConcluido(this.getConcluido());
	   	diasHabito.setHoraInicio(this.getHoraInicio());
	   	diasHabito.setHoraFim(this.getHoraFim());
	   	diasHabito.setDuracaoEmMinutos(this.getDuracaoEmMinutos());
	   	diasHabito.setQuantidadeRealizada(this.getQuantidadeRealizada());
	   	diasHabito.setObservacoes(this.getObservacoes());
	    return diasHabito;
    }
}
