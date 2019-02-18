package controllers;

import dao.compte.CompteEntity;
import dao.compte.comptejoint.CompteJointEntity;
import dao.compte.livret.LivretEntity;
import dao.transaction.TransactionEntity;
import exceptions.ServiceException;
import java.util.LinkedHashSet;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import services.compte.CompteService;
import utils.ControllerUtils;

/**
 * Le controller correspondant à l'affichage des comptes utilisateurs
 */
@Controller
public class CompteController {

    @Autowired
    CompteService service;

    //----------------------
    /**
     * Affichage de la page "consultation" en methode GET
     *
     * @return ResponseEntity correspondant a la page "consultation"
     */
    @RequestMapping(value = "consultation", method = RequestMethod.GET)
    protected ResponseEntity<?> initConsult(HttpServletRequest request) throws Exception {        
	String login = request.getParameter("mail");	
	
        List<CompteEntity> accounts = this.service.consultation(login);
	boolean alreadyAdded = false;
	JSONObject jObj = new JSONObject();
	for (CompteEntity account : accounts) {	    
	    String compte = "Compte";
	    JSONObject jObj2 = new JSONObject();
	    if(account instanceof CompteEntity || account instanceof LivretEntity){
		jObj2.append("id", account.getId());
		jObj2.append("prop", account.getProprietaire());
	    }
	    
	    if(account instanceof LivretEntity)
		jObj2.append("name", ((LivretEntity)account).getNom());
	    else if(account instanceof CompteJointEntity){
		if(!alreadyAdded){
		    jObj2.append("id", account.getId());
		    jObj2.append("prop", account.getProprietaire());
		    jObj2.append("name", ((CompteJointEntity)account).getNom());
		    jObj2.append("solde", account.getSolde());
		    alreadyAdded = true;
		}
	    }
	    else
		jObj2.append("name", "Compte courant");
	    
	    if(account instanceof CompteEntity || account instanceof LivretEntity)
		jObj2.append("solde", account.getSolde());
	    
	    jObj.append(compte, jObj2);
	}
	return new ResponseEntity(jObj.toString(), HttpStatus.OK);
    }

    //--------------------
    /**
     * Affichage de la page "virement" en methode GET
     *
     * @return ResponseEntity correspondant a la page "virement"
     */
    @RequestMapping(value = "virement", method = RequestMethod.GET)
    protected ResponseEntity<?> initVirement(HttpServletRequest request) throws Exception {
        // Compte destinataire
        List<CompteEntity> all_accounts = service.getAllOpenAccounts();
	JSONObject jObj = new JSONObject();
        for (CompteEntity compte : all_accounts) {
	    jObj.append("id", compte.getId());
	    jObj.append("prop", compte.getProprietaire());
	    if(compte instanceof LivretEntity){
		jObj.append("name", ((LivretEntity)compte).getNom());
	    }else if(compte instanceof CompteJointEntity ){
		    jObj.append("name", ((CompteJointEntity)compte).getNom());
	    }else{
		jObj.append("name", "Compte courant");
	    }
	    jObj.append("solde", compte.getSolde());
        }
	
        return new ResponseEntity(jObj.toString(), HttpStatus.OK);
    }

    /**
     * Affichage de la page "virement" en methode POST
     *	
     */
    @RequestMapping(value = "virement", method = RequestMethod.POST)
    protected ResponseEntity<?> virementCompte(
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
                service.virement(idCompteSrc, idCompteDest, mnt, true);
                status = HttpStatus.OK;
            } catch (NumberFormatException e) {
                userResponse = new JSONObject().put("errorMessage", "Virement incorrect.").toString();
            } catch (Exception e) {
                userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
            } 

        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //--------------------
    /**
     * Affichage de la page "ajout_livret" en methode POST
     *
     * @return ResponseEntity correspondant a la page "ajout_livret" avec
     * indication de réussite ou non
     */
    @RequestMapping(value = "ajout_livret", method = RequestMethod.POST)
    protected ResponseEntity<?> ajoutLivret(
            HttpServletRequest request) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
	    JSONObject jObj = ControllerUtils.requestToJSONObj(request);
	    
            String login = jObj.getString("login");
            String nameAccount = jObj.getString("name");
	    
