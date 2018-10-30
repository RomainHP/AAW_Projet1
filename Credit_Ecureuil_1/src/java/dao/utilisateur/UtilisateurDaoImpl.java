package dao.utilisateur;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rcharpen
 */
@Repository
public class UtilisateurDaoImpl implements UtilisateurDao {
    
    @PersistenceContext(unitName="CreditEcureuilPU")
    EntityManager em;
    
    @Override
    @Transactional
    public void save(UtilisateurEntity ue) {
        ue = em.merge(ue);
	em.persist(ue);
    }

    @Override
    @Transactional
    public UtilisateurEntity find(String identifiant) {
        return em.find(UtilisateurEntity.class, identifiant);
    }
    
    @Override
    public String getUserMdp(String identifiant) {
	UtilisateurEntity ue = this.find(identifiant);
	return ue.getMotDePasse();
    }

    @Override
    @Transactional
    public void update(UtilisateurEntity ue) {
	em.merge(ue);
    }

    @Override
    @Transactional
    public void remove(UtilisateurEntity ue) {
	ue = em.merge(ue);
        em.remove(ue);
    }
    
}
