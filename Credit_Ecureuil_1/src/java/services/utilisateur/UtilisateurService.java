package services.utilisateur;

import dao.utilisateur.UtilisateurEntity;
import exceptions.ServiceException;

/**
 *
 * @author rcharpen
 */
public interface UtilisateurService {
    void inscription(String identifiant, String motDePasse) throws ServiceException;
    void inscriptionPro(String identifiant, String motDePasse, String nomEntreprise, long siret) throws ServiceException;
    void connexion(String identifiant, String motDePasse) throws ServiceException;
    UtilisateurEntity getUtilisateur(String identifiant);
    void updateUser(String id, String password, String nom, String prenom);
    void updateProUser(String id, String password, String nom, String prenom, String entreprise);
}
