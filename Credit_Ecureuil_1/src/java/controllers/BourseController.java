package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class BourseController {
    
    public BourseController() {
    }
    
    //------------------
    @RequestMapping(value="achat_bourse", method = RequestMethod.GET)
    protected String initAchat(HttpServletRequest request,HttpServletResponse response) throws Exception {
        if (!ControllerUtils.utilisateurConnecte(request)) return "erreur";
        return "achat_bourse";
    }
    
    @RequestMapping(value="achat_bourse", method = RequestMethod.POST)
    public ModelAndView achatAction(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    //-------------------
    
    @RequestMapping(value="vente_bourse", method = RequestMethod.GET)
    protected String initVente(HttpServletRequest request,HttpServletResponse response) throws Exception {
        if (!ControllerUtils.utilisateurConnecte(request)) return "erreur";
        return "vente_bourse";
    }
    
    @RequestMapping(value="vente_bourse", method = RequestMethod.POST)
    public ModelAndView venteAction(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
