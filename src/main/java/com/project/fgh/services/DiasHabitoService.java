package com.project.fgh.services;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public void iniciarHabito(Long id) {
		Long idHabito = id;
        LocalDate today = LocalDate.now();
		// checagem se existe no banco
		Habito habito = habitoRepository.findById(idHabito)
                .orElseThrow(() -> new ResourceNotFoundException("Hábito com ID " + idHabito + " não encontrado."));

        Optional<DiasHabito> registroExistente = diasHabitoRepository.findByHabitoIdAndData(idHabito, today);
        
		if (registroExistente.isPresent()) {
            throw new IllegalStateException("Hábito '" + habito.getNome() + "' já foi iniciado hoje.");
        }
		DiasHabito novoRegistro = new DiasHabito();
        novoRegistro.setHabito(habito);
        novoRegistro.setData(today);
        novoRegistro.setHoraInicio(LocalDateTime.now()); 
        
        diasHabitoRepository.save(novoRegistro);
	}
	
	// busca o ultimo streak do habito
	public List<DiasHabito> buscarStreakHabito(Long idHabito) {
		return null;
	}
	
}
