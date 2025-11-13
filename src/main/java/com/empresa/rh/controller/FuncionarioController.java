package com.empresa.rh.controller;

import com.empresa.rh.model.entity.Cargo;
import com.empresa.rh.model.entity.Departamento;
import com.empresa.rh.model.entity.Funcionario;
import com.empresa.rh.model.service.FuncionarioService;
import com.empresa.rh.service.CargoService;
import com.empresa.rh.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired private FuncionarioService funcionarioService;
    @Autowired private CargoService cargoService;
    @Autowired private DepartamentoService departamentoService;

    // Métodos para popular os dropdowns em todas as telas
    @ModelAttribute("cargos")
    public List<Cargo> getCargos() {
        return cargoService.listar();
    }

    @ModelAttribute("departamentos")
    public List<Departamento> getDepartamentos() {
        return departamentoService.listar();
    }

    @GetMapping
    public String listar(Model model,
                         @RequestParam(required = false) Long departamentoId,
                         @RequestParam(required = false) Long cargoId) {
        List<Funcionario> funcionarios = funcionarioService.listar();
        if (departamentoId != null) {
            funcionarios = funcionarios.stream()
                .filter(f -> f.getDepartamento() != null && f.getDepartamento().getId().equals(departamentoId))
                .collect(Collectors.toList());
        }
        if (cargoId != null) {
            funcionarios = funcionarios.stream()
                .filter(f -> f.getCargo() != null && f.getCargo().getId().equals(cargoId))
                .collect(Collectors.toList());
        }
        model.addAttribute("funcionarios", funcionarios);
        return "funcionarios/lista";
    }

    @GetMapping("/novo")
    public String mostrarFormularioDeCadastro(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        model.addAttribute("chefes", funcionarioService.listar()); // MOSTRA TODOS OS CHEFES
        model.addAttribute("pageTitle", "Cadastrar Novo Funcionário");
        return "funcionarios/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("funcionario") Funcionario funcionario, RedirectAttributes ra) {
        try {
            if (funcionario.getId() == null) {
                funcionarioService.criar(funcionario);
                ra.addFlashAttribute("message", "Funcionário cadastrado com sucesso!");
            } else {
                funcionarioService.atualizar(funcionario.getId(), funcionario);
                ra.addFlashAttribute("message", "Funcionário atualizado com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", "Erro: " + e.getMessage());
            return funcionario.getId() == null ? "redirect:/funcionarios/novo" : "redirect:/funcionarios/editar/" + funcionario.getId();
        }
        return "redirect:/funcionarios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicao(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        model.addAttribute("funcionario", funcionario);

        // UM FUNCIONARIO NÃO PODE SER CHEFE DELE MESMO
        List<Funcionario> chefesPotenciais = funcionarioService.listar().stream()
                .filter(f -> !f.getId().equals(id))
                .collect(Collectors.toList());
        model.addAttribute("chefes", chefesPotenciais);
        
        model.addAttribute("pageTitle", "Editar Funcionário");
        return "funcionarios/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            funcionarioService.excluir(id);
            ra.addFlashAttribute("message", "Funcionário excluído com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Não foi possível excluir. Verifique se o funcionário é chefe de alguém.");
        }
        return "redirect:/funcionarios";
    }
}
