package com.project.fgh.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.fgh.models.entity.Habito;
import com.project.fgh.repository.HabitoRepository;
import com.project.fgh.services.exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HabitoService {

    private final HabitoRepository habitoRepository;
     //Salva ou atualiza um hábito. Se o hábito tiver um ID, ele é atualizado.
    @Transactional
    public Habito salvar(Habito habito) {
		// TODO: Adicionar validações de nome e tratamento de exceções 
        return habitoRepository.save(habito);
    }

    @Transactional(readOnly = true)
    public Habito buscarId(Long id) {
        return habitoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hábito com ID " + id + " não encontrado."));
    }

    //Busca um hábito pelo seu nome (case-insensitive).
    @Transactional(readOnly = true)
    public Habito buscarNome(String nome) {
        return habitoRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ResourceNotFoundException("Hábito com nome '" + nome + "' não encontrado."));
    }

    @Transactional(readOnly = true)
    public List<Habito> listarTodos() {
        return habitoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Habito> listarAtivos() {
        return habitoRepository.findByAtivoTrue();
    }

    @Transactional
    public void excluir(Long id) {
        if (!habitoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hábito com ID " + id + " não encontrado para exclusão.");
        }
        habitoRepository.deleteById(id);
    }

    @Transactional
    public Habito ativarDesativar(Long id) {
        Habito habito = buscarId(id); 
        habito.setAtivo(!habito.getAtivo()); 
        return habitoRepository.save(habito);
    }
}