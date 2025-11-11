package com.exercicioJPAFuncionario.controller;

import com.exercicioJPAFuncionario.entity.Cargo;
import com.exercicioJPAFuncionario.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    // Criar novo cargo
    @PostMapping
    public Cargo createCargo(@RequestBody Cargo cargo) {
        cargo.setId(null);
        return cargoService.salvar(cargo);
    }

    // Listar todos os cargos
    @GetMapping
    public List<Cargo> getAllCargos() {
        return cargoService.listarTodos();
    }

    // Buscar cargo por ID
    @GetMapping("/{id}")
    public Cargo getCargoById(
            @Parameter(description = "ID do cargo a ser buscado", required = true, example = "1")
            @PathVariable("id") Long id) {
        return cargoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cargo não encontrado"));
    }

    // Atualizar cargo
    @PutMapping("/{id}")
    public Cargo updateCargo(
            @PathVariable("id") Long id,
            @RequestBody Cargo cargoDetails) {

        Cargo cargo = cargoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cargo não encontrado"));

        cargo.setNome(cargoDetails.getNome());
        cargo.setDescricao(cargoDetails.getDescricao());
        cargo.setSalarioBase(cargoDetails.getSalarioBase());

        return cargoService.atualizar(cargo);
    }

    // Deletar cargo
    @DeleteMapping("/{id}")
    public void deleteCargo(
            @Parameter(description = "ID do cargo a ser excluído", required = true, example = "1")
            @PathVariable("id") Long id) {
        cargoService.excluir(id);
    }
}
