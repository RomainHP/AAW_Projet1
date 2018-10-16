package services.compte;

import dao.compte.CompteEntity;
import dao.utilisateur.UtilisateurEntity;
import java.util.List;

/**
 *
 * @author rcharpen
 */
public interface CompteService {
    boolean virement(Long src, Long dest, Long montant);
    List<CompteEntity> consultation(String login);
    List<CompteEntity> retrieveAccounts();
}
