package dao.message;

/**
 * Permet de gérer les messages de la base
 * @author Romain
 */
public interface MessageDao {
    /**
     * Créer un message dans la base
     * @param me Le message à sauvegarder
     */
    public void save(MessageEntity me);
    
    /**
     * Retrouve un message en fonction de son id
     * @param id l'id du message à retrouver
     * @return Le message associé
     */
    public MessageEntity find(Long id);
    
    /**
     * Met à jour un message
     * @param me Le message à mettre à jour
     */
    public void update(MessageEntity me);
    
    /**
     * Supprime un message
     * @param me le message à supprimer
     */
    public void remove(MessageEntity me);
}
