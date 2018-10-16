package dao.utilisateur;

/**
 *
 * @author rcharpen
 */
public interface UtilisateurDao {
    public void save(UtilisateurEntity ue);
    
    public UtilisateurEntity find(String identifiant);
    
    public void update(UtilisateurEntity ue);
    
    public void remove(UtilisateurEntity ue);
    
    public String getUserMdp(String identifiant);
}
