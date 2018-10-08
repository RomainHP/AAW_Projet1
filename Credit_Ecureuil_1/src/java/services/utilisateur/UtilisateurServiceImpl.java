package services.utilisateur;

import dao.utilisateur.UtilisateurDaoImpl;
import dao.utilisateur.UtilisateurEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rcharpen
 */
public class UtilisateurServiceImpl implements UtilisateurService {
    
    @Autowired
    UtilisateurDaoImpl dao;

    public UtilisateurDaoImpl getDao() {
        return dao;
    }

    public void setDao(UtilisateurDaoImpl dao) {
        this.dao = dao;
    }

    public UtilisateurServiceImpl(){
	this.dao = new UtilisateurDaoImpl();
    }
    
    @Override
    public void connexion(UtilisateurEntity ue) {
	if(dao.find(ue.getIdentifiant()) != null){
	    
	}
    }

    @Override
    public void deconnexion() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void profil(UtilisateurEntity ue) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean inscription(String identifiant, String motDePasse) {
	if(dao.find(identifiant) == null){
            UtilisateurEntity utilisateur = new UtilisateurEntity(identifiant, motDePasse);
	    dao.save(utilisateur);
	    return true;
	}
	return false;
    }
    
}
