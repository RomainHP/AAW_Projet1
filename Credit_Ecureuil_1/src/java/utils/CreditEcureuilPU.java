/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author romain
 */
public final class CreditEcureuilPU {
    
    private static EntityManager em=null;
    
    public static EntityManager getEntityManager(){
        if (em==null){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CreditEcureuilPU");
            em = emf.createEntityManager();
        }
        return em;
    }
}
