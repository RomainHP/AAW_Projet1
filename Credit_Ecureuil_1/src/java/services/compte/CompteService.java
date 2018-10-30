package services.compte;

import dao.compte.CompteEntity;
import exceptions.ServiceException;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 * @author rcharpen
 */
public interface CompteService {
    /**
     * Effectue le virement entre le compte src et le compte dest
     * @param src id du compte source
     * @param dest id du compte destinataire
     * @param montant montant de la transaction
     * @throws ServiceException 
     */
    void virement(Long src, Long dest, Double montant) throws ServiceException;
    
    /**
     * Renvoie la liste des comptes associés au login
     * @param login email de l'utilisateur
     * @return la liste des comptes associés au login
     */
    List<CompteEntity> consultation(String login);
    
    /**
     * Crée un livret et l'ajoute à l'utilisateur
     * @param nomCompte nom du livret
     * @param nomUtilisateur email de l'utilisateur
     * @throws ServiceException 
     */
    void ajoutLivret(String nomCompte, String nomUtilisateur) throws ServiceException;
    
    /**
     * Supprime le livret correspondant à l'id
     * @param id id du livret à supprimer
     * @throws ServiceException 
     */
    void supprimerLivret(Long id) throws ServiceException;
    
    /**
     * Crée un compte joint
     * @param nomCompte nom du compte
     * @param nomUtilisateur email du propriétaire
     * @param co_proprietaires liste des emails des co-propriétaires
     * @throws ServiceException 
     */
    void ajoutCompteJoint(String nomCompte, String nomUtilisateur, LinkedHashSet<String> co_proprietaires) throws ServiceException;
    
    /**
     * Supprime le compte joint correspondant à l'id
     * @param id id du compte à supprimer
     * @throws ServiceException 
     */
    void supprimerCompteJoint(Long id) throws ServiceException;
    
    /**
     * Renvoie le compte associé à l'id
     * @param id id du compte à renvoyer
     * @return le compte associé à l'id
     */
    CompteEntity getAccount(Long id);
}
