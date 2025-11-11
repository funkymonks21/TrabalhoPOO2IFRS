package com.empresa.rh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.empresa.rh.model.entity.Departamento;
import com.empresa.rh.service.DepartamentoService;
import java.util.List;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @PostMapping
    public Departamento criar(@RequestBody Departamento departamento) {
        return departamentoService.criar(departamento);
    }

    @GetMapping
    public List<Departamento> listar() {
        return departamentoService.listar();
    }

    @GetMapping("/{id}")
    public Departamento buscarPorId(@PathVariable Long id) {
        return departamentoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Departamento atualizar(@PathVariable Long id, @RequestBody Departamento departamento) {
        return departamentoService.atualizar(id, departamento);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        departamentoService.excluir(id);
    }
}
