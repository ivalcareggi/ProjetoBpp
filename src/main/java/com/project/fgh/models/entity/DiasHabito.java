package com.project.fgh.models.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dias_habito")
@Getter
@Setter
@NoArgsConstructor
public class DiasHabito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habito_id", nullable = false)
    private Habito habito;
    
    // Campo para hábitos de checklist
    @Column(nullable = false)
    private Boolean concluido = false; 
    
    private LocalDateTime horaInicio;
    private LocalDateTime horaFim;
    
    // Campo para hábitos de duração
    private Integer duracaoEmMinutos;

    // Campo para hábitos de quantidade
    private Integer quantidadeRealizada;

    @Column(length = 500)
    private String observacoes;
}