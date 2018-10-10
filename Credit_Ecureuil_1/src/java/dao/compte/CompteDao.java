package dao.compte;

import dao.utilisateur.UtilisateurEntity;
import java.util.List;

/**
 *
 * @author rcharpen
 */
public interface CompteDao {
    public List<CompteEntity> getAccounts(String login);
    
    public void createNewAccount(CompteEntity ce);
    
    public boolean deleteAccount(CompteEntity acc);
}
