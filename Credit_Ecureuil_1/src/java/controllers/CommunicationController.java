package controllers;

import dao.compte.CompteEntity;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import utils.ControllerUtils;

/**
 *
 * @author rcharpen
 */
@Controller
public class CommunicationController {
    
    public CommunicationController() {
    }
    
    
    @RequestMapping(value="index", method = RequestMethod.GET)
    protected String initIndex(HttpServletRequest request,HttpServletResponse response) throws Exception {
       return "index";
    }
    
    //----------------------------
    
    @RequestMapping(value="consulter_messagerie", method = RequestMethod.GET)
    protected ModelAndView initConsulterMessagerie(HttpServletRequest request,HttpServletResponse response) throws Exception {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return new ModelAndView("erreur");
        
        ModelAndView mv = new ModelAndView("consulter_messagerie"); 

        response.setContentType("text/html;charset=UTF-8");

        StringBuffer options = new StringBuffer();

        //TODO remplacer par la base
//        List<MessageEntity> list = new ArrayList<MessageEntity>();
//        list.add(new MessageEntity(1l,"test"));
//        list.add(new MessageEntity(2l,"bidule"));
//
//        for (CompteEntity compte : list){
//            options.append("<option value=\"");
//            options.append(compte.getId());
//            options.append("\">");
//            options.append(compte.getNom());
//            options.append("</option>");
//        }
        
        String precedent = "";
        String suivant = "";

        mv.addObject("messages", options);

        return mv;
    }    
    
    @RequestMapping(value="consulter_messagerie", method = RequestMethod.POST)
    public ModelAndView serviceConsulterMessagerie(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    //----------------------------
    
    @RequestMapping(value="envoyer_message", method = RequestMethod.GET)
    protected String initEnvoyerMessage(HttpServletRequest request,HttpServletResponse response) throws Exception {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return "erreur";
        return "envoyer_message";
    }    
    
    @RequestMapping(value="envoyer_message", method = RequestMethod.POST)
    public ModelAndView serviceEnvoyerMessage(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    //-----------------------------
    @RequestMapping(value="notifications", method = RequestMethod.GET)
    protected String initNotif(HttpServletRequest request,HttpServletResponse response) throws Exception {
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
