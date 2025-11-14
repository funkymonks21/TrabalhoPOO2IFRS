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

        funcionario.setId(null);

        validarFuncionario(funcionario);

        if (funcionario.getChefe() != null && funcionario.getChefe().getId() != null) {
            Funcionario chefe = funcionarioRepository.findById(funcionario.getChefe().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Chefe não encontrado."));
            funcionario.setChefe(chefe);
        }

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

        funcionarioAtualizado.setId(id);

        validarFuncionario(funcionarioAtualizado);


        Funcionario novoChefe = null;
        if (funcionarioAtualizado.getChefe() != null && funcionarioAtualizado.getChefe().getId() != null) {
            novoChefe = funcionarioRepository.findById(funcionarioAtualizado.getChefe().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Chefe não encontrado."));
        }


        if (novoChefe != null) {
            validarChefe(funcionarioExistente, novoChefe);
        }

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


        if (funcionarioRepository.existsByCpf(funcionario.getCpf())) {
            Funcionario existente = funcionarioRepository.findByCpf(funcionario.getCpf());
            if (funcionario.getId() == null || !existente.getId().equals(funcionario.getId())) {
                throw new IllegalArgumentException("Já existe um funcionário com este CPF.");
            }
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

        // Se não tiver chefe, não valida regras de chefe
        if (funcionario.getChefe() == null) {
            return;
        }

        // Chefe não pode ser ele mesmo
        if (funcionario.getId() != null &&
            funcionario.getChefe().getId() != null &&
            funcionario.getChefe().getId().equals(funcionario.getId())) {
            throw new IllegalArgumentException("Um funcionário não pode ser seu próprio chefe.");
        }
    }

    private boolean emailValido(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(regex).matcher(email).matches();
    }
//validações de chefe
    private void validarChefe(Funcionario funcionario, Funcionario novoChefe) {

        // Regra 1 — não pode ser chefe de si mesmo
        if (funcionario.getId().equals(novoChefe.getId())) {
            throw new IllegalArgumentException("Um funcionário não pode ser chefe dele mesmo.");
        }

        // Regra 2 — prevenir ciclos hierárquicos
        Funcionario atual = novoChefe;
        while (atual != null) {
            if (atual.getId().equals(funcionario.getId())) {
                throw new IllegalArgumentException(
                        "Ciclo detectado: o novo chefe é subordinado do funcionário."
                );
            }
            atual = atual.getChefe();
        }
    }
}
