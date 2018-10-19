package controllers;

import dao.compte.CompteEntity;
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
import services.compte.CompteServiceImpl;
import utils.ControllerUtils;

/**
 *
 * @author rcharpen
 */
@Controller
public class CompteController {
    
    @Autowired
    CompteService service;
    
    //----------------------
    @RequestMapping(value="consultation", method = RequestMethod.GET)
    protected ModelAndView initConsult(HttpServletRequest request,HttpServletResponse response) throws Exception {
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
        table_comptes.append("<th scope=\"col\">Intitulé</th>");
	table_comptes.append("<th scope=\"col\">Solde</th>");
	table_comptes.append("</thead>");
        table_comptes.append("<tbody>");

	int cpt = 1;
	for (CompteEntity account : accounts) {
	    table_comptes.append("<tr>");
	    table_comptes.append("<th scope=\"row\">"+cpt+"</th>");
	    table_comptes.append("<th scope=\"row\">"+account.getNom()+"</th>");
	    table_comptes.append("<th scope=\"row\">"+account.getSolde()+"</th>");
	    table_comptes.append("</tr>");
	    cpt++;
	}
	
	mv.addObject("table_comptes",table_comptes);
	return mv;
    }
    
    //--------------------
    @RequestMapping(value="virement", method = RequestMethod.GET)
    protected ModelAndView initVirement(HttpServletRequest request,HttpServletResponse response) throws Exception {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return new ModelAndView("erreur");
        ModelAndView mv = new ModelAndView("virement"); 
        
        response.setContentType("text/html;charset=UTF-8");
        
        StringBuffer options = new StringBuffer();
        
	HttpSession session = request.getSession(false);
	String login = String.valueOf(session.getAttribute("login"));
	List<CompteEntity> accounts = this.service.consultation(login);
        
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
        ModelAndView mv; 
        
        response.setContentType("text/html;charset=UTF-8");
        
        String nomCompteSrc = request.getParameter("id");
	Long idCompteSrc = Long.parseLong(nomCompteSrc);
	
        String montant = request.getParameter("value");
	Double mnt = Double.parseDouble(montant);
        
	String nomDest = request.getParameter("id_dest");
	Long idCompteDest = Long.parseLong(nomDest);
	
	boolean toot = this.service.virement(idCompteSrc, idCompteDest, mnt);
	System.out.println(toot);
	if(toot){
	    return this.initConsult(request, response);
	}else{
	    return new ModelAndView("erreur");
	}
    }    
}
