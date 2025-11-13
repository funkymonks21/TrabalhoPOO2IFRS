package com.empresa.rh.model.service;

import com.empresa.rh.model.entity.Funcionario;

import com.empresa.rh.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario criar(Funcionario funcionario) {
        validarFuncionario(funcionario);
        funcionario.setId(null);
        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> listar() {
        return funcionarioRepository.findAll();
    }

    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado com ID: " + id));
    }

    public Funcionario atualizar(Long id, Funcionario funcionarioAtualizado) {
        Funcionario funcionarioExistente = buscarPorId(id);
        funcionarioAtualizado.setId(funcionarioExistente.getId());
        validarFuncionario(funcionarioAtualizado);
        return funcionarioRepository.save(funcionarioAtualizado);
    }

    public void excluir(Long id) {
        funcionarioRepository.deleteById(id);
    }

    private void validarFuncionario(Funcionario funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do funcionário é obrigatório.");
        }

        if (funcionario.getCpf() == null || funcionario.getCpf().isBlank()) {
            throw new IllegalArgumentException("O CPF é obrigatório.");
        }

        if (funcionarioRepository.existsByCpf(funcionario.getCpf()) &&
            (funcionario.getId() == null || 
             !funcionarioRepository.findByCpf(funcionario.getCpf()).getId().equals(funcionario.getId()))) {
            throw new IllegalArgumentException("Já existe um funcionário com este CPF.");
        }

        if (funcionario.getEmail() == null || !emailValido(funcionario.getEmail())) {
            throw new IllegalArgumentException("Email inválido.");
        }

        if (funcionario.getSalario() <= 0) {
            throw new IllegalArgumentException("O salário deve ser maior que zero.");
        }

        if (funcionario.getCargo() == null) {
            throw new IllegalArgumentException("O cargo é obrigatório.");
        }

        if (funcionario.getDepartamento() == null) {
            throw new IllegalArgumentException("O departamento é obrigatório.");
        }

        if (funcionario.getData_admissao() == null) {
            throw new IllegalArgumentException("A data de admissão é obrigatória.");
        }

        if (funcionario.getData_admissao().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de admissão não pode estar no futuro.");
        }

        if (funcionario.getId() != null && funcionario.getChefe().getId().equals(funcionario.getId())) {
            throw new IllegalArgumentException("Um funcionário não pode ser seu próprio chefe.");
        }
    }

    private boolean emailValido(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(regex).matcher(email).matches();
    }
}
