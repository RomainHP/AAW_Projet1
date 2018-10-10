package dao.compte;

import dao.utilisateur.UtilisateurEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CompteDaoImpl implements CompteDao {
    
    @PersistenceContext
    private EntityManager em;
    
    public CompteDaoImpl(){
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("CreditEcureuilPU");
	this.em = emf.createEntityManager();
    }

    @Override
    public List<CompteEntity> getAccounts(String login) {
	Query query = em.createQuery("SELECT COMPTE.* FROM COMPTE, UTILISATEUR WHERE COMPTE.PROPRIETAIRE.IDENTIFIANT = UTILISATEUR.IDENTIFIANT FIND UTILISATEUR.IDENTIFIANT = :login");
	 return query.setParameter("login", login).getResultList();
    }

    @Override
    public void createNewAccount(CompteEntity ce) {
	em.merge(ce);
    }

    @Override
    public boolean deleteAccount(CompteEntity acc) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
