package services.compte;

import dao.compte.CompteEntity;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author rcharpen
 */
public interface CompteService {
    void virement(Long src, Long dest, Double montant) throws ServiceException;
    List<CompteEntity> consultation(String login);
    void ajoutLivret(String nomCompte, String nomUtilisateur) throws ServiceException;
    void supprimerLivret(Long id) throws ServiceException;
    void ajoutCompteJoint(String nomCompte, String nomUtilisateur, List<String> co_proprietaires) throws ServiceException;
    void supprimerCompteJoint(Long id) throws ServiceException;
    CompteEntity getAccount(Long id);
}
