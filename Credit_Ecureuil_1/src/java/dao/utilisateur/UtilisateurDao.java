package dao.utilisateur;

/**
 *
 * @author rcharpen
 */
public interface UtilisateurDao {
    public void save(UtilisateurEntity ue);
    
    public UtilisateurEntity find(UtilisateurEntity id);
    
    public void update(UtilisateurEntity oldUE, UtilisateurEntity newUE);
    
    public void remove(UtilisateurEntity ue);
}
