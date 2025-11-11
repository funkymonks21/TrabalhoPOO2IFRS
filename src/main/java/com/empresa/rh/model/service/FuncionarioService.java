package com.empresa.rh.service;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.empresa.rh.model.entity.Funcionario;
import com.empresa.rh.model.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario criar(Funcionario funcionario) {
        validarFuncionario(funcionario);
        funcionario.setId(null);

        if (funcionarioRepository.existsByEmail(funcionario.getEmail())) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com este e-mail.");
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
        validarFuncionario(funcionarioAtualizado);

        if (!funcionarioExistente.getEmail().equalsIgnoreCase(funcionarioAtualizado.getEmail())
                && funcionarioRepository.existsByEmail(funcionarioAtualizado.getEmail())) {
            throw new IllegalArgumentException("Já existe outro funcionário com esse e-mail.");
        }

        funcionarioExistente.setNome(funcionarioAtualizado.getNome());
        funcionarioExistente.setEmail(funcionarioAtualizado.getEmail());
        funcionarioExistente.setData_contratacao(funcionarioAtualizado.getData_contratacao());
        funcionarioExistente.setSalario(funcionarioAtualizado.getSalario());
        funcionarioExistente.setCargo(funcionarioAtualizado.getCargo());
        funcionarioExistente.setChefe(funcionarioAtualizado.getChefe());

        return funcionarioRepository.save(funcionarioExistente);
    }

    public void excluir(Long id) {
        Funcionario funcionario = buscarPorId(id);
        funcionarioRepository.delete(funcionario);
    }

    private void validarFuncionario(Funcionario funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do funcionário é obrigatório.");
        }

        if (funcionario.getEmail() == null || funcionario.getEmail().isBlank()) {
            throw new IllegalArgumentException("O e-mail do funcionário é obrigatório.");
        }

        if (funcionario.getSalario() <= 0) {
            throw new IllegalArgumentException("O salário deve ser um valor positivo.");
        }

        if (funcionario.getData_contratacao() == null) {
            throw new IllegalArgumentException("A data de contratação é obrigatória.");
        }

        Calendar hoje = Calendar.getInstance();
        if (funcionario.getData_contratacao().after(hoje)) {
            throw new IllegalArgumentException("A data de contratação não pode ser futura.");
        }

        if (funcionario.getChefe() != null && funcionario.getChefe().getId() != null 
                && funcionario.getChefe().getId().equals(funcionario.getId())) {
            throw new IllegalArgumentException("Um funcionário não pode ser chefe de si mesmo.");
        }
    }
}