            try {
                service.ajoutLivret(nameAccount, login);
                status = HttpStatus.OK;
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
            }
        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //----------------------
    /**
     * Affichage de la page "supprimer_livret" en methode POST
     *
     * @return ResponseEntity correspondant a la page "supprimer_livret" avec
     * indication de réussite ou non
     */
    @RequestMapping(value = "supprimer_livret", method = RequestMethod.POST)
    protected ResponseEntity<?> supprimerLivret(
            HttpServletRequest request) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);

            String idCompteStr = jObj.getString("id");
            Long idCompte = Long.parseLong(idCompteStr);

            try {
                service.supprimerLivret(idCompte, true);
                status = HttpStatus.OK;
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
            }

        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //--------------------
    /**
     * Affichage de la page "ajout_compte_joint" en methode POST
     *
     * @return ResponseEntity correspondant a la page "ajout_compte_joint" avec
     * indication de réussite ou non
     */
    @RequestMapping(value = "create_linked_account", method = RequestMethod.POST)
    protected ResponseEntity<?> ajoutCompteJoint(
            HttpServletRequest request) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);

            String login = jObj.getString("login");
            String newAccount = jObj.getString("nom_compte");
            LinkedHashSet<String> co_proprietaires = new LinkedHashSet<>();

            String proprietaire = null;
	    JSONArray tt = jObj.getJSONArray("listeCoPro");
	    int nbCopro = tt.length();
	    for(int i=0; i<nbCopro;i++){
		proprietaire = tt.get(i).toString();
		co_proprietaires.add(proprietaire);
	    }
            try {
                service.ajoutCompteJoint(newAccount, login, co_proprietaires);
                status = HttpStatus.OK;
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
            }
        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //----------------------
    /**
     * Affichage de la page "conulstation" en methode GET après suppression d'un
     * compte
     *
     * @return ResponseEntity correspondant a la page "consultation" avec
     * indication de réussite ou non dans la suppression de compte joint
     */
    @RequestMapping(value = "supprimer_compte_joint", method = RequestMethod.POST)
    protected ResponseEntity<?> supprimerCompteJoint(
            HttpServletRequest request) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);

            String idCompteStr = jObj.getString("id");
            Long idCompte = Long.parseLong(idCompteStr);

            try {
                service.supprimerCompteJoint(idCompte, true);
                status = HttpStatus.OK;
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
            }

        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //------------------------  
    /**
     * Affichage de la page "details_compte" en methode POST
     *
     * @return ResponseEntity correspondant a la page "details_compte" si
     * réussite, page "erreur" autrement
     */
    @RequestMapping(value = "details", method = RequestMethod.GET)
    protected ResponseEntity<?> detailsCompte(
            HttpServletRequest request) throws Exception {
	JSONObject jObj = ControllerUtils.requestToJSONObj(request);

	String idCompteStr = jObj.getString("idCompte");
	Long idCompte = Long.parseLong(idCompteStr);
	
	CompteEntity ce = service.getAccount(idCompte);
	JSONObject answer = new JSONObject();
	
	for (TransactionEntity trans : ce.getAllTransactions()) {
	    answer.accumulate("montant", trans.getMontant());
	    answer.accumulate("date", trans.getDate());
	    
	    
	    if(trans.getCptSource() instanceof LivretEntity)
		answer.accumulate("nomCompteSrc", ((LivretEntity)trans.getCptSource()).getNom());
	    else if(trans.getCptSource() instanceof CompteJointEntity)
		answer.accumulate("nomCompteSrc", ((CompteJointEntity)trans.getCptSource()).getNom());
	    else if(trans.getCptSource() instanceof CompteEntity)
		answer.accumulate("nomCompteSrc", "Compte courant");
	    
	    answer.accumulate("nomPropCptSrc", trans.getCptSource().getProprietaire().getEmail());
	    
	    if(trans.getCptDest() instanceof LivretEntity)
		answer.accumulate("nomCompteDest", ((LivretEntity)trans.getCptDest()).getNom());
	    else if(trans.getCptDest() instanceof CompteJointEntity)
		answer.accumulate("nomCompteDest", ((CompteJointEntity)trans.getCptDest()).getNom());
	    else if(trans.getCptDest() instanceof CompteEntity)
		answer.accumulate("nomCompteDest", "Compte courant");
	    
	    answer.accumulate("nomPropCptDest", trans.getCptDest().getProprietaire().getEmail());
	}

        return new ResponseEntity(answer.toString(), HttpStatus.OK);
    }
}
