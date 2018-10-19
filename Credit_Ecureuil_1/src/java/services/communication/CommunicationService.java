package services.communication;

import dao.message.MessageEntity;
import java.util.List;

/**
 *
 * @author rcharpen
 */
public interface CommunicationService {
    List<MessageEntity> lireMessages(String login);
    void envoyerMessage(String from, String to, String sujet, String message);
    void lireNotifications();
}
