package dao.transaction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TransactionDaoImpl implements TransactionDao {

    @PersistenceContext(unitName="CreditEcureuilPU")
    EntityManager em;
    
    @Override
    public void save(TransactionEntity ue) {
	ue = em.merge(ue);
	em.persist(ue);
    }

    @Override
    public TransactionEntity find(String identifiant) {
	return em.find(TransactionEntity.class, identifiant);
    }

    @Override
    public void update(TransactionEntity ue) {
	em.merge(ue);
    }

    @Override
    public void remove(TransactionEntity ue) {
        ue = em.merge(ue);
        em.remove(ue);
    }       
}
