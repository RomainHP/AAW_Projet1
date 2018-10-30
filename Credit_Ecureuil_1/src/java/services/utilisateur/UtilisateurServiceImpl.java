package services.utilisateur;

import dao.entreprise.EntrepriseDao;
import dao.entreprise.EntrepriseEntity;
import dao.compte.CompteEntity;
import dao.utilisateur.UtilisateurDao;
import dao.utilisateur.UtilisateurDaoImpl;
import dao.utilisateur.UtilisateurEntity;
import dao.utilisateur.pro.UtilisateurProDao;
import dao.utilisateur.pro.UtilisateurProEntity;
import exceptions.ServiceException;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import dao.compte.CompteDao;
import dao.compte.CompteDaoImpl;

/**
 *
 * @author rcharpen
 */
@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    
    public final static double SOLDE_OUVERTURE_COMPTE = 100d;
    
    @Resource
    UtilisateurDao dao;
    
    @Resource
    UtilisateurProDao daoPro;
    
    @Resource
    CompteDao daoCompte;
    
    @Resource
    EntrepriseDao entrepriseDao;
    
    @Override
    public void connexion(String identifiant, String motDePasse) throws ServiceException {
	if(dao.find(identifiant) == null){
	    throw new ServiceException("Utilisateur inexistant.");
	}else{
            if(!dao.getUserMdp(identifiant).equals(motDePasse)){
                throw new ServiceException("Mot de passe incorrect.");
            }
        }
    }

    @Override
    public UtilisateurEntity getUtilisateur(String identifiant) {
        return dao.find(identifiant);
    }

    @Override
    public void inscription(String identifiant, String motDePasse) throws ServiceException {
	if(dao.find(identifiant) == null){
            UtilisateurEntity utilisateur = new UtilisateurEntity(identifiant, motDePasse);
            dao.save(utilisateur);
	    CompteEntity compte = new CompteEntity(utilisateur, UtilisateurServiceImpl.SOLDE_OUVERTURE_COMPTE);
            utilisateur.addAccount(compte);
            dao.update(utilisateur);
	} else {
            throw new ServiceException("Utilisateur déjà inscrit.");
        }
    }

    @Override
    public void inscriptionPro(String identifiant, String motDePasse, String nomEntreprise, long siret) throws ServiceException {
        if(daoPro.find(identifiant) == null){
            if (entrepriseDao.find(siret) == null){
                UtilisateurProEntity utilisateur = new UtilisateurProEntity(identifiant, motDePasse);
                dao.save(utilisateur);
                EntrepriseEntity entreprise = new EntrepriseEntity(siret,nomEntreprise,utilisateur);
                utilisateur.setEntreprise(entreprise);
		CompteEntity compte = new CompteEntity(utilisateur, UtilisateurServiceImpl.SOLDE_OUVERTURE_COMPTE);
                utilisateur.addAccount(compte);
                dao.update(utilisateur);
            } else {
                throw new ServiceException("Entreprise déjà enregistrée.");
            }
	} else {
            throw new ServiceException("Utilisateur déjà inscrit.");
        }
    }

    public void updateUser(String id, String password, String nom, String prenom) {
        UtilisateurEntity user = dao.find(id);
        if (user != null) {
            user.setMotDePasse(password);
            user.setNom(nom);
            user.setPrenom(prenom);
            dao.update(user);
        }
    }
    
    public void updateProUser(String id, String password, String nom, String prenom, String entreprise) {
        UtilisateurProEntity user = daoPro.find(id);
        if (user != null){
            user.setMotDePasse(password);
            user.setNom(nom);
            user.setPrenom(prenom);
            user.getEntreprise().setNom(entreprise);
            daoPro.update(user);
        }
    }
    
}
