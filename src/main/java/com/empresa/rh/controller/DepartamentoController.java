package com.exercicioJPAFuncionario.controller;

import com.exercicioJPAFuncionario.entity.Departamento;
import com.exercicioJPAFuncionario.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    // Criar novo departamento
    @PostMapping
    public Departamento createDepartamento(@RequestBody Departamento departamento) {
        departamento.setId(null);
        return departamentoService.salvar(departamento);
    }

    // Listar todos os departamentos
    @GetMapping
    public List<Departamento> getAllDepartamentos() {
        return departamentoService.listarTodos();
    }

    // Buscar um departamento por ID
    @GetMapping("/{id}")
    public Departamento getDepartamentoById(
            @Parameter(description = "ID do departamento a ser buscado", required = true, example = "1")
            @PathVariable("id") Long id) {
        return departamentoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));
    }

    // Atualizar departamento
    @PutMapping("/{id}")
    public Departamento updateDepartamento(
            @PathVariable("id") Long id,
            @RequestBody Departamento departamentoDetails) {

        Departamento departamento = departamentoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Departamento não encontrado"));

        departamento.setNome(departamentoDetails.getNome());
        departamento.setDescricao(departamentoDetails.getDescricao());

        return departamentoService.atualizar(departamento);
    }

    // Deletar departamento
    @DeleteMapping("/{id}")
    public void deleteDepartamento(
            @Parameter(description = "ID do departamento a ser excluído", required = true, example = "1")
            @PathVariable("id") Long id) {
        departamentoService.excluir(id);
    }
}
