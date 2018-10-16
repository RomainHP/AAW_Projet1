package controllers;

import dao.compte.CompteEntity;
import dao.utilisateur.UtilisateurDao;
import dao.utilisateur.UtilisateurEntity;
import dao.utilisateur.UtilisateurProEntity;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.utilisateur.UtilisateurServiceImpl;
import utils.ControllerUtils;

/**
 *
 * @author rcharpen
 */
@Controller
public class UtilisateurController {
        
    private UtilisateurServiceImpl service;
    
    public UtilisateurController() {
        this.service = new UtilisateurServiceImpl();
    }
    
    //---------------------------
    @RequestMapping(value="connexion", method = RequestMethod.GET)
    protected String initConnexion(HttpServletRequest request,HttpServletResponse response) throws Exception {
        return ControllerUtils.isUtilisateurConnecte(request) ? "erreur" : "connexion";
    }
    
    @RequestMapping(value="connexion", method = RequestMethod.POST)
    protected ModelAndView connexion(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mv; 
        
        HttpSession session = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        
        String identifiant = request.getParameter("email");
        String mdp = request.getParameter("password");
        
        // Si la session n'est pas déjà crée
	if(this.service.connexion(identifiant, mdp)){
	    if (session==null){
		session = request.getSession(true); // on la crée
	    }
	    session.setAttribute("login", identifiant);
            session.setAttribute("pro", service.getUtilisateur(identifiant) instanceof UtilisateurProEntity);
	    mv = new ModelAndView("index");
	}else{
	    mv = new ModelAndView("erreur");
	}
        
        return mv;
    }

    //---------------------------
    @RequestMapping(value="deconnexion", method = RequestMethod.GET)
    protected String initDeconnexion(HttpServletRequest request,HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);

        // Si l'utilisateur n'est pas déjà connecté
        if (!ControllerUtils.isUtilisateurConnecte(request)){
            return "erreur"; // on renvoie la page d'erreur
        }
        
        session.setAttribute("login", null);
        session.setAttribute("pro", null);
        return "index";
    }

    //---------------------------
    @RequestMapping(value="inscription", method = RequestMethod.GET)
    protected String initInscription(HttpServletRequest request,HttpServletResponse response) throws Exception {
       if (ControllerUtils.isUtilisateurConnecte(request)) return "erreur";
       return "inscription";
    }

    @RequestMapping(value="inscription", method = RequestMethod.POST)
    protected ModelAndView inscription(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	if(service.inscription(request.getParameter("email"),request.getParameter("password")) == true){
	    ModelAndView mv = new ModelAndView("index");
	    return mv;
	}else{
	    ModelAndView mv = new ModelAndView("erreur");
	    return mv;
	}
    }
    
    //---------------------------
    @RequestMapping(value="inscription_pro", method = RequestMethod.GET)
    protected String initInscriptionPro(HttpServletRequest request,HttpServletResponse response) throws Exception {
       if (ControllerUtils.isUtilisateurConnecte(request)) return "erreur";
       return "inscription_pro";
    }

    @RequestMapping(value="inscription_pro", method = RequestMethod.POST)
    protected ModelAndView inscriptionPro(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String company = request.getParameter("company");
        long siret = 0l;
        try {
            siret = Long.parseLong(request.getParameter("siret"));
        } catch (Exception e){
            ModelAndView mv = new ModelAndView("erreur");
	    return mv;
        }
	if(service.inscriptionPro(email,password,company,siret) == true){
	    ModelAndView mv = new ModelAndView("index");
	    return mv;
	}else{
	    ModelAndView mv = new ModelAndView("erreur");
	    return mv;
	}
    }

    //---------------------------
    @RequestMapping(value="profil", method = RequestMethod.GET)
    protected ModelAndView initProfil(HttpServletRequest request,HttpServletResponse response) throws Exception {
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
        }
	return mv;
    }

    @RequestMapping(value="profil", method = RequestMethod.POST)
    protected ModelAndView profilUtilisateur(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
