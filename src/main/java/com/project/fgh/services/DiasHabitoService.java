package com.project.fgh.services;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.project.fgh.models.entity.DiasHabito;
import com.project.fgh.models.entity.Habito;
import com.project.fgh.repository.DiasHabitoRepository;
import com.project.fgh.repository.HabitoRepository;
import com.project.fgh.services.exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DiasHabitoService {

	private final DiasHabitoRepository diasHabitoRepository;
	private final HabitoRepository habitoRepository;
	
	// salva um diasHabito
	public DiasHabito salvar(DiasHabito diaHabito) {
		return diasHabitoRepository.save(diaHabito);
	}
	
	// busca um diasHabito com base no id do diasHabito
	public DiasHabito buscar(Long id) {
		Optional<DiasHabito> diasHabitoOptional = diasHabitoRepository.findById(id);
		return diasHabitoOptional.orElseThrow(()-> new ResourceNotFoundException("Dia não encontrado!"));
	}

	// marca a hora de inicio do habito naquele dia
	public void iniciarHabito(Long id) {
		LocalDate today = LocalDate.now();
		Optional<DiasHabito> registroExistente = diasHabitoRepository.findByHabitoIdAndData(idHabito, today); // DEFINIR ESSA VARIAVEL IDHABITO
		if (registroExistente.isPresent()) {
            System.out.println("Hábito com ID " + idHabito + " já foi iniciado hoje.");
            return; 
        }
		Habito habito = habitoRepository.findById(idHabito); // MEUS NEURONIOS ESTAO QUEIMADOS AGORA DEPOIS CORRIJO 🙈😘 🤯
	}
	
	// busca o ultimo streak do habito
	public List<DiasHabito> buscarStreakHabito(Long idHabito) {
		return null;
	}
	
}
