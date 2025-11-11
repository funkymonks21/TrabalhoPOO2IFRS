package com.empresa.rh.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.empresa.rh.model.Funcionario;
import com.empresa.rh.model.FuncionarioRepository;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

@RestController
@RequestMapping("/recursoshumanos")
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Método para criar novo funcionario
    @PostMapping
    public Funcionario criaFuncionario(@RequestBody Funcionario funcionario) {
    	funcionario.setId(null);
    	System.out.println(funcionario.toString());
        return funcionarioRepository.save(funcionario);
    }

    // Método para listar todos as tarefas
    @GetMapping
    public List<Funcionario> getAllTarefas() {
        return tarefaRepository.findAll();
    }

    // Método para buscar um tarefa por ID
    @GetMapping("/{id}")
    public Tarefa getTarefaById(
        @Parameter(description = "ID of the product to retrieve", required = true, example = "123")
        @PathVariable("id") Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    // Método para atualizar um produto
    @PutMapping("/{id}")
    public Tarefa updateTarefa(
    		@PathVariable("id") Long id,
    		@Parameter(description = "ID of the product to retrieve", required = true, example = "123")
    		@RequestBody Tarefa tarefaDetails) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrado"));

        tarefa.setDescricao(tarefaDetails.getDescricao());
        tarefa.setFinalizado(tarefaDetails.isFinalizado());
        tarefa.setDataFinalizacao(tarefaDetails.getDataFinalizacao());

        return tarefaRepository.save(tarefa);
    }

    // Método para deletar um produto
    @DeleteMapping("/{id}")
    public void deleteTarefa(
    		@Parameter(description = "ID of the product to retrieve", required = true, example = "123")
    		@PathVariable("id") Long id)  {
        tarefaRepository.deleteById(id);
    }
}
