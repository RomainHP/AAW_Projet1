package utils;

import java.util.regex.Pattern;
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
     * Renvoie vrai si l'utilisateur est admin
     * @param request
     * @return vrai si l'utilisateur est admin
     */
    public static boolean isUtilisateurAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session!=null && session.getAttribute("admin")!=null &&(boolean)session.getAttribute("admin"));
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
    
    /**
     * Genere le message d'erreur sous format HTML
     * @param error
     * @return message d'erreur sous format HTML
     */
    public static String generateErrorMessage(String error){
        StringBuffer error_message = new StringBuffer();
        error_message.append("<div class=\"alert alert-danger\">");
        error_message.append("<strong>Erreur ! </strong>");
        error_message.append(error);
        error_message.append("</div>");
        return error_message.toString();
    }
    
    /**
     * Genere le message de succes sous format HTML
     * @param error
     * @return message de succes sous format HTML
     */
    public static String generateSuccessMessage(String success){
        StringBuffer error_message = new StringBuffer();
        error_message.append("<div class=\"alert alert-success\">");
        error_message.append("<strong>Succès ! </strong>");
        error_message.append(success);
        error_message.append("</div>");
        return error_message.toString();
    }
}
