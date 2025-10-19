package com.br.farney.crudapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.br.farney.crudapi.model.Materias;

public interface MateriasRepository extends JpaRepository<Materias, Long>, JpaSpecificationExecutor<Materias> {

}

//# sourceMappingURL=MateriasRepository.java.map
