package dao.utilisateur.pro;

/**
 *
 * @author rcharpen
 */
public interface UtilisateurProDao {
    public void save(UtilisateurProEntity ue);
    
    public UtilisateurProEntity find(String identifiant);
    
    public void update(UtilisateurProEntity ue);
    
    public void remove(UtilisateurProEntity ue);
}
