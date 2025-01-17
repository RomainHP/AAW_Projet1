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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
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
     * @return ModelAndView correspondant a la page "consultation"
     */
    @RequestMapping(value="consultation", method = RequestMethod.GET)
    protected ModelAndView initConsult(HttpServletRequest request,HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request))
	    return new ModelAndView("erreur");

	String login = ControllerUtils.getUserLogin(request);
	List<CompteEntity> accounts = this.service.consultation(login);
	
	ModelAndView mv = new ModelAndView("consultation");
	StringBuffer table_comptes = new StringBuffer();

	int cpt = 1;
	for (CompteEntity account : accounts) {
	    table_comptes.append("<tr>");
	    table_comptes.append("<td scope=\"row\">"+cpt+"</td>");
	    table_comptes.append("<td scope=\"row\">"+account.getId()+"</td>");
	    table_comptes.append("<td scope=\"row\">"+account.getProprietaire().getEmail()+"</td>");
	    table_comptes.append("<td scope=\"row\">"+account+"</td>");
	    table_comptes.append("<td scope=\"row\">"+account.getSolde()+"</td>");
            // Bouton détail
	    table_comptes.append("<td scope=\"row\"><form class=\"form\" action=\"details_compte.htm\" method=\"post\"><div class=\"form-group mb-3\">");
	    table_comptes.append("<input type=\"hidden\" class=\"form-control\" name=\"idCpt\" value=\""+account.getId()+"\"><button type=\"submit\" class=\"btn btn-primary btn-md\">Détails</button></div></form></td>");
	    
	    if(account instanceof LivretEntity){
                // Bouton supprimer
		table_comptes.append("<td scope=\"row\"><form class=\"form\" action=\"supprimer_livret.htm\" method=\"post\"><div class=\"form-group mb-3\">");
		table_comptes.append("<input type=\"hidden\" class=\"form-control\" name=\"id\" value=\""+account.getId()+"\"><button type=\"submit\" class=\"btn btn-primary btn-md\">Supprimer</button></div></form></td>");
            } else if (account instanceof CompteJointEntity && account.getProprietaire().getEmail().equals(login)){
                // Bouton supprimer
		table_comptes.append("<td scope=\"row\"><form class=\"form\" action=\"supprimer_compte_joint.htm\" method=\"post\"><div class=\"form-group mb-3\">");
		table_comptes.append("<input type=\"hidden\" class=\"form-control\" name=\"id\" value=\""+account.getId()+"\"><button type=\"submit\" class=\"btn btn-primary btn-md\">Supprimer</button></div></form></td>");
            } else {
                table_comptes.append("<td />");
            }
            
	    cpt++;
	}
        
	mv.addObject("table_comptes",table_comptes);
	return mv;
    }
    
    //--------------------
    /**
     * Affichage de la page "virement" en methode GET
     * @return ModelAndView correspondant a la page "virement"
     */
    @RequestMapping(value="virement", method = RequestMethod.GET)
    protected ModelAndView initVirement(HttpServletRequest request,HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return new ModelAndView("erreur");
        ModelAndView mv = new ModelAndView("virement"); 
        
        // Compte source
        StringBuilder options = new StringBuilder();
	List<CompteEntity> accounts = service.consultation(ControllerUtils.getUserLogin(request)); 
        for (CompteEntity compte : accounts){
            options.append("<option value=\"");
            options.append(compte.getId());
            options.append("\">");
            options.append(compte).append(" (").append(compte.getSolde()).append("€)");
            options.append("</option>");
        }
        
        // Compte destinataire
        StringBuilder options_dest = new StringBuilder();
	List<CompteEntity> all_accounts = service.getAllOpenAccounts();
        for (CompteEntity compte : all_accounts){
            options_dest.append("<option value=\"");
            options_dest.append(compte.getId());
            options_dest.append("\">");
            options_dest.append(compte).append(" - ").append(compte.getProprietaire());
            options_dest.append("</option>");
        }
        
        mv.addObject("options", options.toString());
        mv.addObject("options_dest", options_dest.toString());
        mv.addObject("form","virement.htm");
        
        return mv;
    }    
    
    /**
     * Affichage de la page "virement" en methode POST
     * @return ModelAndView correspondant a la page "virement" avec indication de réussite ou non
     */
    @RequestMapping(value="virement", method = RequestMethod.POST)
    protected ModelAndView virementCompte(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            String nomCompteSrc = request.getParameter("id");
            Long idCompteSrc = Long.parseLong(nomCompteSrc);

            String montant = request.getParameter("value");
            Double mnt = Double.parseDouble(montant);

            String nomDest = request.getParameter("id_dest");
            Long idCompteDest = Long.parseLong(nomDest);
            service.virement(idCompteSrc, idCompteDest, mnt,true);
            request.setAttribute("returnMessage", ControllerUtils.generateSuccessMessage("Virement effectué."));
        } catch (ServiceException e) {
            request.setAttribute("returnMessage", ControllerUtils.generateErrorMessage(e.getMessage()));
        } catch (Exception e){
            request.setAttribute("returnMessage", ControllerUtils.generateErrorMessage("Virement incorrect."));
        }
        
        return initVirement(request,response);
    } 
    
    //--------------------
    
    /**
     * Affichage de la page "ajout_livret" en methode GET
     * @return ModelAndView correspondant a la page "ajout_livret"
     */
    @RequestMapping(value="ajout_livret", method = RequestMethod.GET)
    protected ModelAndView initAjoutLivret(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	if (!ControllerUtils.isUtilisateurConnecte(request))
	    return new ModelAndView("erreur");
	
	return new ModelAndView("ajout_livret");
    }
    
    /**
     * Affichage de la page "ajout_livret" en methode POST
     * @return ModelAndView correspondant a la page "ajout_livret" avec indication de réussite ou non
     */
    @RequestMapping(value="ajout_livret", method = RequestMethod.POST)
    protected ModelAndView ajoutLivret(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	
        ModelAndView mv = new ModelAndView("ajout_livret");
        
	String login = ControllerUtils.getUserLogin(request);

	String newAccount = request.getParameter("nom_compte");

        try { 
            service.ajoutLivret(newAccount, login);
            mv.addObject("returnMessage", ControllerUtils.generateSuccessMessage("Le livret a bien été créé."));
        } catch (ServiceException e){
            mv.addObject("returnMessage", ControllerUtils.generateErrorMessage(e.getMessage()));
        }
	return mv;
    }
    
    //----------------------
    
    /**
     * Affichage de la page "supprimer_livret" en methode POST
     * @return ModelAndView correspondant a la page "supprimer_livret" avec indication de réussite ou non
     */
    @RequestMapping(value="supprimer_livret", method = RequestMethod.POST)
    protected ModelAndView supprimerLivret(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	if (!ControllerUtils.isUtilisateurConnecte(request))
	    return new ModelAndView("erreur");
	
	String idCompteStr = request.getParameter("id");
	Long idCompte = Long.parseLong(idCompteStr);
	
        try { 
            service.supprimerLivret(idCompte,true);
            request.setAttribute("returnMessage", ControllerUtils.generateSuccessMessage("Le livret a bien été supprimé."));
        } catch (ServiceException e){
            request.setAttribute("returnMessage", ControllerUtils.generateErrorMessage(e.getMessage()));
        }
	
	return initConsult(request, response);
    }
    
    //--------------------
    
    /**
     * Affichage de la page "ajout_compte_joint" en methode GET
     * @return ModelAndView correspondant a la page "ajout_compte_joint"
     */
    @RequestMapping(value="ajout_compte_joint", method = RequestMethod.GET)
    protected ModelAndView initAjoutCompteJoint(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	if (!ControllerUtils.isUtilisateurConnecte(request))
	    return new ModelAndView("erreur");
	
	return new ModelAndView("ajout_compte_joint");
    }
    
    /**
     * Affichage de la page "ajout_compte_joint" en methode POST
     * @return ModelAndView correspondant a la page "ajout_compte_joint" avec indication de réussite ou non
     */
    @RequestMapping(value="ajout_compte_joint", method = RequestMethod.POST)
    protected ModelAndView ajoutCompteJoint(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	
	String login = ControllerUtils.getUserLogin(request);
	String newAccount = request.getParameter("nom_compte");
        LinkedHashSet<String> co_proprietaires = new  LinkedHashSet<>();
        
        int cpt=1;
        String proprietaire=null;
        do {
            proprietaire = request.getParameter("field"+cpt);
            cpt++;
            if (proprietaire!=null) {
                co_proprietaires.add(proprietaire);
            }
        } while (proprietaire!=null);

        try { 
            service.ajoutCompteJoint(newAccount, login, co_proprietaires);
            request.setAttribute("returnMessage", ControllerUtils.generateSuccessMessage("Le compte joint a bien été créé."));
        } catch (ServiceException e){
            request.setAttribute("returnMessage", ControllerUtils.generateErrorMessage(e.getMessage()));
        }
	return initAjoutCompteJoint(request, response);
    }
    
    //----------------------
    
    /**
     * Affichage de la page "conulstation" en methode GET après suppression d'un compte
     * @return ModelAndView correspondant a la page "consultation" avec indication de réussite ou non dans la suppression de compte joint
     */
    @RequestMapping(value="supprimer_compte_joint", method = RequestMethod.POST)
    protected ModelAndView supprimerCompteJoint(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	if (!ControllerUtils.isUtilisateurConnecte(request))
	    return new ModelAndView("erreur");
	
        ModelAndView mv = new ModelAndView("consultation");
        
	String idCompteStr = request.getParameter("id");
	Long idCompte = Long.parseLong(idCompteStr);
	
        try { 
            service.supprimerCompteJoint(idCompte,true);
            mv.addObject("returnMessage", ControllerUtils.generateSuccessMessage("Le compte joint a bien été supprimé."));
        } catch (ServiceException e){
            mv.addObject("returnMessage", ControllerUtils.generateErrorMessage(e.getMessage()));
        }
	
	return mv;
    }
    
    //------------------------  
    /**
     * Affichage de la page "details_compte" en methode POST
     * @return ModelAndView correspondant a la page "details_compte" si réussite, page "erreur" autrement
     */
    @RequestMapping(value="details_compte", method = RequestMethod.POST)
    protected ModelAndView detailsCompte(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	if (!ControllerUtils.isUtilisateurConnecte(request))
	    return new ModelAndView("erreur");
	
	String idCompteStr = request.getParameter("idCpt");
	Long idCompte = Long.parseLong(idCompteStr);
	CompteEntity ce = service.getAccount(idCompte);
	
	ModelAndView mv = new ModelAndView("details_compte");
	StringBuffer table_transactions = new StringBuffer();

	for (TransactionEntity te : ce.getAllTransactions()) {
	    // Couleur de la ligne en fonction du type de la transaction
            if (te.getCptSource().getId().equals(idCompte)){
                table_transactions.append("<tr style=\" background-color:#ff5b5b \">");
            } else {
                table_transactions.append("<tr style=\" background-color:#5bff63 \">");
            }
            
            // COMPTE SOURCE
	    table_transactions.append("<td scope=\"row\">"+te.getCptSource()+"</td>");
            // Affichage ou non du prénom et nom (si renseigné)
            if (te.getCptSource().getProprietaire().getNom().isEmpty() && te.getCptSource().getProprietaire().getPrenom().isEmpty()){
                table_transactions.append("<td scope=\"row\">"+te.getCptSource().getProprietaire().getEmail()+"</td>");
            }else{
                table_transactions.append("<td scope=\"row\">"+te.getCptSource().getProprietaire().getEmail()+"<p style=\"font-size:12px\"> ( " + te.getCptSource().getProprietaire().getPrenom() + " " + te.getCptSource().getProprietaire().getNom() + ")</p> </td>");
            }
            
            // COMPTE DESTINATAIRE
            table_transactions.append("<td scope=\"row\">"+te.getCptDest()+"</td>");
            // Affichage ou non du prénom et nom (si renseigné)
            if (te.getCptDest().getProprietaire().getNom().isEmpty() && te.getCptDest().getProprietaire().getPrenom().isEmpty()){
                table_transactions.append("<td scope=\"row\">"+te.getCptDest().getProprietaire().getEmail()+"</td>");
            }else{
                table_transactions.append("<td scope=\"row\">"+te.getCptDest().getProprietaire().getEmail()+"<p style=\"font-size:12px\"> ( " + te.getCptDest().getProprietaire().getPrenom() + " " + te.getCptDest().getProprietaire().getNom() + ")</p> </td>");
            }
            
	    table_transactions.append("<td scope=\"row\">"+te.getMontant()+"</td>");
	    table_transactions.append("<td scope=\"row\">"+te.getDate()+"</td>");
	    table_transactions.append("</tr>");
	}

	mv.addObject("table_transactions",table_transactions);
	return mv;
    }
}
