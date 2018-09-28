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
public class CommunicationController {
    
    public CommunicationController() {
    }
    
    
    @RequestMapping(value="index", method = RequestMethod.GET)
    protected String initIndex(HttpServletRequest request,HttpServletResponse response) throws Exception {
       return "index";
    }
    
    //----------------------------
    
    @RequestMapping(value="messagerie", method = RequestMethod.GET)
    protected String initMessagerie(HttpServletRequest request,HttpServletResponse response) throws Exception {
       return "messagerie";
    }    
    
    @RequestMapping(value="messagerie", method = RequestMethod.POST)
    public ModelAndView serviceMessagerie(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    //-----------------------------
    @RequestMapping(value="notifications", method = RequestMethod.GET)
    protected String initNotif(HttpServletRequest request,HttpServletResponse response) throws Exception {
       return "notifications";
    }    
    
    @RequestMapping(value="notifications", method = RequestMethod.POST)
    public ModelAndView notifications(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }    
}
