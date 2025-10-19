package com.br.farney.crudapi.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.farney.crudapi.dto.MateriasSearchDTO;
import com.br.farney.crudapi.model.Materias;
import com.br.farney.crudapi.repository.MateriasRepository;

import jakarta.persistence.criteria.Predicate;

@RestController
@RequestMapping("/api/materias")
public class MateriasController {

    private final MateriasRepository repository;

    public MateriasController(MateriasRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Materias> buscar(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String unidade,
            @RequestParam(required = false) BigDecimal minValor,
            @RequestParam(required = false) BigDecimal maxValor
    ) {
        Specification<Materias> spec = buildSpec(categoria, nome, unidade, minValor, maxValor);
        return repository.findAll(spec);
    }

    @GetMapping("/search")
    public Page<Materias> buscarPaged(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String unidade,
            @RequestParam(required = false) BigDecimal minValor,
            @RequestParam(required = false) BigDecimal maxValor,
            Pageable pageable
    ) {
        Specification<Materias> spec = buildSpec(categoria, nome, unidade, minValor, maxValor);
        return repository.findAll(spec, pageable);
    }

    @org.springframework.web.bind.annotation.PostMapping("/search")
    public Page<Materias> buscarPorPost(@org.springframework.web.bind.annotation.RequestBody MateriasSearchDTO dto,
                                        Pageable pageable) {
        Specification<Materias> spec = buildSpec(dto.getCategoria(), dto.getNome(), dto.getUnidade(), dto.getMinValor(), dto.getMaxValor());
        return repository.findAll(spec, pageable);
    }

    private Specification<Materias> buildSpec(String categoria, String nome, String unidade, BigDecimal minValor, BigDecimal maxValor) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (categoria != null && !categoria.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("categoria")), categoria.toLowerCase()));
            }
            if (nome != null && !nome.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            }
            if (unidade != null && !unidade.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("unidade")), unidade.toLowerCase()));
            }
            if (minValor != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("valor"), minValor));
            }
            if (maxValor != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("valor"), maxValor));
            }

            if (predicates.isEmpty()) {
                return null; // sem restrição
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

}
