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
    public CompteEntity find(Long id){
        return em.find(CompteEntity.class, id);
    }

    @Override
    public void createNewAccount(CompteEntity ce) {
	ce = em.merge(ce);
	em.persist(ce);
    }

    @Override
    public boolean deleteAccount(CompteEntity acc) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
