package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author rcharpen
 */
@Controller
public class UtilisateurController {
    
    public UtilisateurController() {
    }
    
    //---------------------------
    @RequestMapping(value="connexion", method = RequestMethod.GET)
    protected String initConnexion(HttpServletRequest request,HttpServletResponse response) throws Exception {
       return "connexion";
    }
    
    @RequestMapping(value="connexion", method = RequestMethod.POST)
    protected ModelAndView connexion(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //---------------------------
    @RequestMapping(value="deconnexion", method = RequestMethod.GET)
    protected String initDeconnexion(HttpServletRequest request,HttpServletResponse response) throws Exception {
       return "deconnexion";
    }

    @RequestMapping(value="deconnexion", method = RequestMethod.POST)
    protected ModelAndView deconnexion(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }    

    //---------------------------
    @RequestMapping(value="inscription", method = RequestMethod.GET)
    protected String initInscription(HttpServletRequest request,HttpServletResponse response) throws Exception {
       return "inscription";
    }

    @RequestMapping(value="inscription", method = RequestMethod.POST)
    protected ModelAndView inscription(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //---------------------------
    @RequestMapping(value="profil", method = RequestMethod.GET)
    protected String initProfil(HttpServletRequest request,HttpServletResponse response) throws Exception {
       return "profil";
    }

    @RequestMapping(value="profil", method = RequestMethod.POST)
    protected ModelAndView profilUtilisateur(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
