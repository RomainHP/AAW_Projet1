package dao.utilisateur.pro;

import dao.utilisateur.pro.UtilisateurProEntity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rcharpen
 */
@Repository
public class UtilisateurProDaoImpl implements UtilisateurProDao {
    
    @PersistenceContext(unitName="CreditEcureuilPU")
    private EntityManager em;

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
	ue = em.merge(ue);
        em.remove(ue);
    }
    
}
