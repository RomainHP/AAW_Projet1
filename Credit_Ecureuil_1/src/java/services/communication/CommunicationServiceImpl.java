package services.communication;

import dao.message.MessageDao;
import dao.message.MessageEntity;
import dao.utilisateur.UtilisateurDao;
import dao.utilisateur.UtilisateurEntity;
import exceptions.ServiceException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author rcharpen
 */
@Service
public class CommunicationServiceImpl implements CommunicationService {
    
    @Resource
    MessageDao msgDao;
    
    @Resource
    UtilisateurDao userDao;

    @Override
    public List<MessageEntity> lireMessages(String login) {
        List<MessageEntity> res = new ArrayList<>();
        UtilisateurEntity user = userDao.find(login);
        if (user!=null) res.addAll(user.getMessagesRecus());
        return res;
    }

    @Override
    public void envoyerMessage(String from, String to, String sujet, String message) throws ServiceException {
        UtilisateurEntity userFrom = userDao.find(from);
        UtilisateurEntity userTo = userDao.find(to);
        if (userFrom == null) throw new ServiceException("Expéditeur inexistant.");
        if (userTo == null) throw new ServiceException("Destinataire inexistant.");
        MessageEntity msg = new MessageEntity(userFrom, userTo, sujet, message);
        userTo.addMessageRecu(msg);
        userDao.update(userTo);
    }

    @Override
    public void lireNotifications() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimerMessage(long id) throws ServiceException {
        MessageEntity msg = msgDao.find(id);
        if (msg==null) throw new ServiceException("Message inexistant.");
        msg.getUserTo().removeMessageRecu(msg);
        userDao.update(msg.getUserTo());
        msgDao.remove(msg);
    }
    
}
