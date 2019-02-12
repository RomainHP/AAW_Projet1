package controllers;

import dao.compte.CompteEntity;
import dao.compte.comptejoint.CompteJointEntity;
import dao.compte.livret.LivretEntity;
import dao.transaction.TransactionEntity;
import exceptions.ServiceException;
import java.util.LinkedHashSet;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * @return ModelAndView correspondant a la page "consultation"
     */
    @RequestMapping(value = "consultation", method = RequestMethod.GET)
    protected ResponseEntity<?> initConsult(HttpServletRequest request, HttpServletResponse response) throws Exception {        
	String login = request.getParameter("mail");	
	
        List<CompteEntity> accounts = this.service.consultation(login);
	
	JSONObject jObj = new JSONObject();
	for (CompteEntity account : accounts) {
	    String compte = "Compte";
	    JSONObject jObj2 = new JSONObject();
	    jObj2.append("id", account.getId());
	    jObj2.append("prop", account.getProprietaire());
	    
	    if(account instanceof CompteJointEntity || account instanceof LivretEntity){
		jObj2.append("name", ((LivretEntity)account).getNom());
	    }else{
		jObj2.append("name", "Compte courant");
	    }
	    
	    
	    jObj2.append("solde", account.getSolde());
	    jObj.append(compte, jObj2);
	}
	return new ResponseEntity(jObj.toString(), HttpStatus.OK);
    }

    //--------------------
    /**
     * Affichage de la page "virement" en methode GET
     *
     * @return ModelAndView correspondant a la page "virement"
     */
    @RequestMapping(value = "virement", method = RequestMethod.GET)
    protected ResponseEntity<?> initVirement(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Compte destinataire
        List<CompteEntity> all_accounts = service.getAllOpenAccounts();
	JSONObject jObj = new JSONObject();
        for (CompteEntity compte : all_accounts) {
	    jObj.append("id", compte.getId());
	    jObj.append("prop", compte.getProprietaire());
	    if(compte instanceof CompteJointEntity || compte instanceof LivretEntity){
		jObj.append("name", ((LivretEntity)compte).getNom());
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
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
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
     * @return ModelAndView correspondant a la page "ajout_livret" avec
     * indication de réussite ou non
     */
    @RequestMapping(value = "ajout_livret", method = RequestMethod.POST)
    protected ResponseEntity<?> ajoutLivret(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
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
     * @return ModelAndView correspondant a la page "supprimer_livret" avec
     * indication de réussite ou non
     */
    @RequestMapping(value = "supprimer_livret", method = RequestMethod.POST)
    protected ResponseEntity<?> supprimerLivret(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
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
     * @return ModelAndView correspondant a la page "ajout_compte_joint" avec
     * indication de réussite ou non
     */
    @RequestMapping(value = "ajout_compte_joint", method = RequestMethod.POST)
    protected ResponseEntity<?> ajoutCompteJoint(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);

            String login = jObj.getString("login");
            String newAccount = jObj.getString("nom_compte");
            LinkedHashSet<String> co_proprietaires = new LinkedHashSet<>();

            int cpt = 1;
            String proprietaire = null;
            do {
                proprietaire = jObj.getString("field" + cpt);
                cpt++;
                if (proprietaire != null) {
                    co_proprietaires.add(proprietaire);
                }
            } while (proprietaire != null);

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
     * @return ModelAndView correspondant a la page "consultation" avec
     * indication de réussite ou non dans la suppression de compte joint
     */
    @RequestMapping(value = "supprimer_compte_joint", method = RequestMethod.POST)
    protected ResponseEntity<?> supprimerCompteJoint(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
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
     * @return ModelAndView correspondant a la page "details_compte" si
     * réussite, page "erreur" autrement
     */
    @RequestMapping(value = "details_compte", method = RequestMethod.POST)
    protected ResponseEntity<?> detailsCompte(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);

            String idCompteStr = jObj.getString("idCpt");
            Long idCompte = Long.parseLong(idCompteStr);
            CompteEntity ce = service.getAccount(idCompte);

            StringBuffer table_transactions = new StringBuffer();

            for (TransactionEntity te : ce.getAllTransactions()) {
                // Couleur de la ligne en fonction du type de la transaction
                if (te.getCptSource().getId().equals(idCompte)) {
                    table_transactions.append("<tr style=\" background-color:#ff5b5b \">");
                } else {
                    table_transactions.append("<tr style=\" background-color:#5bff63 \">");
                }

                // COMPTE SOURCE
                table_transactions.append("<td scope=\"row\">" + te.getCptSource() + "</td>");
                // Affichage ou non du prénom et nom (si renseigné)
                if (te.getCptSource().getProprietaire().getNom().isEmpty() && te.getCptSource().getProprietaire().getPrenom().isEmpty()) {
                    table_transactions.append("<td scope=\"row\">" + te.getCptSource().getProprietaire().getEmail() + "</td>");
                } else {
                    table_transactions.append("<td scope=\"row\">" + te.getCptSource().getProprietaire().getEmail() + "<p style=\"font-size:12px\"> ( " + te.getCptSource().getProprietaire().getPrenom() + " " + te.getCptSource().getProprietaire().getNom() + ")</p> </td>");
                }

                // COMPTE DESTINATAIRE
                table_transactions.append("<td scope=\"row\">" + te.getCptDest() + "</td>");
                // Affichage ou non du prénom et nom (si renseigné)
                if (te.getCptDest().getProprietaire().getNom().isEmpty() && te.getCptDest().getProprietaire().getPrenom().isEmpty()) {
                    table_transactions.append("<td scope=\"row\">" + te.getCptDest().getProprietaire().getEmail() + "</td>");
                } else {
                    table_transactions.append("<td scope=\"row\">" + te.getCptDest().getProprietaire().getEmail() + "<p style=\"font-size:12px\"> ( " + te.getCptDest().getProprietaire().getPrenom() + " " + te.getCptDest().getProprietaire().getNom() + ")</p> </td>");
                }

                table_transactions.append("<td scope=\"row\">" + te.getMontant() + "</td>");
                table_transactions.append("<td scope=\"row\">" + te.getDate() + "</td>");
                table_transactions.append("</tr>");
            }

        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }
}
