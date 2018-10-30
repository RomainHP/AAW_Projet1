package dao.compte.comptejoint;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CompteJointDaoImpl implements CompteJointDao {
    
    @PersistenceContext(unitName="CreditEcureuilPU")
    EntityManager em;
    
    @Override
    @Transactional
    public void save(CompteJointEntity ce){
        ce = em.merge(ce);
        em.persist(ce);
    }
    
    @Override
    public CompteJointEntity find(Long id){
        return em.find(CompteJointEntity.class, id);
    }

    @Override
    @Transactional
    public void update(CompteJointEntity ce) {
	em.merge(ce);
    }

    @Override
    @Transactional
    public void remove(CompteJointEntity ce) {
        ce = em.merge(ce);
        em.remove(ce);
    }

}
