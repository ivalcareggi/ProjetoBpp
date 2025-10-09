package com.project.fgh.controllers;

import com.project.fgh.models.dto.DiasHabitoDTO;
import com.project.fgh.models.dto.HabitoDTO;
import com.project.fgh.models.dto.HabitoRequestDTO;
import com.project.fgh.models.entity.DiasHabito;
import com.project.fgh.models.entity.Habito;
import com.project.fgh.services.FrequenciaService;
import com.project.fgh.services.HabitoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/habitos")
@Tag(name = "Hábitos", description = "Endpoints para gerenciamento de hábitos")
public class HabitoController {

    private final HabitoService habitoService;
    private final FrequenciaService frequenciaService;

    @Operation(summary = "Cria um novo hábito", description = "Registra um novo hábito no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hábito criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HabitoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<HabitoDTO> criar(
            @Parameter(description = "Dados do hábito a ser criado", required = true) @RequestBody HabitoRequestDTO habitoRequestDTO) {
        Habito habito = habitoRequestDTO.toEntity();
        Habito novoHabito = habitoService.salvar(habito);
        return new ResponseEntity<>(new HabitoDTO(novoHabito), HttpStatus.CREATED);
    }

    @Operation(summary = "Busca um hábito pelo ID", description = "Retorna os detalhes de um hábito específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hábito encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HabitoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Hábito não encontrado com o ID fornecido", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<HabitoDTO> obterPorId(
            @Parameter(description = "ID do hábito a ser buscado", required = true, example = "1") @PathVariable Long id) {
        Habito habito = habitoService.buscarId(id);
        return ResponseEntity.ok(new HabitoDTO(habito));
    }

    @Operation(summary = "Lista todos os hábitos", description = "Retorna uma lista de todos os hábitos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de hábitos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<HabitoDTO>> listarTodos() {
        List<HabitoDTO> habitosDto = habitoService.listarTodosComoDTO();
        return ResponseEntity.ok(habitosDto);
    }

    @Operation(summary = "Atualiza um hábito existente", description = "Modifica os dados de um hábito com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hábito atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HabitoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Hábito não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<HabitoDTO> atualizar(
            @Parameter(description = "ID do hábito a ser atualizado", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Novos dados para o hábito", required = true) @RequestBody HabitoRequestDTO habitoRequestDTO) {
        Habito habitoParaAtualizar = habitoRequestDTO.toEntity();
        habitoParaAtualizar.setId(id);
        Habito habitoAtualizado = habitoService.salvar(habitoParaAtualizar);
        return ResponseEntity.ok(new HabitoDTO(habitoAtualizado));
    }

    @Operation(summary = "Exclui um hábito", description = "Remove um hábito do sistema pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Hábito excluído com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Hábito não encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do hábito a ser excluído", required = true, example = "1") @PathVariable Long id) {
        habitoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca a maior sequência (streak) de um hábito", description = "Calcula e retorna a maior sequência de dias consecutivos em que um hábito foi concluído.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Streak retornado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DiasHabitoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Hábito não encontrado", content = @Content)
    })
    @GetMapping("/streak/{id}")
    public ResponseEntity<List<DiasHabitoDTO>> verStreak(
            @Parameter(description = "ID do hábito para cálculo do streak", required = true, example = "1") @PathVariable Long id){
        Habito habito = habitoService.buscarId(id);
        List<DiasHabito> streak = frequenciaService.buscarMaiorStreak(habito);
        List<DiasHabitoDTO> streakDTO = streak.stream().map(DiasHabitoDTO::new).toList();
        return ResponseEntity.ok(streakDTO);
    }
}