package dao.utilisateur.pro;

import dao.utilisateur.pro.UtilisateurProEntity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import utils.CreditEcureuilPU;

/**
 *
 * @author rcharpen
 */
@Repository
@Transactional
public class UtilisateurProDaoImpl implements UtilisateurProDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public UtilisateurProDaoImpl(){
        em = CreditEcureuilPU.getEntityManager();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEm() {
        return em;
    }
    
    @Override
    @Transactional
    public void save(UtilisateurProEntity ue) {
        ue = em.merge(ue);
	em.persist(ue);
    }

    @Override
    @Transactional
    public UtilisateurProEntity find(String identifiant) {
        return em.find(UtilisateurProEntity.class, identifiant);
    }
    
    @Override
    @Transactional
    public void update(UtilisateurProEntity ue) {
	em.merge(ue);
    }

    @Override
    @Transactional
    public void remove(UtilisateurProEntity ue) {
	em.remove(ue);
    }
    
}
