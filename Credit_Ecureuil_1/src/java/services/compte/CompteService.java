package services.compte;

import dao.compte.CompteEntity;
import dao.utilisateur.UtilisateurEntity;
import java.util.List;

/**
 *
 * @author rcharpen
 */
public interface CompteService {
    void virement();
    List<CompteEntity> consultation(String login);
}
