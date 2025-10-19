package com.br.farney.crudapi.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MateriasSearchDTO {
    private String categoria;
    private String nome;
    private String unidade;
    private BigDecimal minValor;
    private BigDecimal maxValor;
}
