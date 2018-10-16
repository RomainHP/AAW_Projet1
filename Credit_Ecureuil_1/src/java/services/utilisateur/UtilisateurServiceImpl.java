package services.utilisateur;

import dao.entreprise.EntrepriseDao;
import dao.entreprise.EntrepriseDaoImpl;
import dao.entreprise.EntrepriseEntity;
import dao.utilisateur.UtilisateurDaoImpl;
import dao.utilisateur.UtilisateurEntity;
import dao.utilisateur.UtilisateurProEntity;
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
	    dao.save(utilisateur);
	    return true;
	}
	return false;
    }

    @Override
    public boolean inscriptionPro(String identifiant, String motDePasse, String nomEntreprise, long siret) {
        if(dao.find(identifiant) == null){
            EntrepriseDao entrepriseDao = new EntrepriseDaoImpl();
            if (entrepriseDao.find(siret) == null){
                UtilisateurProEntity utilisateur = new UtilisateurProEntity(identifiant, motDePasse);
                EntrepriseEntity entreprise = new EntrepriseEntity(siret,nomEntreprise,utilisateur);
                dao.save(utilisateur);
                entrepriseDao.save(entreprise);
                utilisateur.setEntreprise(entreprise);
                dao.save(utilisateur);
            }
	    return true;
	}
	return false;
    }
    
}
