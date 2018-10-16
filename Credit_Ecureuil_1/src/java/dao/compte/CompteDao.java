package dao.compte;

import java.util.List;

/**
 *
 * @author rcharpen
 */
public interface CompteDao {
    public void createNewAccount(CompteEntity ce);
    
    public boolean deleteAccount(CompteEntity acc);
    
    public CompteEntity find(Long id);
    
    public List<CompteEntity> retrieveAllAccounts();
}
