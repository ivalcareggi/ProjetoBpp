package com.project.fgh.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.fgh.models.entity.Habito;
import com.project.fgh.repository.HabitoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HabitoService {
	
	private final HabitoRepository habitoRepository;
	
	// metodo de salvar
	public void salvar(Habito habito) {}
	
	// buscar com base no nome
	public Habito buscar(String nome) {
		return null;
	}
	
	// buscar com base no id
	public Habito buscar(Long id) {
		return null;
	}
	
	// listar todos habitos
	public List<Habito> listar(){
		return null;
	}
	
	// deletar um habito
	public void excluir(Long id) {}
	
	// desativar/ativar um habito
	public void ativarDesativar(Long id) {}
}
