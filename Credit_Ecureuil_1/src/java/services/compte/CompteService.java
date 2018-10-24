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
    CompteEntity getAccount(Long id);
}
