package controllers;

import dao.compte.CompteEntity;
import dao.message.MessageEntity;
import exceptions.ServiceException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.communication.CommunicationService;
import utils.ControllerUtils;

/**
 *
 * @author rcharpen
 */
@Controller
public class CommunicationController {
    
    @Autowired
    CommunicationService service;
    
    //----------------------------
    
    @RequestMapping(value="index", method = RequestMethod.GET)
    protected String initIndex(HttpServletRequest request,HttpServletResponse response) {
       return "index";
    }
    
    //----------------------------
    
    @RequestMapping(value="consulter_messagerie", method = RequestMethod.GET)
    protected ModelAndView initConsulterMessagerie(HttpServletRequest request,HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return new ModelAndView("erreur");
        
        ModelAndView mv = new ModelAndView("consulter_messagerie"); 

        StringBuffer table_messages = new StringBuffer();
	
	table_messages.append("<table class=\"table\">");
	table_messages.append("<thead style=\"background-color:#ffb860;\">");
	table_messages.append("<tr>");
        table_messages.append("<th scope=\"col\">#</th>");
        table_messages.append("<th scope=\"col\">Emetteur</th>");
	table_messages.append("<th scope=\"col\">Sujet</th>");
	table_messages.append("<th scope=\"col\">Message</th>");
	table_messages.append("</thead>");
        table_messages.append("<tbody>");

        List<MessageEntity> list = service.lireMessages(ControllerUtils.getUserLogin(request));
        
        int cpt = 1;
	for (MessageEntity message : list) {
	    table_messages.append("<tr>");
	    table_messages.append("<th scope=\"row\">"+cpt+"</th>");
	    table_messages.append("<th scope=\"row\">"+message.getUserFrom().getEmail()+"</th>");
	    table_messages.append("<th scope=\"row\">"+message.getSujet()+"</th>");
	    table_messages.append("<th scope=\"row\">"+message.getMessage()+"</th>");
	    table_messages.append("</tr>");
	    cpt++;
	}
        
        String precedent = "";
        String suivant = "";

        mv.addObject("messages", table_messages);

        return mv;
    }    
    
    @RequestMapping(value="consulter_messagerie", method = RequestMethod.POST)
    public ModelAndView serviceConsulterMessagerie(
            HttpServletRequest request,
            HttpServletResponse response) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    //----------------------------
    
    @RequestMapping(value="envoyer_message", method = RequestMethod.GET)
    protected String initEnvoyerMessage(HttpServletRequest request,HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return "erreur";
        return "envoyer_message";
    }    
    
    @RequestMapping(value="envoyer_message", method = RequestMethod.POST)
    public ModelAndView serviceEnvoyerMessage(
            HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("envoyer_message");
        
        String sujet = request.getParameter("sujet");
        String destinataire = request.getParameter("destinataire");
        String message = request.getParameter("message");
        
        try {
            service.envoyerMessage(ControllerUtils.getUserLogin(request), destinataire, sujet, message);
            mv.addObject("returnMessage", ControllerUtils.generateSuccessMessage("Message envoyé."));
        } catch (ServiceException e) {
            mv.addObject("returnMessage", ControllerUtils.generateErrorMessage(e.getMessage()));
        }
        return mv;
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
