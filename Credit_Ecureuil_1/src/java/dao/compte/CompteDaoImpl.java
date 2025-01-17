package dao.compte;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CompteDaoImpl implements CompteDao {
    
    @PersistenceContext(unitName="CreditEcureuilPU")
    EntityManager em;
    
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
        ce = em.merge(ce);
        em.remove(ce);
    }
    
    @Override
    public List<CompteEntity> findAll(){
        Query q = em.createQuery("SELECT c FROM CompteEntity c ORDER BY c.id");
        return q.getResultList();
    }
    
    @Override
    public List<CompteEntity> findAllOpenAccounts(){
        Query q = em.createQuery("SELECT c FROM CompteEntity c WHERE c.cloture=false ORDER BY c.id");
        return q.getResultList();
    }

}
