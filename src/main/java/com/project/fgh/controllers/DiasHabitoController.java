package com.project.fgh.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.fgh.models.dto.DiasHabitoDTO;
import com.project.fgh.models.dto.HabitoDTO;
import com.project.fgh.models.entity.DiasHabito;
import com.project.fgh.services.DiasHabitoService;
import com.project.fgh.services.HabitoService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/diashabito")
public class DiasHabitoController {

	private final DiasHabitoService diasHabitoService;
	
 	@PostMapping
    public ResponseEntity<DiasHabitoDTO> criar(@RequestBody DiasHabitoDTO diasHabitoDTO) {
        DiasHabito diasHabito = diasHabitoDTO.getDiasHabito();
        DiasHabito novoDiasHabito = diasHabitoService.salvar(diasHabito);
        return new ResponseEntity<DiasHabitoDTO>(new DiasHabitoDTO(novoDiasHabito), HttpStatus.CREATED);
    }

	@GetMapping("/{id}")
    public ResponseEntity<DiasHabitoDTO> obterPorId(@PathVariable Long id) {
		DiasHabito diasHabito = diasHabitoService.buscarId(id);
        return ResponseEntity.ok(new DiasHabitoDTO(diasHabito));
    }

    @GetMapping
    public ResponseEntity<List<DiasHabitoDTO>> listarTodos() {
    	List<DiasHabito> habitos = diasHabitoService.listarTodos();
        List<DiasHabitoDTO> habitosDto = habitos.stream()
			.map(DiasHabitoDTO::new)
			.collect(Collectors.toList());
        return ResponseEntity.ok(habitosDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiasHabitoDTO> atualizar(@PathVariable Long id, @RequestBody DiasHabitoDTO diasHabitoDTO) {
    	diasHabitoService.buscarId(id);
    	diasHabitoDTO.setId(id);
		DiasHabito habitoAtualizado = diasHabitoService.salvar(diasHabitoDTO.getDiasHabito());
		return ResponseEntity.ok(new DiasHabitoDTO(habitoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
    	diasHabitoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
