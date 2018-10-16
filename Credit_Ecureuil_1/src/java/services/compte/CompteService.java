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
}
