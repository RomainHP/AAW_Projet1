package dao.message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Romain
 */
@Repository
public class MessageDaoImpl implements MessageDao {
    
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
    public void save(MessageEntity me) {
        me = em.merge(me);
        em.persist(me);
    }

    @Override
    public MessageEntity find(Long id) {
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
