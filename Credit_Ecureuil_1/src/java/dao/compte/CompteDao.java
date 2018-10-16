package dao.compte;

import dao.utilisateur.UtilisateurEntity;
import java.util.List;

/**
 *
 * @author rcharpen
 */
public interface CompteDao {
    public void save(CompteEntity ce);
    
    public CompteEntity find(Long id);
    
    public void update(CompteEntity ce);
    
    public void remove(CompteEntity ce);
}
