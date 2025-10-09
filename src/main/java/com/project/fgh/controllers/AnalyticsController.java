package com.project.fgh.controllers;

import com.project.fgh.models.dto.analytics.CorrelacaoDTO;
import com.project.fgh.models.dto.analytics.MelhorDesempenhoDTO;
import com.project.fgh.models.dto.analytics.TaxaSucessoDTO;
import com.project.fgh.services.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics e Insights", description = "Endpoints para análise de dados e estatísticas dos hábitos")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @Operation(summary = "Calcula a taxa de sucesso de um hábito", description = "Retorna a porcentagem de sucesso de um hábito em um determinado período de tempo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cálculo realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Hábito não encontrado com o ID fornecido")
    })
    @GetMapping("/habito/{habitoId}/taxa-sucesso")
    public ResponseEntity<TaxaSucessoDTO> getTaxaDeSucesso(
            @Parameter(description = "ID do hábito a ser analisado", required = true, example = "1") @PathVariable Long habitoId,
            @Parameter(description = "Data de início do período (formato AAAA-MM-DD)", required = true, example = "2025-10-01") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @Parameter(description = "Data de fim do período (formato AAAA-MM-DD)", required = true, example = "2025-10-31") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        TaxaSucessoDTO taxaSucesso = analyticsService.calcularTaxaDeSucesso(habitoId, inicio, fim);
        return ResponseEntity.ok(taxaSucesso);
    }

    @Operation(summary = "Encontra o melhor dia e período de desempenho", description = "Analisa o histórico e retorna o dia da semana e o período do dia (manhã, tarde, noite) com maior número de hábitos concluídos.")
    @GetMapping("/melhor-desempenho")
    public ResponseEntity<MelhorDesempenhoDTO> getMelhorDesempenho(
            @Parameter(description = "Data de início do período (formato AAAA-MM-DD)", required = true, example = "2025-10-01") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @Parameter(description = "Data de fim do período (formato AAAA-MM-DD)", required = true, example = "2025-10-31") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        MelhorDesempenhoDTO melhorDesempenho = analyticsService.encontrarMelhorDesempenho(inicio, fim);
        return ResponseEntity.ok(melhorDesempenho);
    }

    @Operation(summary = "Analisa correlações entre hábitos", description = "Busca por padrões onde a conclusão de um hábito aumenta a probabilidade de concluir outro.")
    @GetMapping("/correlacoes")
    public ResponseEntity<List<CorrelacaoDTO>> getCorrelacoes(
            @Parameter(description = "Data de início do período (formato AAAA-MM-DD)", required = true, example = "2025-01-01") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @Parameter(description = "Data de fim do período (formato AAAA-MM-DD)", required = true, example = "2025-10-31") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        List<CorrelacaoDTO> correlacoes = analyticsService.analisarCorrelacoes(inicio, fim);
        return ResponseEntity.ok(correlacoes);
    }
}