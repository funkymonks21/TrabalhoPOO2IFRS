package com.exercicioJPAFuncionario.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {


    // A fábrica é criada uma única vez e mantida estaticamente.
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("recursoshumanos");

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
    
    public static void closeEntityManagerFactory() {
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}

