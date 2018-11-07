package controllers;

import dao.utilisateur.UtilisateurEntity;
import dao.utilisateur.admin.AdminEntity;
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
 * Le controller utilisé par les utilisateurs de types classiques (hors admin)
 */
@Controller
public class UtilisateurController {
        
    @Autowired
    UtilisateurService service;
    
    //---------------------------
    /**
     * Affichage de la page "connexion" en methode GET
     * @return ModelAndView correspondant a la page "connexion" si réussite, page "erreur" sinon
     */
    @RequestMapping(value="connexion", method = RequestMethod.GET)
    protected String initConnexion(HttpServletRequest request,HttpServletResponse response) {
        return ControllerUtils.isUtilisateurConnecte(request) ? "erreur" : "connexion";
    }
    
    /**
     * Affichage de la page "index" en methode POST
     * @return ModelAndView correspondant a la page "index" si connexion réussite, page "erreur" sinon
     */
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
            UtilisateurEntity utilisateur = service.getUtilisateur(identifiant);
            // si l'utilisateur est admin ou non
            session.setAttribute("admin", utilisateur instanceof AdminEntity);
            // si l'utilisateur est pro ou non
            session.setAttribute("pro", utilisateur instanceof UtilisateurProEntity);
            return new ModelAndView("index");
        } catch (ServiceException e){
            ModelAndView mv = new ModelAndView("connexion");
            mv.addObject("returnMessage", ControllerUtils.generateErrorMessage(e.getMessage()));
            return mv;
        }
    }

    //---------------------------
    
    /**
     * Affichage de la page "deconnexion" en methode GET
     * @return ModelAndView correspondant a la page "index" si réussite, page "erreur" sinon
     */
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
    
    /**
     * Affichage de la page "inscription" en methode GET
     * @return ModelAndView correspondant a la page "inscription" si réussite, page "erreur" sinon
     */
    @RequestMapping(value="inscription", method = RequestMethod.GET)
    protected String initInscription(HttpServletRequest request,HttpServletResponse response) {
       if (ControllerUtils.isUtilisateurConnecte(request)) return "erreur";
       return "inscription";
    }

    
    /**
     * Affichage de la page "inscription" en methode POST
     * @return ModelAndView correspondant a la page "inscription" si réussite affichage d'une erreur sinon
     */
    @RequestMapping(value="inscription", method = RequestMethod.POST)
    protected ModelAndView inscription(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("inscription");
        String email = request.getParameter("email");
        // Si l'email est valide
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
    /**
     * Affichage de la page "inscription_pro" en methode GET
     * @return ModelAndView correspondant a la page "inscription_pro" si réussite affichage d'une erreur sinon
     */
    @RequestMapping(value="inscription_pro", method = RequestMethod.GET)
    protected String initInscriptionPro(HttpServletRequest request,HttpServletResponse response) {
       if (ControllerUtils.isUtilisateurConnecte(request)) return "erreur";
       return "inscription_pro";
    }

    /**
     * Affichage de la page "inscription_pro" en methode POST
     * @return ModelAndView correspondant a la page "inscription_pro" si réussite affichage d'une erreur sinon
     */
    @RequestMapping(value="inscription_pro", method = RequestMethod.POST)
    protected ModelAndView inscriptionPro(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String email = request.getParameter("email");
        ModelAndView mv = new ModelAndView("inscription_pro");
        // Si l'email est valide
        if (ControllerUtils.testEmail(email)){
            String password = request.getParameter("password");
            String company = request.getParameter("company");
            try {
                long siret = Long.parseLong(request.getParameter("siret"));
                service.inscriptionPro(email, password, company, siret);
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
    
    /**
     * Affichage de la page "profil" en methode GET
     * @return ModelAndView correspondant a la page "profil" si réussite affichage de la page "erreur" autrement
     */
    @RequestMapping(value="profil", method = RequestMethod.GET)
    protected ModelAndView initProfil(HttpServletRequest request,HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return new ModelAndView("erreur");
        
        HttpSession session = request.getSession(false);
	String login = String.valueOf(session.getAttribute("login"));
	UtilisateurEntity user = this.service.getUtilisateur(login);

	ModelAndView mv = new ModelAndView("profil");
	
        // On envoie les champs de l'utilisateur à la vue
	mv.addObject("email",user.getEmail());
	mv.addObject("password",user.getMotDePasse());
        mv.addObject("prenom", user.getPrenom());
        mv.addObject("nom", user.getNom());
        // Si l'utilisateur est pro, il a des champs supplémentaires
        if (user instanceof UtilisateurProEntity){
           mv.addObject("entreprise", ((UtilisateurProEntity)user).getEntreprise().getNom());
           mv.addObject("siret", ((UtilisateurProEntity)user).getEntreprise().getSiret());
        }
	return mv;
    }

    /**
     * Affichage de la page "profil" en methode POST
     * @return ModelAndView correspondant a la page "profil" si réussite affichage d'une erreur sinon
     */
    @RequestMapping(value="profil", method = RequestMethod.POST)
    protected ModelAndView profilUtilisateur(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	String login = ControllerUtils.getUserLogin(request);
        
        // Vérification du mot de passe
        String password = request.getParameter("password");
        String password_confirmation = request.getParameter("password_confirmation");
        if (!password.equals(password_confirmation)){
            request.setAttribute("returnMessage", ControllerUtils.generateErrorMessage("Mots de passe différents."));
        } else {
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");

            // Actualise les données de l'utilisateur
            if (ControllerUtils.isUtilisateurPro(request)){
                String company = request.getParameter("company");
                service.updateProUser(login, password, nom, prenom, company);
            } else {
                service.updateUser(login,password,nom,prenom);
            }
            request.setAttribute("returnMessage", ControllerUtils.generateSuccessMessage("Modification effectuée."));
        }
        
        return initProfil(request,response);
    }
}
