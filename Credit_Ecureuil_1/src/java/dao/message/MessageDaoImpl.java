package dao.message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import utils.CreditEcureuilPU;

/**
 *
 * @author Romain
 */
public class MessageDaoImpl implements MessageDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public MessageDaoImpl(){
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
    public void save(MessageEntity me) {
        me = em.merge(me);
        em.persist(me);
    }

    @Override
    public MessageEntity find(String id) {
       return em.find(MessageEntity.class, id);
    }

    @Override
    @Transactional
    public void update(MessageEntity me) {
        em.merge(me);
    }

    @Override
    @Transactional
    public void remove(MessageEntity me) {
        em.remove(me);
    }
    
}
