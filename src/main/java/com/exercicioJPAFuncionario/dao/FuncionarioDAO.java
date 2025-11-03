package com.exercicioJPAFuncionario.dao;

//import java.math.BigInteger;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import com.exercicioJPAFuncionario.controller.Funcionario;

public class FuncionarioDAO {
   private EntityManager em;
   public FuncionarioDAO(EntityManager em) {
       this.em = em;
   }
  
   public void salvar(Funcionario funcionario) {
       em.persist(funcionario); // Retrieve the ID
   }
   
   // Outros métodos: buscar, remover, etc.
   public Funcionario lista(int id) {
   	Funcionario funcionario = em.find(Funcionario.class, id);       
       return funcionario;
   }
  
   public List<Funcionario> lista() {
       List<Funcionario> funcionarios = em.createQuery("FROM " + Funcionario.class.getName()).getResultList();
       return funcionarios;
   }
   public void atualiza(Funcionario funcionario) {
       em.merge(funcionario);
   }
   public void exclui(int i) {
       Funcionario funcionario = em.find(Funcionario.class, i);
       if (funcionario != null) {
           em.remove(funcionario);
       }
   }
}

