package com.empresa.rh.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.empresa.rh.model.entity.Departamento;
import com.empresa.rh.model.repository.DepartamentoRepository;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Departamento criar(Departamento departamento) {
        validarDepartamento(departamento);
        departamento.setId(null);

        if (departamentoRepository.existsByNome(departamento.getNome())) {
            throw new IllegalArgumentException("Já existe um departamento com o nome informado.");
        }

        return departamentoRepository.save(departamento);
    }

    public List<Departamento> listar() {
        return departamentoRepository.findAll();
    }

    public Departamento buscarPorId(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Departamento não encontrado com ID: " + id));
    }

    public Departamento atualizar(Long id, Departamento departamentoAtualizado) {
        Departamento departamentoExistente = buscarPorId(id);
        validarDepartamento(departamentoAtualizado);

        if (!departamentoExistente.getNome().equalsIgnoreCase(departamentoAtualizado.getNome())
                && departamentoRepository.existsByNome(departamentoAtualizado.getNome())) {
            throw new IllegalArgumentException("Já existe outro departamento com esse nome.");
        }

        departamentoExistente.setNome(departamentoAtualizado.getNome());
        departamentoExistente.setDescricao(departamentoAtualizado.getDescricao());

        return departamentoRepository.save(departamentoExistente);
    }

    public void excluir(Long id) {
        Departamento departamento = buscarPorId(id);
        departamentoRepository.delete(departamento);
    }

    private void validarDepartamento(Departamento departamento) {
        if (departamento.getNome() == null || departamento.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do departamento é obrigatório.");
        }

        if (departamento.getNome().length() > 100) {
            throw new IllegalArgumentException("O nome do departamento não pode ter mais que 100 caracteres.");
        }

        if (departamento.getDescricao() != null && departamento.getDescricao().length() > 255) {
            throw new IllegalArgumentException("A descrição do departamento não pode ter mais que 255 caracteres.");
        }
    }
}
