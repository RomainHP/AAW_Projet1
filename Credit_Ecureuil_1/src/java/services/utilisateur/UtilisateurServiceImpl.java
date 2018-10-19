package services.utilisateur;

import dao.compte.CompteDao;
import dao.entreprise.EntrepriseDao;
import dao.entreprise.EntrepriseDaoImpl;
import dao.entreprise.EntrepriseEntity;
import dao.compte.CompteDaoImpl;
import dao.compte.CompteEntity;
import dao.utilisateur.UtilisateurDao;
import dao.utilisateur.UtilisateurDaoImpl;
import dao.utilisateur.UtilisateurEntity;
import dao.utilisateur.pro.UtilisateurProDaoImpl;
import dao.utilisateur.pro.UtilisateurProEntity;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author rcharpen
 */
@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    
    @Resource
    UtilisateurDao dao;
    
    @Resource
    CompteDao daoCompte;
    
    @Resource
    EntrepriseDao entrepriseDao;

    public UtilisateurDao getDao() {
        return dao;
    }

    public void setDao(UtilisateurDao dao) {
        this.dao = dao;
    }

    public CompteDao getDaoCompte() {
        return daoCompte;
    }

    public void setDaoCompte(CompteDao daoCompte) {
        this.daoCompte = daoCompte;
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
	    daoCompte.save(compte);
	    utilisateur.addAccount(compte);
	    dao.save(utilisateur);
	    return true;
	}
	return false;
    }

    @Override
    public boolean inscriptionPro(String identifiant, String motDePasse, String nomEntreprise, long siret) {
        if(dao.find(identifiant) == null){
            if (entrepriseDao.find(siret) == null){
                UtilisateurProEntity utilisateur = new UtilisateurProEntity(identifiant, motDePasse);
                EntrepriseEntity entreprise = new EntrepriseEntity(siret,nomEntreprise,utilisateur);
		CompteEntity compte = new CompteEntity("Compte pro", utilisateur);
		dao.save(utilisateur);
		daoCompte.save(compte);
                entrepriseDao.save(entreprise);
		utilisateur.addAccount(compte);
                utilisateur.setEntreprise(entreprise);
                dao.save(utilisateur);
            }
	    return true;
	}
	return false;
    }

    public void updateUser(String id, String password, String nom, String prenom) {
        UtilisateurEntity user = dao.find(id);
        if (user != null) {
            user.setMotDePasse(password);
            user.setNom(nom);
            user.setPrenom(prenom);
            dao.save(user);
        }
    }
    
    public void updateProUser(String id, String password, String nom, String prenom, String entreprise) {
        UtilisateurProEntity user = new UtilisateurProDaoImpl().find(id);
        if (user != null){
            user.setMotDePasse(password);
            user.setNom(nom);
            user.setPrenom(prenom);
            user.getEntreprise().setNom(entreprise);
            dao.save(user);
            new EntrepriseDaoImpl().save(user.getEntreprise());
        }
    }
    
}
