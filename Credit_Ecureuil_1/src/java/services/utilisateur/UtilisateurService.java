package services.utilisateur;

import dao.utilisateur.UtilisateurEntity;
import exceptions.ServiceException;

/**
 *
 * @author rcharpen
 */
public interface UtilisateurService {
    /**
     * Crée un utilisateur dans la base
     * @param identifiant email
     * @param motDePasse
     * @throws ServiceException 
     */
    void inscription(String identifiant, String motDePasse) throws ServiceException;
    
    /**
     * Crée un utilisateur pro dans la base
     * @param identifiant email
     * @param motDePasse
     * @param nomEntreprise nom de l'entreprise
     * @param siret siret de l'entreprise
     * @throws ServiceException 
     */
    void inscriptionPro(String identifiant, String motDePasse, String nomEntreprise, long siret) throws ServiceException;
    
    /**
     * Vérifie que l'utilisateur existe et que le mot de passe est correcte sinon génère une exception
     * @param identifiant email
     * @param motDePasse
     * @throws ServiceException 
     */
    void connexion(String identifiant, String motDePasse) throws ServiceException;
    
    /**
     * Renvoie un utilisateur en fonction de son identifiant (email)
     * @param identifiant email
     * @return un utilisateur en fonction de son identifiant (email)
     */
    UtilisateurEntity getUtilisateur(String identifiant);
    
    /**
     * Met à jour les champs d'un utilisateur
     * @param id email
     * @param password mot de passe
     * @param nom
     * @param prenom 
     */
    void updateUser(String id, String password, String nom, String prenom);
    
    /**
     * Met à jour les champs d'un utilisateur pro
     * @param id email
     * @param password mot de passe
     * @param nom
     * @param prenom
     * @param entreprise nom de l'entreprise
     */
    void updateProUser(String id, String password, String nom, String prenom, String entreprise);
}
