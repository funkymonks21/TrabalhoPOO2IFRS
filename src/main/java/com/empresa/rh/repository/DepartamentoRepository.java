package com.empresa.rh.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.empresa.rh.model.entity.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    boolean existsByNome(String nome);
}
