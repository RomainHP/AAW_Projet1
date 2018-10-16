package dao.entreprise;

import dao.utilisateur.UtilisateurEntity;

/**
 *
 * @author romain
 */
public interface EntrepriseDao {
    public void save(EntrepriseEntity ue);
    
    public EntrepriseEntity find(Long siret);
    
    public void update(EntrepriseEntity oldEE, EntrepriseEntity newEE);
    
    public void remove(EntrepriseEntity ue);
}
