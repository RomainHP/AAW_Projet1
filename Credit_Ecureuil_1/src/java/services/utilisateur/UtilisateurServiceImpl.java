package services.utilisateur;

import dao.utilisateur.UtilisateurDaoImpl;
import dao.utilisateur.UtilisateurEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rcharpen
 */
public class UtilisateurServiceImpl implements UtilisateurService{
    @Autowired
    UtilisateurDaoImpl UserDAO;

    public UtilisateurServiceImpl(){
	this.UserDAO = new UtilisateurDaoImpl();
    }
    
    @Override
    public void connexion(UtilisateurEntity ue) {
	if(UserDAO.find(ue) != null){
	    
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
    public boolean inscription(UtilisateurEntity ue) {
	if(UserDAO.find(ue) == null){
	    UserDAO.save(ue);
	    return true;
	}
	return false;
    }
    
}
