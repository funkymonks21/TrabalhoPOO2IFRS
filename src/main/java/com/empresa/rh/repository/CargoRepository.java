package com.empresa.rh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empresa.rh.model.entity.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo,String>{

}

