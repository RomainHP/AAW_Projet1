package dao.compte;

import java.util.List;

/**
 *
 * @author rcharpen
 */
public interface CompteDao {
    void save(CompteEntity ce);
    
    CompteEntity find(Long id);
    
    void update(CompteEntity ce);
    
    void remove(CompteEntity ce);

    List<CompteEntity> findAll();
    
    List<CompteEntity> findAllOpenAccounts();
}
