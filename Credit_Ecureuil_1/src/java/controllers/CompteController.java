package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
       return "consultation";
    }    
    
    @RequestMapping(value="consultation", method = RequestMethod.POST)
    protected ModelAndView consultCompte(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    //--------------------
    @RequestMapping(value="virement", method = RequestMethod.GET)
    protected String initVirement(HttpServletRequest request,HttpServletResponse response) throws Exception {
       return "virement";
    }    
    
    @RequestMapping(value="virement", method = RequestMethod.POST)
    protected ModelAndView virementCompte(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }    
}
