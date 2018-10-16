package dao.utilisateur;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import utils.CreditEcureuilPU;

/**
 *
 * @author rcharpen
 */
@Repository
@Transactional
public class UtilisateurDaoImpl implements UtilisateurDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public UtilisateurDaoImpl(){
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
    public void update(UtilisateurEntity oldUE, UtilisateurEntity newUE) {
//	UtilisateurEntity ue = find(oldUE);
//	em.getTransaction().begin();
//	ue.setIdentifiant(newUE.getIdentifiant());
//	ue.setMotDePasse(newUE.getMotDePasse());
//	//ue.setComptes(newUE.getComptes());
//	em.getTransaction().commit();
    }

    @Override
    public void remove(UtilisateurEntity ue) {
//	UtilisateurEntity UEdelete = find(ue);
//	em.getTransaction().begin();
//	em.remove(UEdelete);
//	em.getTransaction().commit();
    }
    
}
