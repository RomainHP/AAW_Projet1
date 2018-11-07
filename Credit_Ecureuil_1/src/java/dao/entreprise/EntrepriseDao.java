package dao.entreprise;

/**
 * Permet de gérer les entreprises de la base
 */
public interface EntrepriseDao {
    /**
     * Créer une entreprise dans la base
     * @param ee L'entreprise à rajouter dans la base
     */
    public void save(EntrepriseEntity ee);
    
    /**
     * Retrouve une entreprise en fonction d'un numéro de siret
     * @param siret le siret de l'entreprise à récuperer
     * @return EntrepriseEntity correspondant au numéro de siret
     */
    public EntrepriseEntity find(Long siret);
    
    /**
     * Met à jour une entreprise
     * @param ee L'entreprise à mettre à jour
     */
    public void update(EntrepriseEntity ee);
    
    /**
     * Supprime une entreprise
     * @param ee l'entreprise à supprimer
     */
    public void remove(EntrepriseEntity ee);
}
