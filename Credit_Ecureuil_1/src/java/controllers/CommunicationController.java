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
import org.springframework.web.servlet.ModelAndView;
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
    
    @RequestMapping(value="index", method = RequestMethod.GET)
    protected String initIndex(HttpServletRequest request,HttpServletResponse response) {
       return "index";
    }
    
    //----------------------------
    /**
     * Affichage de la page "consulter_messagerie" en methode GET
     * @return ModelAndView correspondant a la page "consulter_messagerie"
     */
    @RequestMapping(value="consulter_messagerie", method = RequestMethod.GET)
    protected ModelAndView initConsulterMessagerie(HttpServletRequest request,HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return new ModelAndView("erreur");
        
        ModelAndView mv = new ModelAndView("consulter_messagerie"); 

        StringBuffer table_messages = new StringBuffer();

        List<MessageEntity> list = service.lireMessages(ControllerUtils.getUserLogin(request));
        
        // Affiche chaque message de la liste
        int cpt = 1;
	for (MessageEntity message : list) {
	    table_messages.append("<tr data-toggle=\"collapse\" data-target=\"#msg" + cpt + "\" class=\"accordion-toggle\">");
	    table_messages.append("<td scope=\"row\">"+cpt+"</td>");
            // Affichage ou non du prénom et nom (si renseigné)
            table_messages.append("<td scope=\"row\">"+message.getUserFrom()+"</td>");
	    table_messages.append("<td scope=\"row\">"+message.getSujet()+"</td>");
            // Boutton Supprimer
            table_messages.append("<td scope=\"row\"><form class=\"form\" action=\"supprimer_message.htm\" method=\"post\"><div class=\"form-group mb-3\">");
            table_messages.append("<input type=\"hidden\" class=\"form-control\" name=\"id\" value=\""+message.getId()+"\"><button type=\"submit\" class=\"btn btn-primary btn-md\">Supprimer</button></div></form></td>");
	    table_messages.append("</tr>");
            // Ligne d'affichage du message (caché par défaut)
	    table_messages.append("<tr>");
            table_messages.append("<td colspan=\"4\" class=\"hiddenRow\"><div class=\"accordian-body collapse\" id=\"msg" + cpt + "\">" + message.getHtmlMessage() + "</div> </td>");
	    table_messages.append("</tr>");
	    cpt++;
	}

        mv.addObject("messages", table_messages);
        
        return mv;
    }
    
    //----------------------------
    /**
     * Affichage de la page "envoyer_message" en methode GET
     * @return ModelAndView correspondant a la page "envoyer_message"
     */
    @RequestMapping(value="envoyer_message", method = RequestMethod.GET)
    protected ModelAndView initEnvoyerMessage(HttpServletRequest request,HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return new ModelAndView("erreur");
        ModelAndView mv = new ModelAndView("envoyer_message");
        // Compte destinataire
        StringBuilder options_dest = new StringBuilder();
	List<UtilisateurEntity> all_users = utilisateurController.service.getAllUsers();
        for (UtilisateurEntity utilisateur : all_users){
            System.err.println(utilisateur);
            options_dest.append("<option value=\"");
            options_dest.append(utilisateur.getEmail());
            options_dest.append("\">");
            options_dest.append(utilisateur);
            options_dest.append("</option>");
        }
        
        mv.addObject("options_dest", options_dest.toString());
        return mv;
    }    
    
    /**
     * Affichage de la page "envoyer_message" en methode POST
     * @return ModelAndView correspondant a la page "envoyer_message" avec indication de réussite ou non
     */
    @RequestMapping(value="envoyer_message", method = RequestMethod.POST)
    public ResponseEntity<?> serviceEnvoyerMessage(
            HttpServletRequest request,
            HttpServletResponse response) throws JSONException, IOException {
        JSONObject jObj = ControllerUtils.requestToJSONObj(request);
        String userResponse = "[]";
	HttpStatus status = HttpStatus.BAD_REQUEST;
        
        String sujet = jObj.getString("sujet");
        String destinataire = jObj.getString("destinataire");
        String message = jObj.getString("message");
        
        // Envoi du message
        try {
            service.envoyerMessage(ControllerUtils.getUserLogin(request), destinataire, sujet, message);
            status = HttpStatus.OK;
        } catch (ServiceException e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }
    
     //----------------------
    
    /**
     * Affichage de la page "supprimer_message" en methode POST
     * @return ModelAndView correspondant a la page "supprimer_message" avec indication de réussite ou non
     */
    @RequestMapping(value="supprimer_message", method = RequestMethod.POST)
    protected ResponseEntity<?> supprimerMessage(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONObject jObj = ControllerUtils.requestToJSONObj(request);
        String userResponse = "[]";
	HttpStatus status = HttpStatus.BAD_REQUEST;
	
	String idCompteStr = jObj.getString("id");
        
        // Suppression du message
        try {
            Long idCompte = Long.parseLong(idCompteStr);
            service.supprimerMessage(idCompte);
            status = HttpStatus.OK;
        } catch (NumberFormatException e){
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        } catch (ServiceException e){
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
	
        return new ResponseEntity(userResponse, status);
    }
    
    //-----------------------------
    @RequestMapping(value="notifications", method = RequestMethod.GET)
    protected String initNotif(HttpServletRequest request,HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return "erreur";
        return "notifications";
    }    
    
    @RequestMapping(value="notifications", method = RequestMethod.POST)
    public ModelAndView notifications(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }    
}
