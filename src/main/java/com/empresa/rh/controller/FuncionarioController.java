package com.empresa.rh.controller;

import com.empresa.rh.model.entity.Funcionario;
import com.empresa.rh.model.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public Funcionario criarFuncionario(@RequestBody Funcionario funcionario) {
        return funcionarioService.criar(funcionario);
    }

    @GetMapping
    public List<Funcionario> listarFuncionarios() {
        return funcionarioService.listar();
    }

    @GetMapping("/{id}")
    public Funcionario buscarFuncionarioPorId(
            @Parameter(description = "ID do funcionário", required = true, example = "1")
            @PathVariable("id") Long id) {
        return funcionarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Funcionario atualizarFuncionario(
            @PathVariable("id") Long id,
            @RequestBody Funcionario funcionario) {
        return funcionarioService.atualizar(id, funcionario);
    }

    @DeleteMapping("/{id}")
    public void excluirFuncionario(
            @Parameter(description = "ID do funcionário", required = true, example = "1")
            @PathVariable("id") Long id) {
        funcionarioService.excluir(id);
    }
}
