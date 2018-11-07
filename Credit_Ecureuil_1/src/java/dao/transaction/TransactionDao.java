package dao.transaction;

/**
 * Gère les transactions dans la base
 */

public interface TransactionDao{
    /**
     * Sauvegarde une transaction dans la base
     * @param ue La transaction a sauvegarder
     */
    public void save(TransactionEntity ue);
    
    /**
     * Trouve une transaction en fonction de son identifiant
     * @param identifiant L'identifiant de la transaction à trouver
     * @return La transaction correspondant à la transaction
     */
    public TransactionEntity find(String identifiant);
    
    /**
     * Met à jour une transaction
     * @param ue La transaction a mettre à jour
     */
    public void update(TransactionEntity ue);
    
    /**
     * Supprime une transaction
     * @param ue La transaction à supprimer
     */
    public void remove(TransactionEntity ue);
}
