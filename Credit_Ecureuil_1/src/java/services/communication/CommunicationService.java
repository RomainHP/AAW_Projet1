package services.communication;

import dao.message.MessageEntity;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author rcharpen
 */
public interface CommunicationService {
    /**
     * Renvoie la liste des messages reçus de l'utilisateur correspondant au login
     * @param login email
     * @return la liste des messages reçus de l'utilisateur correspondant au login
     */
    List<MessageEntity> lireMessages(String login);
    
    /**
     * Envoie un message d'un utilisateur vers un autre
     * @param from email de l'expediteur
     * @param to email du destinataire
     * @param sujet sujet du message
     * @param message contenu du message
     * @throws ServiceException 
     */
    void envoyerMessage(String from, String to, String sujet, String message) throws ServiceException;

    /**
     * Supprime un messsage en fonction de l'id
     * @param id id du message
     * @throws ServiceException 
     */
    void supprimerMessage(long id) throws ServiceException;
    
    /**
     * Pas encore implémenté
     */
    void lireNotifications();
}
