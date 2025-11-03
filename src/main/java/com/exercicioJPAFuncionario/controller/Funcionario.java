package com.exercicioJPAFuncionario.controller;

import java.util.Calendar;

import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
@Entity
@DynamicUpdate
public class Funcionario {
   @Id
   @GeneratedValue
   private int id;
   private String nome;
   private String email;
   @Temporal(TemporalType.DATE)
   private Calendar data_contratacao;
   private float salario;
    
	public int getId() {
	return id;
}

	public void setId(int id) {
	this.id = id;
   }

   public String getNome() {
	return nome;
   }

   public void setNome(String nome) {
	this.nome = nome;
   }

   public String getEmail() {
	return email;
   }

   public void setEmail(String email) {
	this.email = email;
   }

   public Calendar getData_contratacao() {
	return data_contratacao;
   }

   public void setData_contratacao(Calendar data_contratacao) {
	this.data_contratacao = data_contratacao;
   }

   public float getSalario() {
	return salario;
   }

   public void setSalario(float salario) {
	this.salario = salario;
   }

	@Override
	public String toString() {
		return 	"Tarefa [id = " + id +
				", nome = " + nome +
				", email = " + email +
				", data_contratacao = " + data_contratacao +
				", salario = " + salario +
				"]";
	}	
}
