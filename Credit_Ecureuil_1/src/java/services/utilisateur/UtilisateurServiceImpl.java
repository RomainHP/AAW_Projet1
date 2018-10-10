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
    private UtilisateurDaoImpl dao;

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
    public boolean connexion(String ident, String mdp) {
	if(dao.find(ident) != null){
	    if(dao.getUserMdp(ident).equals(mdp))
		return true;
	}
	return false;
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
