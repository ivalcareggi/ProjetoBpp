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
import com.project.fgh.models.entity.Habito;
import com.project.fgh.services.FrequenciaService;
import com.project.fgh.services.HabitoService;

import lombok.AllArgsConstructor;

@RestController // de @Controller para @RestController
@AllArgsConstructor
@RequestMapping("/habitos") 
public class HabitoController {

    private final HabitoService habitoService;

    private final FrequenciaService frequenciaService;
    
    @PostMapping
    public ResponseEntity<HabitoDTO> criar(@RequestBody HabitoDTO habitoDTO) {
        Habito habito = habitoDTO.getHabito();
        Habito novoHabito = habitoService.salvar(habito);
        return new ResponseEntity<>(new HabitoDTO(novoHabito), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HabitoDTO> obterPorId(@PathVariable Long id) {
        Habito habito = habitoService.buscarId(id);
        return ResponseEntity.ok(new HabitoDTO(habito));
    }

    @GetMapping
    public ResponseEntity<List<HabitoDTO>> listarTodos() {
        List<Habito> habitos = habitoService.listarTodos();
        List<HabitoDTO> habitosDto = habitos.stream()
			.map(HabitoDTO::new)
			.collect(Collectors.toList());
        return ResponseEntity.ok(habitosDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitoDTO> atualizar(@PathVariable Long id, @RequestBody HabitoDTO habitoDTO) {
        habitoService.buscarId(id);
        habitoDTO.setId(id);
        Habito habitoAtualizado = habitoService.salvar(habitoDTO.getHabito());
        return ResponseEntity.ok(new HabitoDTO(habitoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        habitoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("streak/{id}")
    public ResponseEntity<List<DiasHabitoDTO>> verStreak(@PathVariable Long id){
    	Habito habito = habitoService.buscarId(id);
    	List<DiasHabito> streak = frequenciaService.buscarMaiorStreak(habito);
    	List<DiasHabitoDTO> streakDTO = streak.stream().map(DiasHabitoDTO::new).toList();
    	return ResponseEntity.ok(streakDTO);
    }
}