package controllers;

import bean.Utilisateur;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
public class UtilisateurController {
    
    public UtilisateurController() {
    }
    
    //---------------------------
    @RequestMapping(value="connexion", method = RequestMethod.GET)
    protected String initConnexion(HttpServletRequest request,HttpServletResponse response) throws Exception {
        return ControllerUtils.utilisateurConnecte(request) ? "erreur" : "connexion";
    }
    
    @RequestMapping(value="connexion", method = RequestMethod.POST)
    protected ModelAndView connexion(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("index"); 
        
        HttpSession session = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        
        String identifiant = request.getParameter("username");
        String mdp = request.getParameter("password");
        
        // Si la session n'est pas déjà crée
        if (session==null){
            session = request.getSession(true); // on la crée
        }
        
        session.setAttribute("utilisateur", new Utilisateur(identifiant,mdp));
        session.setAttribute("connexion", new Boolean(true));
        
        return mv;
    }

    //---------------------------
    @RequestMapping(value="deconnexion", method = RequestMethod.GET)
    protected String initDeconnexion(HttpServletRequest request,HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);

        // Si l'utilisateur n'est pas déjà connecté
        System.err.println(ControllerUtils.utilisateurConnecte(request));
        System.err.println(session.getAttribute("connexion"));
        if (!ControllerUtils.utilisateurConnecte(request)){
            return "erreur"; // on renvoie la page d'erreur
        }
        
        session.setAttribute("utilisateur", null);
        session.setAttribute("connexion", new Boolean(false));
        return "index";
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
