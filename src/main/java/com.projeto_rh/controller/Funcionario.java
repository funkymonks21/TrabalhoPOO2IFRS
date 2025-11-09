package com.projeto_rh.controller;

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
   private Long id;
   private String nome;
   private String email;
   @Temporal(TemporalType.DATE)
   private Calendar data_contratacao;
   private double salario;
   private Cargo cargo;
   private Funcionario chefe;
    
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

   public double getSalario() {
	   return salario;
   }

   public void setSalario(double salario) {
	   this.salario = salario;
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

   @Override
   public String toString() {
	   return 	"Funcionario [id = " + id +
				", nome = " + nome +
				", email = " + email +
				", data_contratacao = " + data_contratacao +
				", salario = " + salario +
				"]";
   }	
}
