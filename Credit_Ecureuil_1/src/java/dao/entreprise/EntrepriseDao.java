package dao.entreprise;

import dao.utilisateur.UtilisateurEntity;

/**
 *
 * @author romain
 */
public interface EntrepriseDao {
    public void save(EntrepriseEntity ee);
    
    public EntrepriseEntity find(Long siret);
    
    public void update(EntrepriseEntity ee);
    
    public void remove(EntrepriseEntity ee);
}
