package controllers;

import dao.utilisateur.UtilisateurEntity;
import dao.utilisateur.admin.AdminEntity;
import dao.utilisateur.pro.UtilisateurProEntity;
import exceptions.ServiceException;
import java.io.IOException;
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
     * @return ResponseEntity<?> correspondant a la page "connexion" si réussite, page "erreur" sinon
     */
    @RequestMapping(value="connexion", method = RequestMethod.GET)
    protected String initConnexion(HttpServletRequest request,HttpServletResponse response) {
        return "connexion";
    }
    
    /**
     * Affichage de la page "index" en methode POST
     * @return ResponseEntity<?> correspondant a la page "index" si connexion réussite, page "erreur" sinon
     */
    @RequestMapping(value="connexion", method = RequestMethod.POST)
    protected ResponseEntity<?> connexion(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONObject jObj = ControllerUtils.requestToJSONObj(request);
        String userResponse = "[]";
	HttpStatus status = HttpStatus.BAD_REQUEST;
        
        String identifiant = jObj.getString("email");
        String mdp = jObj.getString("password");
        
        try{
            JSONObject json = new JSONObject();
            service.connexion(identifiant, mdp);
            json.put("login", identifiant);
            UtilisateurEntity utilisateur = service.getUtilisateur(identifiant);
            // si l'utilisateur est admin ou non
            json.put("admin", utilisateur instanceof AdminEntity);
            // si l'utilisateur est pro ou non
            json.put("pro", utilisateur instanceof UtilisateurProEntity);
            userResponse = json.toString();
            status = HttpStatus.OK;
        } catch (ServiceException e){
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //---------------------------
    
    /**
     * Affichage de la page "deconnexion" en methode GET
     * @return ResponseEntity<?> correspondant a la page "index" si réussite, page "erreur" sinon
     */
    @RequestMapping(value="deconnexion", method = RequestMethod.GET)
    protected String initDeconnexion(HttpServletRequest request,HttpServletResponse response) {
        return "index";
    }

    //---------------------------
    
    /**
     * Affichage de la page "inscription" en methode GET
     * @return ResponseEntity<?> correspondant a la page "inscription" si réussite, page "erreur" sinon
     */
    @RequestMapping(value="inscription", method = RequestMethod.GET)
    protected String initInscription(HttpServletRequest request,HttpServletResponse response) {
       return "inscription";
    }

    
    /**
     * Affichage de la page "inscription" en methode POST
     * @return ResponseEntity<?> correspondant a la page "inscription" si réussite affichage d'une erreur sinon
     */
    @RequestMapping(value="register", method = RequestMethod.POST)
    protected ResponseEntity<?> inscription(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	JSONObject jObj = ControllerUtils.requestToJSONObj(request);
        String userResponse = "[]";
	HttpStatus status = HttpStatus.BAD_REQUEST;
        
        String email = jObj.getString("email");
        // Si l'email est valide
        if (ControllerUtils.testEmail(email)){
            String password = jObj.getString("password");
            try {
                service.inscription(email, password);
                status = HttpStatus.OK;
            } catch (ServiceException e){
                userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
            }
        } else {
            userResponse = new JSONObject().put("errorMessage", "Email non valide").toString();
        }
        return new ResponseEntity(userResponse, status);
    }
    
    //---------------------------
    /**
     * Affichage de la page "inscription_pro" en methode GET
     * @return ResponseEntity<?> correspondant a la page "inscription_pro" si réussite affichage d'une erreur sinon
     */
    @RequestMapping(value="inscription_pro", method = RequestMethod.GET)
    protected String initInscriptionPro(HttpServletRequest request,HttpServletResponse response) {
       return "inscription_pro";
    }

    /**
     * Affichage de la page "inscription_pro" en methode POST
     * @return ResponseEntity<?> correspondant a la page "inscription_pro" si réussite affichage d'une erreur sinon
     */
    @RequestMapping(value="inscription_pro", method = RequestMethod.POST)
    protected ResponseEntity<?> inscriptionPro(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONObject jObj = ControllerUtils.requestToJSONObj(request);
        String userResponse = "[]";
	HttpStatus status = HttpStatus.BAD_REQUEST;
        String email = jObj.getString("email");
        // Si l'email est valide
        if (ControllerUtils.testEmail(email)){
            String password = jObj.getString("password");
            String company = request.getParameter("company");
            try {
                long siret = Long.parseLong(request.getParameter("siret"));
                service.inscriptionPro(email, password, company, siret);
                status = HttpStatus.OK;
            } catch (NumberFormatException e){
                userResponse = new JSONObject().put("errorMessage", "Le SIRET n'est pas un nombre.").toString();
            } catch (ServiceException e){
                userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
            }
        } else {
            userResponse = new JSONObject().put("errorMessage", "Email non valide").toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //---------------------------
    
    /**
     * Affichage de la page "profil" en methode GET
     * @return ResponseEntity<?> correspondant a la page "profil" si réussite affichage de la page "erreur" autrement
     */
    @RequestMapping(value="profil", method = RequestMethod.GET)
    protected ResponseEntity<?> initProfil(HttpServletRequest request,HttpServletResponse response) throws JSONException, IOException {
        JSONObject jObj = ControllerUtils.requestToJSONObj(request);
        String userResponse = "[]";
	HttpStatus status = HttpStatus.BAD_REQUEST;
        String login = jObj.getString("login");
	UtilisateurEntity user = this.service.getUtilisateur(login);
	
        // On envoie les champs de l'utilisateur à la vue
        JSONObject json = new JSONObject()
                            .put("email", user.getEmail())
                            .put("password", user.getMotDePasse())
                            .put("prenom", user.getPrenom())
                            .put("nom", user.getNom());
        // Si l'utilisateur est pro, il a des champs supplémentaires
        if (user instanceof UtilisateurProEntity){
            json.put("entreprise", ((UtilisateurProEntity)user).getEntreprise().getNom());
            json.put("siret", ((UtilisateurProEntity)user).getEntreprise().getSiret());
        }
        status = HttpStatus.OK;
	return new ResponseEntity(userResponse, status);
    }

    /**
     * Affichage de la page "profil" en methode POST
     * @return ResponseEntity<?> correspondant a la page "profil" si réussite affichage d'une erreur sinon
     */
    @RequestMapping(value="profil", method = RequestMethod.POST)
    protected ResponseEntity<?> profilUtilisateur(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONObject jObj = ControllerUtils.requestToJSONObj(request);
        String userResponse = "[]";
	HttpStatus status = HttpStatus.BAD_REQUEST;
        
	String login = jObj.getString("login");
        // Vérification du mot de passe
        String password = jObj.getString("password");
        String password_confirmation = jObj.getString("password_confirmation");
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
        
        return new ResponseEntity(userResponse, status);
    }
}
