package services.communication;

/**
 *
 * @author rcharpen
 */
public interface CommunicationService {
    void lireMessages(String login);
    void envoyerMessage(String from, String to, String sujet, String message);
    void lireNotifications();
}
