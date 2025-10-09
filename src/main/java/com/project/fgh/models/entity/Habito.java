package com.project.fgh.models.entity;

import java.time.LocalDate;
import java.util.List;

import com.project.fgh.models.enums.TipoFrequencia;
import com.project.fgh.models.enums.TipoHabito;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "habito")
@Getter
@Setter
public class Habito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false, updatable = false)
    private LocalDate dataCriacao;

    @Column(nullable = false)
    private Boolean ativo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoHabito tipoHabito;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoFrequencia tipoFrequencia;

    // Campo flexível para guardar configurações da frequência.
    // Ex: para DIAS_ESPECIFICOS -> "SEG,QUA,SEX"
    // Ex: para VEZES_POR_SEMANA -> "3"
    // Ex: para INTERVALO_DE_DIAS -> "2"
    @Column
    private String configFrequencia;

    @OneToMany(mappedBy = "habito", fetch = FetchType.LAZY)
    private List<DiasHabito> diasHabitos;

    public Habito() {
        this.dataCriacao = LocalDate.now();
        this.ativo = true;
    }

	public Habito(Long habitoId) {
		this.id = habitoId;
	}
}