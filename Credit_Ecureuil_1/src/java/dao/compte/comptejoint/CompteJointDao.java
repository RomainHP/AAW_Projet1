package dao.compte.comptejoint;

/**
 * Permet d'accéder aux comptes joints dans la base
 */
public interface CompteJointDao {
    /**
     * Sauvegarde un compte joint dans la base
     * @param ce Le compte joint a créer
     */
    public void save(CompteJointEntity ce);
    
    /**
     * Récupère un compte joint donné
     * @param id l'id du compte joint à récupérer
     * @return Le compte joint trouvé
     */
    public CompteJointEntity find(Long id);
    
    /**
     * Met à jour un compte
     * @param ce Le compte à mettre un jour
     */
    public void update(CompteJointEntity ce);
    
    /**
     * Supprime un compte
     * @param ce Le compte à supprimer
     */
    public void remove(CompteJointEntity ce);
}
