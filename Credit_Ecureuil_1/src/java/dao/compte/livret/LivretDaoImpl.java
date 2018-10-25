package dao.compte.livret;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LivretDaoImpl implements LivretDao {
    
    @PersistenceContext(unitName="CreditEcureuilPU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    @Override
    @Transactional
    public void save(LivretEntity ce){
        ce = em.merge(ce);
        em.persist(ce);
    }
    
    @Override
    public LivretEntity find(Long id){
        return em.find(LivretEntity.class, id);
    }

    @Override
    @Transactional
    public void update(LivretEntity ce) {
	em.merge(ce);
    }

    @Override
    @Transactional
    public void remove(LivretEntity ce) {
        ce = em.merge(ce);
        em.remove(ce);
    }

}
