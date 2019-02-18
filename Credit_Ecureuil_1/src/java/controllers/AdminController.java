package controllers;

import dao.compte.CompteEntity;
import dao.compte.comptejoint.CompteJointEntity;
import dao.compte.livret.LivretEntity;
import exceptions.ServiceException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import utils.ControllerUtils;

/**
 * Le controller utilisé par les utilisateur de type Admin
 *
 * @author romain
 */
@Controller
public class AdminController {

    @Autowired
    CompteController compteController;

    //---------------------------
    /**
     * Fonction de type GET utilisée pour la consultation de compte d'admin
     *
     */
    @RequestMapping(value = "consultation_admin", method = RequestMethod.GET)
    protected ResponseEntity<?> initConsultationComptesAdmin(
            HttpServletRequest request) throws Exception {
	HttpStatus status = HttpStatus.BAD_REQUEST;
	JSONObject answer = new JSONObject();
	List<CompteEntity> allAcc = compteController.getAllAccounts();
	
	for (CompteEntity ce : allAcc) {
	    answer.accumulate("id", ce.getId());
	    answer.accumulate("proprio", ce.getProprietaire());
	    answer.accumulate("solde", ce.getSolde());

	    if(ce instanceof LivretEntity)
		answer.accumulate("nomCompteSrc", ((LivretEntity)ce).getNom());
	    else if(ce instanceof CompteJointEntity)
		answer.accumulate("nomCompteSrc", ((CompteJointEntity)ce).getNom());
	    else if(ce instanceof CompteEntity)
		answer.accumulate("nomCompteSrc", "Compte courant");
	}
	status = HttpStatus.OK;
	System.out.println(answer);
        return new ResponseEntity(answer.toString(), status);
    }

    //--------------------
    /**
     * Affichage de la page ajout de livret en mode admin avec une methode POST
     */
    @RequestMapping(value = "ajout_livret_admin", method = RequestMethod.POST)
    protected ResponseEntity<?> ajoutLivret(
            HttpServletRequest request) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);

            String nomCompte = jObj.getString("nom_compte");
            String utilisateur = jObj.getString("utilisateur");

            try {
                compteController.service.ajoutLivret(nomCompte, utilisateur);
                status = HttpStatus.OK;
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", "Email non valide").toString();
            }
        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //----------------------
    /**
     * Affichage de la page suppression de livret en mode admin avec une methode
     * POST
     *
     */
    @RequestMapping(value = "supprimer_livret_admin", method = RequestMethod.POST)
    protected ResponseEntity<?> supprimerLivret(
            HttpServletRequest request) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);

            String idCompteStr = jObj.getString("id");
            Long idCompte = Long.parseLong(idCompteStr);

            try {
                compteController.service.supprimerLivret(idCompte, false);
                status = HttpStatus.OK;
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", "Email non valide").toString();
            }

        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //----------------------
    /**
     * Affichage de la page suppression de compte joint en mode admin avec une
     * methode POST
     */
    @RequestMapping(value = "supprimer_compte_joint_admin", method = RequestMethod.POST)
    protected ResponseEntity<?> supprimerCompteJoint(
            HttpServletRequest request) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);

            String idCompteStr = jObj.getString("id");
            Long idCompte = Long.parseLong(idCompteStr);

            try {
                compteController.service.supprimerCompteJoint(idCompte, false);
                status = HttpStatus.OK;
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", "Email non valide").toString();
            }

        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //--------------------
    /**
     * Affichage de la page virement en mode admin avec une methode GET
     */
    @RequestMapping(value = "virement_admin", method = RequestMethod.GET)
    protected ResponseEntity<?> initVirementAdmin(HttpServletRequest request) {
        return null;
    }

    /**
     * Affichage de la page virement en mode admin avec une methode POST
     */
    @RequestMapping(value = "virement_admin", method = RequestMethod.POST)
    protected ResponseEntity<?> virementAdmin(
            HttpServletRequest request) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);
            try {
                String nomCompteSrc = jObj.getString("source");
                Long idCompteSrc = Long.parseLong(nomCompteSrc);

                String montant = jObj.getString("montant");
                Double mnt = Double.parseDouble(montant);

                String nomDest = jObj.getString("dest");
                Long idCompteDest = Long.parseLong(nomDest);

                compteController.service.virement(idCompteSrc, idCompteDest, mnt, false);
                status = HttpStatus.OK;
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
            } catch (Exception e) {
                userResponse = new JSONObject().put("errorMessage", "Virement incorrect").toString();
            }

        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }
}
