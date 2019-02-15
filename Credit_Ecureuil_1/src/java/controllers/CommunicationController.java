package controllers;

import dao.message.MessageEntity;
import dao.utilisateur.UtilisateurEntity;
import exceptions.ServiceException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import services.communication.CommunicationService;
import utils.ControllerUtils;

/**
 * Le controller utilisé par le système de communication
 */
@Controller
public class CommunicationController {

    @Autowired
    CommunicationService service;

    @Autowired
    UtilisateurController utilisateurController;

    //----------------------------
    /**
     * Affichage de la page "consulter_messagerie" en methode GET
     *
     * @return ResponseEntity correspondant a la page "consulter_messagerie"
     */
    @RequestMapping(value = "consulter_messagerie", method = RequestMethod.GET)
    protected ResponseEntity initConsulterMessagerie(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject json = ControllerUtils.requestToJSONObj(request);
            String user = json.getString("user");
            
            List<MessageEntity> allMessages = service.lireMessages(user);
            JSONObject jObj = new JSONObject();
            for (MessageEntity msg : allMessages) {
                jObj.append("id", msg.getId());
                jObj.append("emetteur", msg.getUserFrom().toString());
                jObj.append("sujet", msg.getSujet());
                jObj.append("message", msg.getHtmlMessage());
            }
            status = HttpStatus.OK;
            userResponse = jObj.toString();
        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        
        return new ResponseEntity(userResponse, status);
    }

    //----------------------------
    /**
     * Affichage de la page "envoyer_message" en methode GET
     */
    @RequestMapping(value = "envoyer_message", method = RequestMethod.GET)
    protected ResponseEntity initEnvoyerMessage(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            List<UtilisateurEntity> allUsers = utilisateurController.service.getAllUsers();
            JSONObject jObj = new JSONObject();
            for (UtilisateurEntity user : allUsers) {
                jObj.append("mail", user.getEmail());
            }
            status = HttpStatus.OK;
            userResponse = jObj.toString();
        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        
        return new ResponseEntity(userResponse, status);
    }

    /**
     * Affichage de la page "envoyer_message" en methode POST
     *
     * @return ResponseEntity correspondant a la page "envoyer_message" avec
     * indication de réussite ou non
     */
    @RequestMapping(value = "envoyer_message", method = RequestMethod.POST)
    public ResponseEntity<?> serviceEnvoyerMessage(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception, IOException {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);
            String sujet = jObj.getString("sujet");
            String from = jObj.getString("from");
            String to = jObj.getString("to");
            String message = "";
            try {
                message = jObj.getString("message");
            } catch (JSONException ex) {
                
            }

            // Envoi du message
            try {
                service.envoyerMessage(from, to, sujet, message);
                status = HttpStatus.OK;
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
            }
        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //----------------------
    /**
     * Affichage de la page "supprimer_message" en methode POST
     *
     * @return ResponseEntity correspondant a la page "supprimer_message" avec
     * indication de réussite ou non
     */
    @RequestMapping(value = "supprimer_message", method = RequestMethod.POST)
    protected ResponseEntity<?> supprimerMessage(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);

            String idCompteStr = jObj.getString("id");

            // Suppression du message
            try {
                Long idCompte = Long.parseLong(idCompteStr);
                service.supprimerMessage(idCompte);
                status = HttpStatus.OK;
            } catch (NumberFormatException e) {
                userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
            }

        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //-----------------------------
    @RequestMapping(value = "notifications", method = RequestMethod.POST)
    public ResponseEntity notifications(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
