package com.empresa.rh.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.empresa.rh.model.entity.Cargo;
import com.empresa.rh.model.repository.CargoRepository;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public Cargo criar(Cargo cargo) {
        validarCargo(cargo);
        cargo.setId(null);

        if (cargoRepository.existsByNome(cargo.getNome())) {
            throw new IllegalArgumentException("Já existe um cargo com o nome informado.");
        }

        return cargoRepository.save(cargo);
    }

    public List<Cargo> listar() {
        return cargoRepository.findAll();
    }

    public Cargo buscarPorId(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cargo não encontrado com ID: " + id));
    }

    public Cargo atualizar(Long id, Cargo cargoAtualizado) {
        Cargo cargoExistente = buscarPorId(id);
        validarCargo(cargoAtualizado);

        if (!cargoExistente.getNome().equalsIgnoreCase(cargoAtualizado.getNome())
                && cargoRepository.existsByNome(cargoAtualizado.getNome())) {
            throw new IllegalArgumentException("Já existe outro cargo com esse nome.");
        }

        cargoExistente.setNome(cargoAtualizado.getNome());
        cargoExistente.setDescricao(cargoAtualizado.getDescricao());

        return cargoRepository.save(cargoExistente);
    }

    public void excluir(Long id) {
        Cargo cargo = buscarPorId(id);
        cargoRepository.delete(cargo);
    }

    private void validarCargo(Cargo cargo) {
        if (cargo.getNome() == null || cargo.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do cargo é obrigatório.");
        }

        if (cargo.getNome().length() > 100) {
            throw new IllegalArgumentException("O nome do cargo não pode ter mais que 100 caracteres.");
        }

        if (cargo.getDescricao() != null && cargo.getDescricao().length() > 255) {
            throw new IllegalArgumentException("A descrição do cargo não pode ter mais que 255 caracteres.");
        }
    }
}
