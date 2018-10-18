package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author romain
 */
public final class oldCreditEcureuilPU {
    
    private static EntityManager em=null;
    
    public static EntityManager getEntityManager(){
        if (em==null){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CreditEcureuilPU");
            em = emf.createEntityManager();
        }
        return em;
    }
}
