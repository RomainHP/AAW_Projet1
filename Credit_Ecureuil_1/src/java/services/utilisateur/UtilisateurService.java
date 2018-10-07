package services.utilisateur;

import dao.utilisateur.UtilisateurEntity;

/**
 *
 * @author rcharpen
 */
public interface UtilisateurService {
    boolean inscription(UtilisateurEntity ue);
    void connexion(UtilisateurEntity ue);
    void deconnexion();
    void profil(UtilisateurEntity ue);
}
