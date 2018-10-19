package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.utilisateur.UtilisateurService;
import services.utilisateur.UtilisateurServiceImpl;
import utils.ControllerUtils;

/**
 *
 * @author rcharpen
 */
@Controller
public class BourseController {
    
    //------------------
    @RequestMapping(value="ordre_bourse", method = RequestMethod.GET)
    protected String initBourse(HttpServletRequest request,HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return "erreur";
        if (!ControllerUtils.isUtilisateurPro(request)) return "erreur";
        return "ordre_bourse";
    }
    
    @RequestMapping(value="ordre_bourse", method = RequestMethod.POST)
    public ModelAndView bourse(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
