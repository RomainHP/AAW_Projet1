package dao.compte.livret;

/**
 * Permet d'accèder aux livrets de la base de données
 * @author rcharpen
 */
public interface LivretDao {
    /**
     * Sauvegarde un livret dans la base
     * @param ce le livret à sauvegarder
     */
    public void save(LivretEntity ce);
    
    /**
     * Récupère un livret en fonction de son id
     * @param id l'id du livret à retrouver
     * @return un LivretEntity correspondant à l'id
     */
    public LivretEntity find(Long id);
    
    /**
     * Met à jour un livret
     * @param ce Le livret à mettre à jour
     */
    public void update(LivretEntity ce);
    
    /**
     * Supprime un livret
     * @param ce Le livret à supprimer
     */
    public void remove(LivretEntity ce);
}
