package services.utilisateur;

import dao.utilisateur.UtilisateurEntity;

/**
 *
 * @author rcharpen
 */
public interface UtilisateurService {
    boolean inscription(String identifiant, String motDePasse);
    boolean connexion(String identifiant, String motDePasse);
    void deconnexion();
    void profil(UtilisateurEntity ue);
}
