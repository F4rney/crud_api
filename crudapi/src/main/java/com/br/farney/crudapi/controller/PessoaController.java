package com.br.farney.crudapi.controller;

import org.springframework.web.bind.annotation.*;

import com.br.farney.crudapi.dto.PessoaDTO;
import com.br.farney.crudapi.model.Pessoa;
import com.br.farney.crudapi.repository.PessoaRepository;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaRepository service;

    public PessoaController(PessoaRepository service) {
        this.service = service;
    }

    @PostMapping
    public Pessoa criar(@RequestBody PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setIdade(pessoaDTO.getIdade());
        return service.save(pessoa);
    }

    @GetMapping
    public List<Pessoa> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Pessoa buscarPorId(@PathVariable Long id) {
        return service.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public Pessoa atualizar(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO) {
        Pessoa pessoaExistente = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com id: " + id));

        pessoaExistente.setNome(pessoaDTO.getNome());
        pessoaExistente.setIdade(pessoaDTO.getIdade());

        return service.save(pessoaExistente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deleteById(id);
    }
}
