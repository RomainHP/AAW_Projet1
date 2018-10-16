package dao.compte;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import utils.CreditEcureuilPU;

@Repository
@Transactional
public class CompteDaoImpl implements CompteDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public CompteDaoImpl(){
        em = CreditEcureuilPU.getEntityManager();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    @Override
    @Transactional
    public void save(CompteEntity ce){
        ce = em.merge(ce);
        em.persist(ce);
    }
    
    @Override
    public CompteEntity find(Long id){
        return em.find(CompteEntity.class, id);
    }

    @Override
    @Transactional
    public void update(CompteEntity ce) {
	em.merge(ce);
    }

    @Override
    @Transactional
    public void remove(CompteEntity ce) {
        em.remove(ce);
    }

}
