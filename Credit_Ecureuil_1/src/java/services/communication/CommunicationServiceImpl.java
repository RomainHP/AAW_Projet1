package services.communication;

import dao.message.MessageDao;
import dao.message.MessageDaoImpl;
import dao.message.MessageEntity;
import dao.utilisateur.UtilisateurDaoImpl;
import dao.utilisateur.UtilisateurEntity;
import java.util.ArrayList;
import java.util.List;
import services.utilisateur.UtilisateurServiceImpl;

/**
 *
 * @author rcharpen
 */
public class CommunicationServiceImpl implements CommunicationService {
    
    private MessageDao msgDao = new MessageDaoImpl();

    @Override
    public List<MessageEntity> lireMessages(String login) {
        List<MessageEntity> res = new ArrayList<>();
        UtilisateurEntity user = new UtilisateurServiceImpl().getUtilisateur(login);
        if (user!=null) res.addAll(user.getMessagesRecus());
        return res;
    }

    @Override
    public void envoyerMessage(String from, String to, String sujet, String message) {
        UtilisateurEntity userFrom = new UtilisateurServiceImpl().getUtilisateur(from);
        UtilisateurEntity userTo = new UtilisateurServiceImpl().getUtilisateur(to);
        MessageEntity msg = new MessageEntity(userFrom, userTo, sujet, message);
        msgDao.save(msg);
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
