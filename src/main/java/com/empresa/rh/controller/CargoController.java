package com.empresa.rh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.empresa.rh.model.entity.Cargo;
import com.empresa.rh.service.CargoService;
import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @PostMapping
    public Cargo criar(@RequestBody Cargo cargo) {
        return cargoService.criar(cargo);
    }

    @GetMapping
    public List<Cargo> listar() {
        return cargoService.listar();
    }

    @GetMapping("/{id}")
    public Cargo buscarPorId(@PathVariable Long id) {
        return cargoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Cargo atualizar(@PathVariable Long id, @RequestBody Cargo cargo) {
        return cargoService.atualizar(id, cargo);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        cargoService.excluir(id);
    }
}
