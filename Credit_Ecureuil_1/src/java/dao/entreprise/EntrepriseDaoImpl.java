package dao.entreprise;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import utils.CreditEcureuilPU;

/**
 *
 * @author romain
 */
@Repository
@Transactional
public class EntrepriseDaoImpl implements EntrepriseDao {
    @PersistenceContext
    private EntityManager em;
    
    public EntrepriseDaoImpl(){
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
    public void save(EntrepriseEntity ue) {
        ue = em.merge(ue);
	em.persist(ue);
    }

    @Override
    @Transactional
    public EntrepriseEntity find(Long siret) {
        return em.find(EntrepriseEntity.class, siret);
    }

    @Override
    public void update(EntrepriseEntity oldEE, EntrepriseEntity newEE) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(EntrepriseEntity ue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
