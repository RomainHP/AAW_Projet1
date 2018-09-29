package controllers;

import dao.compte.CompteEntity;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import utils.ControllerUtils;

/**
 *
 * @author rcharpen
 */
@Controller
public class CompteController {
    
    public CompteController() {
    }
    
    //----------------------
    @RequestMapping(value="consultation", method = RequestMethod.GET)
    protected String initConsult(HttpServletRequest request,HttpServletResponse response) throws Exception {
        if (!ControllerUtils.utilisateurConnecte(request)) return "erreur";
        return "consultation";
    }
    
    //--------------------
    @RequestMapping(value="virement", method = RequestMethod.GET)
    protected ModelAndView initVirement(HttpServletRequest request,HttpServletResponse response) throws Exception {
        if (!ControllerUtils.utilisateurConnecte(request)) return new ModelAndView("erreur");
        ModelAndView mv = new ModelAndView("virement"); 
        
        response.setContentType("text/html;charset=UTF-8");
        
        StringBuffer options = new StringBuffer();
        
        //TODO remplacer par la base
        List<CompteEntity> list = new ArrayList<CompteEntity>();
        list.add(new CompteEntity(1l,"test"));
        list.add(new CompteEntity(2l,"bidule"));
        
        for (CompteEntity compte : list){
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
        ModelAndView mv = new ModelAndView("index"); 
        
        response.setContentType("text/html;charset=UTF-8");
        
        String id = request.getParameter("id");
        String montant = request.getParameter("value");
        String idDest = request.getParameter("id_dest");
        return null;
    }    
}
