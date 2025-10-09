package com.project.fgh.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.fgh.models.entity.DiasHabito;
import com.project.fgh.models.entity.Habito;
import com.project.fgh.models.enums.TipoFrequencia;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FrequenciaService {

	public List<DiasHabito> buscarMaiorStreak(Habito habito) {
	    List<DiasHabito> diasHabitos = new ArrayList<>(habito.getDiasHabitos());
	    diasHabitos.sort(Comparator.comparing(DiasHabito::getData));
	    
	    if (diasHabitos.isEmpty()) return Collections.emptyList();
	    
	    List<DiasHabito> maiorStreak = encontrarMaiorStreak(habito, diasHabitos);

	    return maiorStreak;
	}

	private List<DiasHabito> encontrarMaiorStreak(Habito habito, List<DiasHabito> diasHabitos) {
		List<DiasHabito> maiorStreak = new ArrayList<>();
	    List<DiasHabito> streakAtual = new ArrayList<>();
	    streakAtual.add(diasHabitos.get(0));

	    TipoFrequencia tipo = habito.getTipoFrequencia();
	    String config = habito.getConfigFrequencia();

	    for (int i = 0; i < diasHabitos.size() - 1; i++) {
	    	if(i + 1 >= diasHabitos.size()) break;
	        LocalDate dataAtual = diasHabitos.get(i).getData();
	        LocalDate proximaData = diasHabitos.get(i + 1).getData();
	        boolean continuaStreak = verificaStreakValido(tipo, config, dataAtual, proximaData);
	        if (continuaStreak) {
	            streakAtual.add(diasHabitos.get(i + 1));
	        } else {
	            if (streakAtual.size() >= maiorStreak.size()) {
	                maiorStreak = new ArrayList<>(streakAtual);
	            }
	            streakAtual.clear();
	            streakAtual.add(diasHabitos.get(i + 1));
	        }
	    }
	    if (streakAtual.size() >= maiorStreak.size()) {
	        maiorStreak = streakAtual;
	    }
		return maiorStreak;
	}

	private boolean verificaStreakValido(TipoFrequencia tipo, String config, LocalDate dataAtual,
			LocalDate proximaData) {
		boolean continuaStreak = false;
		switch (tipo) {
		    case DIARIAMENTE:
		        continuaStreak = ChronoUnit.DAYS.between(dataAtual, proximaData) == 1;
		        break;
		    case DIAS_ESPECIFICOS:
		        Set<DayOfWeek> diasPermitidos = Arrays.stream(config.split(","))
		                .map(String::trim)
		                .map(String::toUpperCase)
		                .map(this::mapearDiaSemana)
		                .collect(Collectors.toSet());

		        DayOfWeek proximoPermitido = encontrarProximoDiaPermitido(dataAtual.getDayOfWeek(), diasPermitidos);
		        LocalDate proximaDataEsperada = dataAtual.plusDays(
		                (proximoPermitido.getValue() - dataAtual.getDayOfWeek().getValue() + 7) % 7);
		        continuaStreak = proximaData.equals(proximaDataEsperada);
		        break;
		    case INTERVALO_DE_DIAS:
		        int intervalo = Integer.parseInt(config);
		        continuaStreak = ChronoUnit.DAYS.between(dataAtual, proximaData) == intervalo;
		        break;
			default:
				break;
		}
		return continuaStreak;
	}

	private DayOfWeek mapearDiaSemana(String dia) {
	    switch (dia) {
	        case "SEG": return DayOfWeek.MONDAY;
	        case "TER": return DayOfWeek.TUESDAY;
	        case "QUA": return DayOfWeek.WEDNESDAY;
	        case "QUI": return DayOfWeek.THURSDAY;
	        case "SEX": return DayOfWeek.FRIDAY;
	        case "SAB": return DayOfWeek.SATURDAY;
	        case "DOM": return DayOfWeek.SUNDAY;
	        default: throw new IllegalArgumentException("Dia inválido: " + dia);
	    }
	}

	private DayOfWeek encontrarProximoDiaPermitido(DayOfWeek atual, Set<DayOfWeek> permitidos) {
	    for (int i = 1; i <= 7; i++) {
	        DayOfWeek candidato = atual.plus(i);
	        if (permitidos.contains(candidato)) {
	            return candidato;
	        }
	    }
	    return atual;
	}
	public int calcularDiasEsperados(Habito habito, LocalDate inicio, LocalDate fim) {
        LocalDate dataInicioCalculo = inicio.isBefore(habito.getDataCriacao()) ? habito.getDataCriacao() : inicio;
        if (dataInicioCalculo.isAfter(fim)) {
            return 0;
        }

        switch (habito.getTipoFrequencia()) {
            case DIARIAMENTE:
                return calcularDiariamente(dataInicioCalculo, fim);
            case DIAS_ESPECIFICOS:
                return calcularDiasEspecificos(habito.getConfigFrequencia(), dataInicioCalculo, fim);
            case INTERVALO_DE_DIAS:
                return calcularIntervaloDeDias(habito, dataInicioCalculo, fim);
            case VEZES_POR_SEMANA:
                return calcularVezesPorSemana(habito.getConfigFrequencia(), dataInicioCalculo, fim);
            default:
                return 0;
        }
    }

    private int calcularDiariamente(LocalDate inicio, LocalDate fim) {
        return (int) ChronoUnit.DAYS.between(inicio, fim) + 1;
    }

    private int calcularDiasEspecificos(String config, LocalDate inicio, LocalDate fim) {
        Set<DayOfWeek> diasPermitidos = Arrays.stream(config.split(","))
                .map(String::trim).map(String::toUpperCase).map(this::mapearDiaSemana)
                .collect(Collectors.toSet());

        int diasEsperados = 0;
        for (LocalDate data = inicio; !data.isAfter(fim); data = data.plusDays(1)) {
            if (diasPermitidos.contains(data.getDayOfWeek())) {
                diasEsperados++;
            }
        }
        return diasEsperados;
    }

    private int calcularIntervaloDeDias(Habito habito, LocalDate inicio, LocalDate fim) {
        try {
            int intervalo = Integer.parseInt(habito.getConfigFrequencia());
            if (intervalo <= 0) return 0;

            int diasEsperados = 0;
            LocalDate dataHabito = habito.getDataCriacao();
            while (dataHabito.isBefore(inicio)) {
                dataHabito = dataHabito.plusDays(intervalo);
            }
            while (!dataHabito.isAfter(fim)) {
                diasEsperados++;
                dataHabito = dataHabito.plusDays(intervalo);
            }
            return diasEsperados;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int calcularVezesPorSemana(String config, LocalDate inicio, LocalDate fim) {
        try {
            int vezesPorSemana = Integer.parseInt(config);
            long totalDiasNoPeriodo = ChronoUnit.DAYS.between(inicio, fim) + 1;
            return (int) Math.round((totalDiasNoPeriodo / 7.0) * vezesPorSemana);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
