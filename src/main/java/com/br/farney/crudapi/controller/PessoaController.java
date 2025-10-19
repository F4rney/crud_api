package com.br.farney.crudapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.farney.crudapi.dto.PessoaDTO;
import com.br.farney.crudapi.model.Pessoa;
import com.br.farney.crudapi.repository.PessoaRepository;

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
        pessoa.setTipo(pessoaDTO.getTipo());
        pessoa.setCpf(pessoaDTO.getCpf());
        pessoa.setCnpj(pessoaDTO.getCnpj());
        pessoa.setDdd(pessoaDTO.getDdd());
        pessoa.setTelefone(pessoaDTO.getTelefone());
        return service.save(pessoa);
    }

    @PostMapping("/fisica")
    public Pessoa criarFisica(@RequestBody PessoaDTO pessoaDTO) {
        if (pessoaDTO.getCpf() == null || pessoaDTO.getCpf().isBlank()) {
            throw new RuntimeException("CPF é obrigatório para pessoa física");
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setIdade(pessoaDTO.getIdade());
        pessoa.setTipo("FISICA");
        pessoa.setCpf(pessoaDTO.getCpf());
        pessoa.setDdd(pessoaDTO.getDdd());
        pessoa.setTelefone(pessoaDTO.getTelefone());
        return service.save(pessoa);
    }

    @PostMapping("/juridica")
    public Pessoa criarJuridica(@RequestBody PessoaDTO pessoaDTO) {
        if (pessoaDTO.getCnpj() == null || pessoaDTO.getCnpj().isBlank()) {
            throw new RuntimeException("CNPJ é obrigatório para pessoa jurídica");
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setIdade(pessoaDTO.getIdade());
        pessoa.setTipo("JURIDICA");
        pessoa.setCnpj(pessoaDTO.getCnpj());
        pessoa.setDdd(pessoaDTO.getDdd());
        pessoa.setTelefone(pessoaDTO.getTelefone());
        return service.save(pessoa);
    }

    /**
     * Cria um cadastro do zero (nome nulo/blank e idade 0 são permitidos).
     * Se for enviado ddd e telefone, serão gravados.
     */
    @PostMapping("/empty")
    public Pessoa criarVazio(@RequestBody(required = false) PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        if (pessoaDTO != null) {
            pessoa.setDdd(pessoaDTO.getDdd());
            pessoa.setTelefone(pessoaDTO.getTelefone());
        }
        // tipo, cpf, cnpj, nome e idade ficam nulos/zero
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
        pessoaExistente.setTipo(pessoaDTO.getTipo());
        pessoaExistente.setCpf(pessoaDTO.getCpf());
        pessoaExistente.setCnpj(pessoaDTO.getCnpj());
        pessoaExistente.setDdd(pessoaDTO.getDdd());
        pessoaExistente.setTelefone(pessoaDTO.getTelefone());

        return service.save(pessoaExistente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deleteById(id);
    }
}
