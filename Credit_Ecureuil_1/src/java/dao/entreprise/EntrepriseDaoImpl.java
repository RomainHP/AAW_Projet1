package dao.entreprise;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author romain
 */
@Repository
public class EntrepriseDaoImpl implements EntrepriseDao {
    
    @PersistenceContext(unitName="CreditEcureuilPU")
    EntityManager em;

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
    @Transactional
    public void update(EntrepriseEntity ee) {
        em.merge(ee);
    }

    @Override
    @Transactional
    public void remove(EntrepriseEntity ee) {
        ee = em.merge(ee);
        em.remove(ee);
    }
}
