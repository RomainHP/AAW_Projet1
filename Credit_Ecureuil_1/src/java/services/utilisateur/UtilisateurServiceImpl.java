package services.utilisateur;

import dao.compte.CompteDaoImpl;
import dao.compte.CompteEntity;
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
    
    @Autowired
    private CompteDaoImpl daoCompte;

    public UtilisateurDaoImpl getDao() {
        return dao;
    }

    public void setDao(UtilisateurDaoImpl dao) {
        this.dao = dao;
    }

    public UtilisateurServiceImpl(){
	this.dao = new UtilisateurDaoImpl();
	this.daoCompte = new CompteDaoImpl();
    }
    
    @Override
    public boolean connexion(String identifiant, String motDePasse) {
	if(dao.find(identifiant) != null){
	    if(dao.getUserMdp(identifiant).equals(motDePasse))
		return true;
	}
	return false;
    }

    @Override
    public UtilisateurEntity getUtilisateur(String identifiant) {
        return dao.find(identifiant);
    }

    @Override
    public boolean inscription(String identifiant, String motDePasse) {
	if(dao.find(identifiant) == null){
            UtilisateurEntity utilisateur = new UtilisateurEntity(identifiant, motDePasse);
	    CompteEntity compte = new CompteEntity("Compte courant", utilisateur);
	    dao.save(utilisateur);
	    daoCompte.createNewAccount(compte);
	    utilisateur.addSingleAccount(compte);
	    dao.save(utilisateur);
	    return true;
	}
	return false;
    }

    @Override
    public boolean inscriptionPro(String identifiant, String motDePasse, String entreprise, long siret) {
        if(dao.find(identifiant) == null){
            UtilisateurEntity utilisateur = new UtilisateurEntity(identifiant, motDePasse);
	    dao.save(utilisateur);
	    return true;
	}
	return false;
    }
    
}
