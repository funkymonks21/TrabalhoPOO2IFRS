package com.empresa.rh.model.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicUpdate
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    private String email;
    private double salario;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //Manter data durante edição
    private LocalDate data_admissao;

    //@ManyToOne = 
    //@JoinColumn = 
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "chefe_id")
    private Funcionario chefe;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public LocalDate getData_admissao() {
        return data_admissao;
    }

    public void setData_admissao(LocalDate data_admissao) {
        this.data_admissao = data_admissao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Funcionario getChefe() {
        return chefe;
    }

    public void setChefe(Funcionario chefe) {
        this.chefe = chefe;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return 
            "Funcionario [id = " + id +
            ", Nome = " + nome +
            ", CPF = " + cpf +
            ", Email = " + email +
            ", Salário = " + salario +
            ", Data de admissão = " + data_admissao +
            ", Cargo = " + (cargo != null ? cargo.getNome() : "N/A") +
            ", Departamento = " + (departamento != null ? departamento.getNome() : "N/A") +
            ", Chefe = " + (chefe != null ? chefe.getNome() : "N/A") +
            "]";
    }
}

