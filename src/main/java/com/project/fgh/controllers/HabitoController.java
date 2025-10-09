package com.project.fgh.controllers;

import com.project.fgh.models.dto.DiasHabitoDTO;
import com.project.fgh.models.dto.HabitoDTO;
import com.project.fgh.models.dto.HabitoRequestDTO; 
import com.project.fgh.models.entity.DiasHabito;
import com.project.fgh.models.entity.Habito;
import com.project.fgh.services.FrequenciaService;
import com.project.fgh.services.HabitoService;
import io.swagger.v3.oas.annotations.Operation;
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
            @ApiResponse(responseCode = "201", description = "Hábito criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<HabitoDTO> criar(@RequestBody HabitoRequestDTO habitoRequestDTO) { // MUDANÇA AQUI
        Habito habito = habitoRequestDTO.toEntity(); // MUDANÇA AQUI
        Habito novoHabito = habitoService.salvar(habito);
        return new ResponseEntity<>(new HabitoDTO(novoHabito), HttpStatus.CREATED);
    }

    @Operation(summary = "Busca um hábito pelo ID", description = "Retorna os detalhes de um hábito específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hábito encontrado"),
            @ApiResponse(responseCode = "404", description = "Hábito não encontrado com o ID fornecido")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HabitoDTO> obterPorId(@PathVariable Long id) {
        Habito habito = habitoService.buscarId(id);
        return ResponseEntity.ok(new HabitoDTO(habito));
    }

    @Operation(summary = "Lista todos os hábitos", description = "Retorna uma lista de todos os hábitos cadastrados.")
    @GetMapping
    public ResponseEntity<List<HabitoDTO>> listarTodos() {
        List<HabitoDTO> habitosDto = habitoService.listarTodosComoDTO();
        return ResponseEntity.ok(habitosDto);
    }

    @Operation(summary = "Atualiza um hábito existente", description = "Modifica os dados de um hábito com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hábito atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Hábito não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<HabitoDTO> atualizar(@PathVariable Long id, @RequestBody HabitoRequestDTO habitoRequestDTO) { // MUDANÇA AQUI
        Habito habitoParaAtualizar = habitoRequestDTO.toEntity(); // MUDANÇA AQUI
        habitoParaAtualizar.setId(id); // Garante que estamos atualizando o ID correto
        Habito habitoAtualizado = habitoService.salvar(habitoParaAtualizar);
        return ResponseEntity.ok(new HabitoDTO(habitoAtualizado));
    }

    @Operation(summary = "Exclui um hábito", description = "Remove um hábito do sistema pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Hábito excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Hábito não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        habitoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca a maior sequência (streak) de um hábito", description = "Calcula e retorna a maior sequência de dias consecutivos em que um hábito foi concluído.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Streak retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Hábito não encontrado")
    })
    @GetMapping("streak/{id}")
    public ResponseEntity<List<DiasHabitoDTO>> verStreak(@PathVariable Long id){
        Habito habito = habitoService.buscarId(id);
        List<DiasHabito> streak = frequenciaService.buscarMaiorStreak(habito);
        List<DiasHabitoDTO> streakDTO = streak.stream().map(DiasHabitoDTO::new).toList();
        return ResponseEntity.ok(streakDTO);
    }
}