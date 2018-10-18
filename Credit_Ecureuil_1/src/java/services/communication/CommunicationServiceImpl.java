package services.communication;

import dao.message.MessageDao;
import dao.message.MessageDaoImpl;
import dao.message.MessageEntity;
import dao.utilisateur.UtilisateurDaoImpl;
import dao.utilisateur.UtilisateurEntity;
import services.utilisateur.UtilisateurServiceImpl;

/**
 *
 * @author rcharpen
 */
public class CommunicationServiceImpl implements CommunicationService {
    
    private MessageDao msgDao = new MessageDaoImpl();

    @Override
    public void lireMessages(String login) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void envoyerMessage(String from, String to, String sujet, String message) {
        UtilisateurEntity userFrom = new UtilisateurServiceImpl().getUtilisateur(from);
        UtilisateurEntity userTo = new UtilisateurServiceImpl().getUtilisateur(to);
        MessageEntity msg = new MessageEntity(userFrom, userTo, sujet, message);
        userFrom.addMessageEnvoye(msg);
        userTo.addMessageRecu(msg);
        new UtilisateurDaoImpl().save(userFrom);
        new UtilisateurDaoImpl().save(userTo);
        
    }

    @Override
    public void lireNotifications() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
