package services.compte;

import dao.compte.CompteEntity;
import java.util.List;

/**
 *
 * @author rcharpen
 */
public interface CompteService {
    boolean virement(Long src, Long dest, Double montant);
    List<CompteEntity> consultation(String login);
    public boolean creeCompte(String nomCompte, String nomUtilisateur);
    public boolean supprCompte(Long id);
    public CompteEntity getAcc(Long id);
}
