package com.br.farney.crudapi.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Materias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Categoria ex: "Plástico", "Metal", "Vidro", "Papel"
     */
    private String categoria;

    /**
     * Nome do material ex: "Garrafa PET 1.5L", "Lata de Alumínio"
     */
    private String nome;

    /**
     * Unidade de comercialização ex: "unidade", "kg"
     */
    private String unidade;

    /**
     * Valor fictício (padrão R$) por unidade ou por kg, conforme a unidade
     */
    private BigDecimal valor;

}
