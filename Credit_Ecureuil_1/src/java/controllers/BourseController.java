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
public class BourseController {
    
    public BourseController() {
    }
    
    @RequestMapping(value="index", method = RequestMethod.GET)
    protected String init(HttpServletRequest request,HttpServletResponse response) throws Exception {
       return "index";
    }
    
    @RequestMapping(value="achat_bourse", method = RequestMethod.POST)
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
