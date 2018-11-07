package dao.compte;

import java.util.List;

/**
 * Classe utilisé pour accéder aux comptes et les gérer dans la base de données
 */
public interface CompteDao {
    /**
     * Créer un compte dans la base
     * @param ce Le compte à créer dans la base
     */
    void save(CompteEntity ce);
    
    /**
     * Trouver un compte selon son id
     * @param id l'id du compte a trouver
     * @return Le compte associé à l'id
     */
    CompteEntity find(Long id);
    
    /**
     * Met à jour un compte
     * @param ce Le compte a mettre à jour
     */
    void update(CompteEntity ce);
    
    /**
     * Supprime un compte de la base
     * @param ce Le compte a supprimer
     */
    void remove(CompteEntity ce);

    /**
     * Recupère tout les comptes de la base
     * @return Une liste des comptes de la base
     */
    List<CompteEntity> findAll();
    
    /**
     * Récupère tout les comptes ouverts de la base
     * @return Une liste des comptes ouverts de la base
     */
    List<CompteEntity> findAllOpenAccounts();
}
