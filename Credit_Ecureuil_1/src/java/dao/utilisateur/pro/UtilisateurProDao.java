package dao.utilisateur.pro;

/**
 * Gère les utilisateurs pro de la base
 * @author rcharpen
 */
public interface UtilisateurProDao {
    /**
     * Sauvegarde un utilisateur pro dans la base
     * @param ue L'utilisateur pro a sauvegarder
     */
    public void save(UtilisateurProEntity ue);
    
    /**
     * Trouve un Utilisateur pro en fonction de son id
     * @param identifiant l'id de l'utilisateur à trouver
     * @return Une entity d'utilisateur pro
     */
    public UtilisateurProEntity find(String identifiant);
    
    /**
     * Met à jour un utilisateur pro
     * @param ue l'utilisateur pro à mettre à jour
     */
    public void update(UtilisateurProEntity ue);
    
    /**
     * Supprime un utilisateur pro
     * @param ue L'utilisateur pro à supprimer
     */
    public void remove(UtilisateurProEntity ue);
}
