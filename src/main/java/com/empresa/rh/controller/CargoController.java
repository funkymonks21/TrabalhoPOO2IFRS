package com.empresa.rh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.empresa.rh.model.entity.Cargo;
import com.empresa.rh.model.service.CargoService;

@Controller
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cargos", cargoService.listar());
        return "cargos/lista";
    }

    @GetMapping("/novo")
    public String mostrarFormularioDeCadastro(Model model) {
        model.addAttribute("cargo", new Cargo());
        model.addAttribute("pageTitle", "Cadastrar Novo Cargo");
        return "cargos/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("cargo") Cargo cargo, RedirectAttributes ra) {
        try {
            if (cargo.getId() == null) {
                cargoService.criar(cargo);
                ra.addFlashAttribute("message", "Cargo cadastrado com sucesso!");
            } else {
                cargoService.atualizar(cargo.getId(), cargo);
                ra.addFlashAttribute("message", "Cargo atualizado com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", "Erro: " + e.getMessage());
            return cargo.getId() == null ? "redirect:/cargos/novo" : "redirect:/cargos/editar/" + cargo.getId();
        }
        return "redirect:/cargos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("cargo", cargoService.buscarPorId(id));
        model.addAttribute("pageTitle", "Editar Cargo");
        return "cargos/formulario";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            cargoService.excluir(id);
            ra.addFlashAttribute("message", "Cargo excluído com sucesso!");
        } catch (Exception e) {
            // Pode haver erro se o cargo estiver associado a um funcionário (ConstraintViolationException)
            ra.addFlashAttribute("error", "Não foi possível excluir o cargo. Verifique se ele não está em uso por algum funcionário.");
        }
        return "redirect:/cargos";
    }
}
