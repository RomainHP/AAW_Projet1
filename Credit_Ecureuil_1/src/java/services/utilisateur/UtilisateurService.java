package services.utilisateur;

import dao.utilisateur.UtilisateurEntity;

/**
 *
 * @author rcharpen
 */
public interface UtilisateurService {
    boolean inscription(String identifiant, String motDePasse);
    boolean inscriptionPro(String identifiant, String motDePasse, String entreprise, long siret);
    boolean connexion(String identifiant, String motDePasse);
    UtilisateurEntity getUtilisateur(String identifiant);
}
