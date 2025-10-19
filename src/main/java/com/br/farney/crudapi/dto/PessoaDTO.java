package com.br.farney.crudapi.dto;

import lombok.Data;

@Data
public class PessoaDTO {
    private String nome;
    private int idade;
    private String tipo; // "FISICA" ou "JURIDICA"
    private String cpf;  // apenas para pessoa física
    private String cnpj; // apenas para pessoa jurídica
    private String ddd; // dois ou mais dígitos do DDD
    private String telefone; // número do telefone sem DDD
}