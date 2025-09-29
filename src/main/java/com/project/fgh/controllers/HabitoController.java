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

import com.project.fgh.models.dto.HabitoDTO;
import com.project.fgh.models.entity.Habito;
import com.project.fgh.services.HabitoService;

import lombok.AllArgsConstructor;

@RestController // de @Controller para @RestController
@AllArgsConstructor
@RequestMapping("/habitos") 
public class HabitoController {

    private final HabitoService habitoService;

    @PostMapping
    public ResponseEntity<HabitoDTO> criar(@RequestBody HabitoDTO habitoDTO) {
        Habito habito = convertDtoToEntity(habitoDTO);
        Habito novoHabito = habitoService.salvar(habito);
        return new ResponseEntity<>(convertEntityToDto(novoHabito), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HabitoDTO> obterPorId(@PathVariable Long id) {
        Habito habito = habitoService.buscarId(id);
        return ResponseEntity.ok(convertEntityToDto(habito));
    }

    @GetMapping
    public ResponseEntity<List<HabitoDTO>> listarTodos() {
        List<Habito> habitos = habitoService.listarTodos();
        List<HabitoDTO> habitosDto = habitos.stream()
			.map(this::convertEntityToDto)
			.collect(Collectors.toList());
        return ResponseEntity.ok(habitosDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitoDTO> atualizar(@PathVariable Long id, @RequestBody HabitoDTO habitoDTO) {
        habitoService.buscarId(id);
        habitoDTO.setId(id);
        Habito habitoAtualizado = habitoService.salvar(convertDtoToEntity(habitoDTO));
        return ResponseEntity.ok(convertEntityToDto(habitoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        habitoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    private Habito convertDtoToEntity(HabitoDTO dto) {
        Habito entity = new Habito();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        entity.setTipoHabito(dto.getTipoHabito());
        entity.setTipoFrequencia(dto.getTipoFrequencia());
        entity.setConfigFrequencia(dto.getConfigFrequencia());
        return entity;
    }

    private HabitoDTO convertEntityToDto(Habito entity) {
        HabitoDTO dto = new HabitoDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setDescricao(entity.getDescricao());
        dto.setAtivo(entity.getAtivo());
        dto.setTipoHabito(entity.getTipoHabito());
        dto.setTipoFrequencia(entity.getTipoFrequencia());
        dto.setConfigFrequencia(entity.getConfigFrequencia());
        return dto;
    }
}