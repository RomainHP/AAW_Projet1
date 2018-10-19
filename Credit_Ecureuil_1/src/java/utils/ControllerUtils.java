package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rcharpen
 */
public class ControllerUtils {
    
    /**
     * Permet de tester si l'utilisateur est connecté ou non sur le site
     * @param request
     * @return vrai si l'utilisateur est connecté
     */
    public static boolean isUtilisateurConnecte(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return (session!=null && session.getAttribute("login")!=null);
    }

    /**
     * Renvoie vrai si l'utilisateur est de la catégorie pro
     * @param request
     * @return vrai si l'utilisateur est de la catégorie pro
     */
    public static boolean isUtilisateurPro(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session!=null && session.getAttribute("pro")!=null &&(boolean)session.getAttribute("pro"));
    }
    
    /**
     * Retourne vrai si l'email est valide
     * @param email
     * @return vrai si l'email est valide
     */
    public static boolean testEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    }
    
    /**
     * Retourne le login de l'utilisateur qui est stocké en variable de session
     * @param request
     * @return le login de l'utilisateur qui est stocké en variable de session
     */
    public static String getUserLogin(HttpServletRequest request){
        String login = null;
        HttpSession session = request.getSession();
        if (session!=null) login = String.valueOf(session.getAttribute("login"));
        return login;
    }
}
