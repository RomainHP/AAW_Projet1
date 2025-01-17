package controllers;

import dao.entreprise.EntrepriseEntity;
import dao.utilisateur.UtilisateurEntity;
import dao.utilisateur.pro.UtilisateurProEntity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.bourse.BourseService;
import services.utilisateur.UtilisateurService;
import utils.ControllerUtils;

/**
 * Le controller utilisé pour la gestion de l'ordre en bourse
 */
@Controller
public class BourseController {
    
    @Autowired
    BourseService service;
    
    @Autowired
    UtilisateurService userService;
    
    //------------------
    /**
     * Affichage de la page ordre en bourse en method GET
     * @return ModelAndView correspondant à la page "ordre_bourse"
     */
    @RequestMapping(value="ordre_bourse", method = RequestMethod.GET)
    protected ModelAndView initBourse(HttpServletRequest request,HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request)) return new ModelAndView("erreur");
        if (!ControllerUtils.isUtilisateurPro(request)) return new ModelAndView("erreur");
        
        ModelAndView mv = new ModelAndView("ordre_bourse");
        String login = ControllerUtils.getUserLogin(request);
        
        // Récupération de l'entreprise
        UtilisateurEntity user = userService.getUtilisateur(login);
        if (user!=null && user instanceof UtilisateurProEntity){
            UtilisateurProEntity userPro = (UtilisateurProEntity)user;
            EntrepriseEntity entreprise = userPro.getEntreprise();
            mv.addObject("entreprise", entreprise.toString());
        }
        
        // Récupération des actions en vente
        
        return mv;
    }
    
    /**
     * Affichage de la page ordre en bourse en method POST
     * @return ModelAndView correspondant à la page "ordre_bourse"
     */
    @RequestMapping(value="ordre_bourse", method = RequestMethod.POST)
    public ModelAndView bourse(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
