package com.project.fgh.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.fgh.models.dto.HabitoDTO;
import com.project.fgh.services.HabitoService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/habito")
public class HabitoController {

	private final HabitoService habitoService;
	
	@GetMapping("listar")
	public String home() {
		return null;
	}
	
	@PostMapping("excluir/{id}")
	public String excluir(@PathVariable("id") Long id) {
		return home();
	}
	
	@PostMapping("salvar")
	public String salvar(HabitoDTO habitoDTO) {
		return null;
	}
}
