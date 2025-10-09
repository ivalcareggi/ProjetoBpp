package com.project.fgh.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AnalyticsService {

	private final HabitoService habitoService;
	private final DiasHabitoService diashabitoService;
	
	// Calcular a % de sucesso das metas definidas no habito
	public BigDecimal calcularTaxaSucesso(Long idHabito) {
		return null;
	}
	
	// Retorna o dia da semana que determinada
	public String melhorDiaSemana(Long idHabito) {
		return null;
	}
	
	// Retorna periodo dia (Manha, Tarde, Noite) com maior taxa de execução
	public String melhorPeriodoDia(Long idHabito) {
		return null;
	}
}
