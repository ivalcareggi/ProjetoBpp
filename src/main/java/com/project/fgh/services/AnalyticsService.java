package com.project.fgh.services;

import com.project.fgh.models.entity.DiasHabito;
import com.project.fgh.models.entity.Habito;
import com.project.fgh.models.dto.analytics.CorrelacaoDTO;
import com.project.fgh.models.dto.analytics.MelhorDesempenhoDTO;
import com.project.fgh.models.dto.analytics.TaxaSucessoDTO;
import com.project.fgh.models.enums.TipoFrequencia;
import com.project.fgh.repository.DiasHabitoRepository;
import com.project.fgh.repository.HabitoRepository;
import com.project.fgh.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final HabitoRepository habitoRepository;
    private final DiasHabitoRepository diasHabitoRepository;
    private final FrequenciaService frequenciaService; 

    public TaxaSucessoDTO calcularTaxaDeSucesso(Long habitoId, LocalDate inicio, LocalDate fim) {
        Habito habito = habitoRepository.findById(habitoId)
                .orElseThrow(() -> new ResourceNotFoundException("Hábito não encontrado com id: " + habitoId));

        int diasCompletados = diasHabitoRepository.countByHabitoIdAndDataBetweenAndConcluido(habitoId, inicio, fim, true);
        int diasEsperados = frequenciaService.calcularDiasEsperados(habito, inicio, fim);

        if (diasEsperados == 0) {
            return new TaxaSucessoDTO(habitoId, habito.getNome(), 0.0, diasCompletados, diasEsperados);
        }

        double taxa = ((double) diasCompletados / diasEsperados) * 100.0;

        return new TaxaSucessoDTO(habitoId, habito.getNome(), taxa, diasCompletados, diasEsperados);
    }

    public MelhorDesempenhoDTO encontrarMelhorDesempenho(LocalDate inicio, LocalDate fim) {
        List<DiasHabito> concluidos = diasHabitoRepository.findByConcluidoAndDataBetween(true, inicio, fim);

        if (concluidos.isEmpty()) {
            return new MelhorDesempenhoDTO("N/A", "N/A");
        }

        String melhorDia = concluidos.stream()
                .map(d -> d.getData().getDayOfWeek())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(DayOfWeek::toString) 
                .orElse("N/A");

        String melhorPeriodo = concluidos.stream()
                .filter(d -> d.getHoraFim() != null)
                .map(d -> getPeriodoDoDia(d.getHoraFim()))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        return new MelhorDesempenhoDTO(melhorDia, melhorPeriodo);
    }

    public List<CorrelacaoDTO> analisarCorrelacoes(LocalDate inicio, LocalDate fim) {
        List<Habito> habitosAtivos = habitoRepository.findByAtivoTrue();
        List<CorrelacaoDTO> correlacoes = new ArrayList<>();
        final double LIMIAR_MINIMO_AUMENTO = 20.0; 

        Map<LocalDate, Set<Long>> diasComHabitosConcluidos = diasHabitoRepository.findByConcluidoAndDataBetween(true, inicio, fim)
                .stream()
                .collect(Collectors.groupingBy(
                        DiasHabito::getData,
                        Collectors.mapping(d -> d.getHabito().getId(), Collectors.toSet())
                ));

        for (int i = 0; i < habitosAtivos.size(); i++) {
            for (int j = 0; j < habitosAtivos.size(); j++) {
                if (i == j) continue;

                Habito habitoA = habitosAtivos.get(i);
                Habito habitoB = habitosAtivos.get(j);

                long diasComAFeito = 0;
                long diasComBFeitoQuandoAFeito = 0;

                for (Set<Long> concluidosNoDia : diasComHabitosConcluidos.values()) {
                    boolean aFeito = concluidosNoDia.contains(habitoA.getId());
                    boolean bFeito = concluidosNoDia.contains(habitoB.getId());

                    if (aFeito) {
                        diasComAFeito++;
                        if (bFeito) {
                            diasComBFeitoQuandoAFeito++;
                        }
                    }
                }

                if (diasComAFeito < 5) continue; 

                double taxaSucessoBComA = (double) diasComBFeitoQuandoAFeito / diasComAFeito;
                double taxaSucessoGeralB = (double) diasComHabitosConcluidos.values().stream().filter(s -> s.contains(habitoB.getId())).count() / diasComHabitosConcluidos.size();

                if (taxaSucessoGeralB > 0 && taxaSucessoBComA > taxaSucessoGeralB) {
                    double aumentoPercentual = ((taxaSucessoBComA / taxaSucessoGeralB) - 1) * 100;
                    if (aumentoPercentual > LIMIAR_MINIMO_AUMENTO) {
                        String insight = String.format("Completar '%s' aumenta em %.0f%% a chance de você completar '%s'.",
                                habitoA.getNome(), aumentoPercentual, habitoB.getNome());
                        correlacoes.add(new CorrelacaoDTO(habitoA.getNome(), habitoB.getNome(), aumentoPercentual, insight));
                    }
                }
            }
        }
        return correlacoes;
    }


    private String getPeriodoDoDia(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        if (hour >= 6 && hour < 12) return "MANHÃ";
        if (hour >= 12 && hour < 18) return "TARDE";
        if (hour >= 18 && hour < 24) return "NOITE";
        return "MADRUGADA";
    }
}