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

	/**
	 * @param habito
	 * @param diasHabitos
	 * @return
	 */
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

	/**
	 * @param tipo
	 * @param config
	 * @param dataAtual
	 * @param proximaData
	 * @return
	 */
	private boolean verificaStreakValido(TipoFrequencia tipo, String config, LocalDate dataAtual,
			LocalDate proximaData) {
		boolean continuaStreak = false;
		switch (tipo) {
		    case DIARIAMENTE:
		        continuaStreak = ChronoUnit.DAYS.between(dataAtual, proximaData) == 1;
		        break;
		    case DIAS_ESPECIFICOS:
		        // Exemplo: "SEG,QUA,SEX"
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

	// Mapeia strings como "SEG", "TER", etc. para DayOfWeek
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

	// Dado o dia atual e os dias permitidos, encontra o próximo dia permitido
	private DayOfWeek encontrarProximoDiaPermitido(DayOfWeek atual, Set<DayOfWeek> permitidos) {
	    for (int i = 1; i <= 7; i++) {
	        DayOfWeek candidato = atual.plus(i);
	        if (permitidos.contains(candidato)) {
	            return candidato;
	        }
	    }
	    return atual; // fallback
	}
}
