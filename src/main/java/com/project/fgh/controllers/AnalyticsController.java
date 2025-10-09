package com.project.fgh.controllers;

import com.project.fgh.models.dto.analytics.CorrelacaoDTO;
import com.project.fgh.models.dto.analytics.MelhorDesempenhoDTO;
import com.project.fgh.models.dto.analytics.TaxaSucessoDTO;
import com.project.fgh.services.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/habito/{habitoId}/taxa-sucesso")
    public ResponseEntity<TaxaSucessoDTO> getTaxaDeSucesso(
            @PathVariable Long habitoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        
        TaxaSucessoDTO taxaSucesso = analyticsService.calcularTaxaDeSucesso(habitoId, inicio, fim);
        return ResponseEntity.ok(taxaSucesso);
    }

    @GetMapping("/melhor-desempenho")
    public ResponseEntity<MelhorDesempenhoDTO> getMelhorDesempenho(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        
        MelhorDesempenhoDTO melhorDesempenho = analyticsService.encontrarMelhorDesempenho(inicio, fim);
        return ResponseEntity.ok(melhorDesempenho);
    }

    @GetMapping("/correlacoes")
    public ResponseEntity<List<CorrelacaoDTO>> getCorrelacoes(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        
        List<CorrelacaoDTO> correlacoes = analyticsService.analisarCorrelacoes(inicio, fim);
        return ResponseEntity.ok(correlacoes);
    }
}