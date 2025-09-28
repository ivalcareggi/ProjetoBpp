package com.project.fgh.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.fgh.models.entity.DiasHabito;
import com.project.fgh.repository.DiasHabitoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DiasHabitoService {

	private final DiasHabitoRepository diasHabitoRepository;
	
	// salva um diasHabito
	public void salvar(DiasHabito diaHabito) {}
	
	// busca um diasHabito com base no id do diasHabito
	public DiasHabito buscar(Long id) {
		return null;
	}

	// marca a hora de inicio do habito naquele dia
	public void iniciarHabito(Long id) {}
	
	// busca o ultimo streak do habito
	public List<DiasHabito> buscarStreakHabito(Long idHabito) {
		return null;
	}
	
}
