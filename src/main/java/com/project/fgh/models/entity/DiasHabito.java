package com.project.fgh.models.entity;

import java.util.GregorianCalendar;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dias_habito")
@Getter
@Setter
@NoArgsConstructor
public class DiasHabito {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private GregorianCalendar dataHabito;
	
	@ManyToOne
	@JoinColumn(name = "idHabito")
	private Habito habito;
	
	private Boolean concluido;
	
	private GregorianCalendar horaInicio;
	private GregorianCalendar horaFim;
	
	private String observacoes;
}

