package controllers;

import dao.utilisateur.UtilisateurEntity;
import dao.utilisateur.pro.UtilisateurProEntity;
import exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.utilisateur.UtilisateurService;
import utils.ControllerUtils;

/**
 *
 * @author rcharpen
 */
@Controller
public class UtilisateurController {
        
    @Autowired
    UtilisateurService service;
    
    //---------------------------
    @RequestMapping(value="connexion", method = RequestMethod.GET)
    protected String initConnexion(HttpServletRequest request,HttpServletResponse response) {
        return ControllerUtils.isUtilisateurConnecte(request) ? "erreur" : "connexion";
    }
    
    @RequestMapping(value="connexion", method = RequestMethod.POST)
    protected ModelAndView connexion(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        
        String identifiant = request.getParameter("email");
        String mdp = request.getParameter("password");
        
        // Si la session n'est pas déjà crée
        try{
            service.connexion(identifiant, mdp);
            if (session==null){
                session = request.getSession(true); // on la crée
            }
            session.setAttribute("login", identifiant);
            session.setAttribute("pro", service.getUtilisateur(identifiant) instanceof UtilisateurProEntity);
            return new ModelAndView("index");
        } catch (ServiceException e){
            ModelAndView mv = new ModelAndView("connexion");
            mv.addObject("returnMessage", ControllerUtils.generateErrorMessage(e.getMessage()));
            return mv;
        }
    }

    //---------------------------
    @RequestMapping(value="deconnexion", method = RequestMethod.GET)
    protected String initDeconnexion(HttpServletRequest request,HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        // Si l'utilisateur n'est pas déjà connecté
        if (!ControllerUtils.isUtilisateurConnecte(request)){
            return "erreur"; // on renvoie la page d'erreur
        }
        
        session.invalidate();
        return "index";
    }

    //---------------------------
    @RequestMapping(value="inscription", method = RequestMethod.GET)
    protected String initInscription(HttpServletRequest request,HttpServletResponse response) {
       if (ControllerUtils.isUtilisateurConnecte(request)) return "erreur";
       return "inscription";
    }

    @RequestMapping(value="inscription", method = RequestMethod.POST)
    protected ModelAndView inscription(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("inscription");
        String email = request.getParameter("email");
        if (ControllerUtils.testEmail(email)){
            String password = request.getParameter("password");
            try {
                service.inscription(email, password);
                mv.addObject("returnMessage", ControllerUtils.generateSuccessMessage("Inscription réussie."));
            } catch (ServiceException e){
                mv.addObject("returnMessage", ControllerUtils.generateErrorMessage(e.getMessage()));
            }
        } else {
            mv.addObject("returnMessage", ControllerUtils.generateErrorMessage("Email incorrecte."));
        }
        return mv;
    }
    
    //---------------------------
    @RequestMapping(value="inscription_pro", method = RequestMethod.GET)
    protected String initInscriptionPro(HttpServletRequest request,HttpServletResponse response) {
       if (ControllerUtils.isUtilisateurConnecte(request)) return "erreur";
       return "inscription_pro";
    }

    @RequestMapping(value="inscription_pro", method = RequestMethod.POST)
    protected ModelAndView inscriptionPro(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String email = request.getParameter("email");
        ModelAndView mv = new ModelAndView("inscription_pro");
        if (ControllerUtils.testEmail(email)){
            String password = request.getParameter("password");
            String company = request.getParameter("company");
            try {
                long siret = Long.parseLong(request.getParameter("siret"));
                service.inscriptionPro(company, password, company, siret);
                mv.addObject("returnMessage", ControllerUtils.generateSuccessMessage("Inscription réussie."));
            } catch (NumberFormatException e){
                mv.addObject("returnMessage", ControllerUtils.generateErrorMessage("Le SIRET n'est pas un nombre."));
            } catch (ServiceException e){
                mv.addObject("returnMessage", ControllerUtils.generateErrorMessage(e.getMessage()));
            }
        } else {
            mv.addObject("returnMessage", ControllerUtils.generateErrorMessage("Email incorrecte."));
        }
        return mv;
    }

    //---------------------------
    @RequestMapping(value="profil", method = RequestMethod.GET)
    protected ModelAndView initProfil(HttpServletRequest request,HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return new ModelAndView("erreur");
        
        HttpSession session = request.getSession(false);
	String login = String.valueOf(session.getAttribute("login"));
	UtilisateurEntity user = this.service.getUtilisateur(login);

	ModelAndView mv = new ModelAndView("profil");
	
	mv.addObject("email",user.getEmail());
	mv.addObject("password",user.getMotDePasse());
        mv.addObject("prenom", user.getPrenom());
        mv.addObject("nom", user.getNom());
        if (user instanceof UtilisateurProEntity){
           mv.addObject("entreprise", ((UtilisateurProEntity)user).getEntreprise().getNom());
           mv.addObject("siret", ((UtilisateurProEntity)user).getEntreprise().getSiret());
        }
	return mv;
    }

    @RequestMapping(value="profil", method = RequestMethod.POST)
    protected ModelAndView profilUtilisateur(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
	String login = String.valueOf(session.getAttribute("login"));
        
        String password = request.getParameter("password");
        String password_confirmation = request.getParameter("password_confirmation");
        if (!password.equals(password_confirmation)){
            return new ModelAndView("erreur");
        }
        
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        
        if (ControllerUtils.isUtilisateurPro(request)){
            String company = request.getParameter("company");
            service.updateProUser(login, password, nom, prenom, company);
        } else {
            service.updateUser(login,password,nom,prenom);
        }
        
        return initProfil(request,response);
    }
}
