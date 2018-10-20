package controllers;

import dao.compte.CompteEntity;
import dao.transaction.TransactionEntity;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.compte.CompteService;
import services.transaction.TransactionService;
import utils.ControllerUtils;

/**
 *
 * @author rcharpen
 */
@Controller
public class CompteController {
    
    @Autowired
    private CompteService service;
    
    @Autowired
    private TransactionService transactionService;
    
    //----------------------
    @RequestMapping(value="consultation", method = RequestMethod.GET)
    protected ModelAndView initConsult(HttpServletRequest request,HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request))
	    return new ModelAndView("erreur");
	
	HttpSession session = request.getSession(false);
	String login = String.valueOf(session.getAttribute("login"));
	List<CompteEntity> accounts = this.service.consultation(login);
	
	ModelAndView mv = new ModelAndView("consultation");
	StringBuffer table_comptes = new StringBuffer();
	
	table_comptes.append("<table class=\"table\">");
	table_comptes.append("<thead style=\"background-color:#ffb860;\">");
	table_comptes.append("<tr>");
        table_comptes.append("<th scope=\"col\">#</th>");
	table_comptes.append("<th scope=\"col\">Id du compte</th>");
        table_comptes.append("<th scope=\"col\">Intitulé</th>");
	table_comptes.append("<th scope=\"col\">Solde</th>");
	table_comptes.append("<th scope=\"col\">Options</th>");
	table_comptes.append("<th scope=\"col\"></th>");
	table_comptes.append("</thead>");
        table_comptes.append("<tbody>");

	int cpt = 1;
	for (CompteEntity account : accounts) {
	    table_comptes.append("<tr>");
	    table_comptes.append("<th scope=\"row\">"+cpt+"</th>");
	    table_comptes.append("<th scope=\"row\">"+account.getId()+"</th>");
	    table_comptes.append("<th scope=\"row\">"+account.getNom()+"</th>");
	    table_comptes.append("<th scope=\"row\">"+account.getSolde()+"</th>");
	    table_comptes.append("<th scope=\"row\"><form class=\"form\" action=\"detail.htm\" method=\"post\"><div class=\"form-group mb-3\">");
	    table_comptes.append("<input type=\"hidden\" class=\"form-control\" name=\"idCpt\" value=\""+account.getId()+"\"><button type=\"submit\">Détail</button></div></form></th>");
	    
	    if(!account.getNom().equalsIgnoreCase("Compte courant") && !account.getNom().equalsIgnoreCase("Compte pro")){
		table_comptes.append("<th scope=\"row\"><form class=\"form\" action=\"suppr_compte.htm\" method=\"post\"><div class=\"form-group mb-3\">");
		table_comptes.append("<input type=\"hidden\" class=\"form-control\" name=\"id\" value=\""+account.getId()+"\"><button type=\"submit\">Supprimer</button></div></form></th>");
	    }
	    
	    table_comptes.append("</tr>");
	    cpt++;
	}
	
	mv.addObject("table_comptes",table_comptes);
	return mv;
    }
    
    //--------------------
    @RequestMapping(value="virement", method = RequestMethod.GET)
    protected ModelAndView initVirement(HttpServletRequest request,HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return new ModelAndView("erreur");
        ModelAndView mv = new ModelAndView("virement"); 
        
        StringBuffer options = new StringBuffer();
        
	List<CompteEntity> accounts = this.service.consultation(ControllerUtils.getUserLogin(request));
        
        for (CompteEntity compte : accounts){
            options.append("<option value=\"");
            options.append(compte.getId());
            options.append("\">");
            options.append(compte.getNom());
            options.append("</option>");
        }
        
        mv.addObject("options", options);
        
        return mv;
    }    
    
    @RequestMapping(value="virement", method = RequestMethod.POST)
    protected ModelAndView virementCompte(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        String nomCompteSrc = request.getParameter("id");
	Long idCompteSrc = Long.parseLong(nomCompteSrc);
	
        String montant = request.getParameter("value");
	Double mnt = Double.parseDouble(montant);
        
	String nomDest = request.getParameter("id_dest");
	Long idCompteDest = Long.parseLong(nomDest);
	
	if(this.service.virement(idCompteSrc, idCompteDest, mnt)){
	    return this.initConsult(request, response);
	}else{
	    return new ModelAndView("erreur");
	}
    }    
    //--------------------
    @RequestMapping(value="ajout_compte", method = RequestMethod.GET)
    protected ModelAndView initAjout(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	if (!ControllerUtils.isUtilisateurConnecte(request))
	    return new ModelAndView("erreur");
	
	return new ModelAndView("ajout_compte");
    }
    
    @RequestMapping(value="ajout_compte", method = RequestMethod.POST)
    protected ModelAndView ajoutCompte(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
	
	HttpSession session = request.getSession(false);
	String login = String.valueOf(session.getAttribute("login"));

	String newAccount = request.getParameter("nom_compte");

	if(!newAccount.equalsIgnoreCase("Compte courant") && !newAccount.equalsIgnoreCase("Compte pro")){
	    if(this.service.creeCompte(newAccount, login)){
		return this.initConsult(request, response);
	    }
	}
	return new ModelAndView("erreur");
    }
    
    //----------------------
    @RequestMapping(value="suppr_compte", method = RequestMethod.POST)
    protected ModelAndView supprCompte(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	if (!ControllerUtils.isUtilisateurConnecte(request))
	    return new ModelAndView("erreur");
	
	String idCompteStr = request.getParameter("id");
	Long idCompte = Long.parseLong(idCompteStr);
	
	if(this.service.supprCompte(idCompte)){
	    return this.initConsult(request, response);
	}
	
	return new ModelAndView("erreur");
    }
    
    //------------------------    
    @RequestMapping(value="detail", method = RequestMethod.POST)
    protected ModelAndView detailCompte(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	response.setContentType("text/html;charset=UTF-8");
	if (!ControllerUtils.isUtilisateurConnecte(request))
	    return new ModelAndView("erreur");
	
	String idCompteStr = request.getParameter("idCpt");
	Long idCompte = Long.parseLong(idCompteStr);
	CompteEntity ce = this.service.getAcc(idCompte);
	
	ModelAndView mv = new ModelAndView("detail");
	StringBuffer table_comptes = new StringBuffer();
	
	table_comptes.append("<table class=\"table\">");
	table_comptes.append("<thead style=\"background-color:#ffb860;\">");
	table_comptes.append("<tr>");
	table_comptes.append("<th scope=\"col\">Compte émetteur</th>");
        table_comptes.append("<th scope=\"col\">Propriétaire</th>");
	table_comptes.append("<th scope=\"col\">Compte récepteur</th>");
        table_comptes.append("<th scope=\"col\">Propriétaire</th>");
	table_comptes.append("<th scope=\"col\">Montant</th>");
	table_comptes.append("<th scope=\"col\">Date de la transaction</th>");
	table_comptes.append("</thead>");
        table_comptes.append("<tbody>");

	for (TransactionEntity te : ce.getTransactions()) {
	    table_comptes.append("<tr>");
	    System.out.println("te : " + te + " cpt source : " + te.getCptSource() + " nom prop" + te.getCptSource().getNom());
	    table_comptes.append("<th scope=\"row\">"+te.getCptSource().getNom()+"</th>");
	    table_comptes.append("<th scope=\"row\">"+te.getCptSource().getProprietaire().getEmail()+"</th>");
	    table_comptes.append("<th scope=\"row\">"+te.getCptDest().getNom()+"</th>");
	    table_comptes.append("<th scope=\"row\">"+te.getCptDest().getProprietaire().getEmail()+"</th>");
	    table_comptes.append("<th scope=\"row\">"+te.getMontant()+"</th>");
	    table_comptes.append("<th scope=\"row\">"+te.getDate()+"</th>");
	}

	mv.addObject("table_comptes",table_comptes);
	return mv;
    }
}
