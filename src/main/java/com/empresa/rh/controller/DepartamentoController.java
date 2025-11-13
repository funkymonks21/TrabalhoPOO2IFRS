package com.empresa.rh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.empresa.rh.model.entity.Departamento;
import com.empresa.rh.service.DepartamentoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("departamentos", departamentoService.listar());
        return "departamentos/lista";
    }

    @GetMapping("/novo")
    public String mostrarFormularioDeCadastro(Model model) {
        model.addAttribute("departamento", new Departamento());
        model.addAttribute("pageTitle", "Cadastrar Novo Departamento");
        return "departamentos/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("departamento") Departamento departamento, RedirectAttributes ra) {
        try {
            if (departamento.getId() == null) {
                departamentoService.criar(departamento);
                ra.addFlashAttribute("message", "Departamento cadastrado com sucesso!");
            } else {
                departamentoService.atualizar(departamento.getId(), departamento);
                ra.addFlashAttribute("message", "Departamento atualizado com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", "Erro: " + e.getMessage());
            // Se der erro, redireciona para a página que originou a requisição
            return departamento.getId() == null ? "redirect:/departamentos/novo" : "redirect:/departamentos/editar/" + departamento.getId();
        }
        return "redirect:/departamentos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("departamento", departamentoService.buscarPorId(id));
        model.addAttribute("pageTitle", "Editar Departamento");
        return "departamentos/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            departamentoService.excluir(id);
            ra.addFlashAttribute("message", "Departamento excluído com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Não foi possível excluir o departamento.");
        }
        return "redirect:/departamentos";
    }
}
