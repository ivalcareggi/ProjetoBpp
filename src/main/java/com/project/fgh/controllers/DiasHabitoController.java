package com.project.fgh.controllers;

import com.project.fgh.models.dto.DiasHabitoDTO;
import com.project.fgh.models.dto.DiasHabitoRequestDTO; 
import com.project.fgh.models.entity.DiasHabito;
import com.project.fgh.services.DiasHabitoService;
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
@RequestMapping("/diashabito")
@Tag(name = "Registro Diário de Hábitos", description = "Endpoints para registrar a conclusão diária de hábitos")
public class DiasHabitoController {

    private final DiasHabitoService diasHabitoService;

    @Operation(summary = "Cria um novo registro diário", description = "Registra a conclusão de um hábito em uma data específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<DiasHabitoDTO> criar(@RequestBody DiasHabitoRequestDTO diasHabitoRequestDTO) { // MUDANÇA AQUI
        DiasHabito diasHabito = diasHabitoRequestDTO.toEntity(); // MUDANÇA AQUI
        DiasHabito novoDiasHabito = diasHabitoService.salvar(diasHabito);
        return new ResponseEntity<>(new DiasHabitoDTO(novoDiasHabito), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Busca um registro diário pelo ID", description = "Retorna os detalhes de um registro de um dia específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado com o ID fornecido")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DiasHabitoDTO> obterPorId(@PathVariable Long id) {
        DiasHabito diasHabito = diasHabitoService.buscarId(id);
        return ResponseEntity.ok(new DiasHabitoDTO(diasHabito));
    }

    @Operation(summary = "Lista todos os registros diários", description = "Retorna uma lista de todos os registros de conclusão de hábitos.")
    @GetMapping
    public ResponseEntity<List<DiasHabitoDTO>> listarTodos() {
        List<DiasHabitoDTO> habitosDto = diasHabitoService.listarTodosComoDTO();
        return ResponseEntity.ok(habitosDto);
    }

    @Operation(summary = "Atualiza um registro diário", description = "Modifica os dados de um registro diário com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DiasHabitoDTO> atualizar(@PathVariable Long id, @RequestBody DiasHabitoRequestDTO diasHabitoRequestDTO) { // MUDANÇA AQUI
        DiasHabito diasHabitoParaAtualizar = diasHabitoRequestDTO.toEntity(); 
        diasHabitoParaAtualizar.setId(id); 
        DiasHabito habitoAtualizado = diasHabitoService.salvar(diasHabitoParaAtualizar);
        return ResponseEntity.ok(new DiasHabitoDTO(habitoAtualizado));
    }

    @Operation(summary = "Exclui um registro diário", description = "Remove um registro diário do sistema pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        diasHabitoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}