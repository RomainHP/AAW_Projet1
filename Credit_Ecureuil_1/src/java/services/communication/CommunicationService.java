package services.communication;

import dao.message.MessageEntity;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author rcharpen
 */
public interface CommunicationService {
    List<MessageEntity> lireMessages(String login);
    void envoyerMessage(String from, String to, String sujet, String message) throws ServiceException;
    void lireNotifications();

    void supprimerMessage(long id) throws ServiceException;
}
