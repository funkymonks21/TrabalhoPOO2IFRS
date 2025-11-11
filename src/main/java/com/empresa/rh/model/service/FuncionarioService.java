package com.empresa.rh.service;

import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.empresa.rh.model.entity.Funcionario;
import com.empresa.rh.model.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario salvar(Funcionario funcionario) {
        validarFuncionario(funcionario);
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario atualizar(Long id, Funcionario funcionarioAtualizado) {
        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));

        funcionarioAtualizado.setId(funcionarioExistente.getId());
        validarFuncionario(funcionarioAtualizado);
        return funcionarioRepository.save(funcionarioAtualizado);
    }

    public void excluir(Long id) {
        funcionarioRepository.deleteById(id);
    }

    public Optional<Funcionario> buscarPorId(Long id) {
        return funcionarioRepository.findById(id);
    }

    public Iterable<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    private void validarFuncionario(Funcionario funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do funcionário é obrigatório.");
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

        if (funcionario.getData_contratacao() == null) {
            throw new IllegalArgumentException("A data de contratação é obrigatória.");
        }

        if (funcionario.getChefe() != null) {
            if (funcionario.getChefe().getId() == null) {
                throw new IllegalArgumentException("O chefe deve estar cadastrado previamente.");
            }
            if (funcionario.getId() != null && funcionario.getChefe().getId().equals(funcionario.getId())) {
                throw new IllegalArgumentException("Um funcionário não pode ser seu próprio chefe.");
            }
        }
    }

    private boolean emailValido(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(regex).matcher(email).matches();
    }
}
