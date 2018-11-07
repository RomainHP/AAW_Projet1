package dao.utilisateur;

import java.util.List;

/**
 * Gère les utilisateur de la base
 * @author etienne
 */

public interface UtilisateurDao {
    /**
     * Sauvegarde un utilisateur dans la base
     * @param ue L'utilisateur à sauvegarder
     */
    public void save(UtilisateurEntity ue);
    
    /**
     * Trouve un utilisateur en fonction de son identifiant
     * @param identifiant l'id de l'utilisateur à trouver
     * @return l'utilisateur associé à l'id
     */
    public UtilisateurEntity find(String identifiant);
    
    /**
     * Met à jour un Utilisateur
     * @param ue l'utilisateur à mettre à jour
     */
    public void update(UtilisateurEntity ue);
    
    /**
     * Supprime un utilisateur
     * @param ue L'utilisateur à supprimer
     */
    public void remove(UtilisateurEntity ue);
    
    /**
     * Récupère le mot de passe d'un utilisateur
     * @param identifiant l'id de l'utilsateur
     * @return Une string correspondant au mdp de l'utilisateur
     */
    public String getUserMdp(String identifiant);
    
    /**
     * Retourne tout les utilisateur de la base
     * @return une liste contenant tout les utilisateurs de la base
     */
    public List<UtilisateurEntity> findAll();
}
