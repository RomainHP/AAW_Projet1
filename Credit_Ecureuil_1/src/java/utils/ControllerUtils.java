/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

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
        boolean result = true;
        try {
           InternetAddress emailAddr = new InternetAddress(email);
           emailAddr.validate();
        } catch (AddressException ex) {
           result = false;
        }
        return result;
    }
}
