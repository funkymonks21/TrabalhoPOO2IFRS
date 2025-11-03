package com.exercicioJPAFuncionario.service;

import com.exercicioJPAFuncionario.dao.FuncionarioDAO;
import com.exercicioJPAFuncionario.controller.Funcionario;
import com.exercicioJPAFuncionario.util.JpaUtil;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class FuncionarioService {
    public void cadastrarFuncionario(Funcionario funcionario) {    	
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            FuncionarioDAO dao = new FuncionarioDAO(em);
            dao.salvar(funcionario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao cadastrar o funcionario.", e);
        } finally {
            em.close();
        }
    }
              
    public void atualizarFuncionario(Funcionario funcionario) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            FuncionarioDAO dao = new FuncionarioDAO(em);
            dao.atualiza(funcionario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao cadastrar o funcionario.", e);
        } finally {
            em.close();
        }
    }
    
    public void excluirFuncionario(Funcionario funcionario) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            FuncionarioDAO dao = new FuncionarioDAO(em);
            dao.exclui(funcionario.getId());
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao excluir a funcionario.", e);
        } finally {
            em.close();
        }
    }
	
	public List<Funcionario> listarFuncionario() {
	    EntityManager em = JpaUtil.getEntityManager();
	    EntityTransaction transaction = em.getTransaction();
	    try {
	        FuncionarioDAO dao = new FuncionarioDAO(em);
	        List<Funcionario> l = dao.lista();
	        return l;
	    } catch (Exception e) {
	        if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        throw new RuntimeException("Erro ao listar funcionarios.", e);
	    } finally {
	        em.close();
	    }
    }

    @SuppressWarnings(value = { }/*"finally"*/)
	public Funcionario listarFuncionario(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            FuncionarioDAO dao = new FuncionarioDAO(em);
            Funcionario f = dao.lista(id);
            transaction.commit();
            em.close();
            return f;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao listar funcionario.", e);
        }
    }
}
