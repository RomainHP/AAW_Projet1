package dao.entreprise;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import utils.oldCreditEcureuilPU;

/**
 *
 * @author romain
 */
@Repository
@Transactional
public class EntrepriseDaoImpl implements EntrepriseDao {
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
        em.remove(ee);
    }
}
