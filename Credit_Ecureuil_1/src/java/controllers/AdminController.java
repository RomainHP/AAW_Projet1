package controllers;

import dao.compte.CompteEntity;
import dao.compte.comptejoint.CompteJointEntity;
import dao.compte.livret.LivretEntity;
import exceptions.ServiceException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import utils.ControllerUtils;

/**
 * Le controller utilisé par les utilisateur de type Admin
 *
 * @author romain
 */
@Controller
public class AdminController {

    @Autowired
    CompteController compteController;

    //---------------------------
    /**
     * Fonction de type GET utilisée pour la consultation de compte d'admin
     *
     * @return Un ModelAndView correspondant soit a la page
     * consultation_comptes_admins si autorisation correcte, soit la page erreur
     * @throws Exception
     */
    @RequestMapping(value = "consultation_comptes_admin", method = RequestMethod.GET)
    protected ModelAndView initConsultationComptesAdmin(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        if (!ControllerUtils.isUtilisateurAdmin(request)) {
            return new ModelAndView("erreur");
        }

        String login = ControllerUtils.getUserLogin(request);
        List<CompteEntity> accounts = compteController.service.getAllOpenAccounts();

        ModelAndView mv = new ModelAndView("consultation_comptes_admin");
        StringBuffer table_comptes = new StringBuffer();

        int cpt = 1;
        for (CompteEntity account : accounts) {
            table_comptes.append("<tr>");
            table_comptes.append("<td scope=\"row\">" + cpt + "</td>");
            table_comptes.append("<td scope=\"row\">" + account.getId() + "</td>");
            table_comptes.append("<td scope=\"row\">" + account.getProprietaire().getEmail() + "</td>");
            table_comptes.append("<td scope=\"row\">" + account + "</td>");
            table_comptes.append("<td scope=\"row\">" + account.getSolde() + "</td>");
            // Bouton détail
            table_comptes.append("<td scope=\"row\"><form class=\"form\" action=\"details_compte.htm\" method=\"post\"><div class=\"form-group mb-3\">");
            table_comptes.append("<input type=\"hidden\" class=\"form-control\" name=\"idCpt\" value=\"" + account.getId() + "\"><button type=\"submit\" class=\"btn btn-primary btn-md\">Détails</button></div></form></td>");

            if (account instanceof LivretEntity) {
                // Bouton supprimer
                table_comptes.append("<td scope=\"row\"><form class=\"form\" action=\"supprimer_livret_admin.htm\" method=\"post\"><div class=\"form-group mb-3\">");
                table_comptes.append("<input type=\"hidden\" class=\"form-control\" name=\"id\" value=\"" + account.getId() + "\"><button type=\"submit\" class=\"btn btn-primary btn-md\">Supprimer</button></div></form></td>");
            } else if (account instanceof CompteJointEntity && !account.getProprietaire().getEmail().equals("admin")) {
                // Bouton supprimer (on ne supprime pas le compte de l'admin)
                table_comptes.append("<td scope=\"row\"><form class=\"form\" action=\"supprimer_compte_joint_admin.htm\" method=\"post\"><div class=\"form-group mb-3\">");
                table_comptes.append("<input type=\"hidden\" class=\"form-control\" name=\"id\" value=\"" + account.getId() + "\"><button type=\"submit\" class=\"btn btn-primary btn-md\">Supprimer</button></div></form></td>");
            } else {
                table_comptes.append("<td />");
            }

            cpt++;
        }

        mv.addObject("table_comptes", table_comptes);
        return mv;
    }

    //--------------------
    /**
     * Affichage de la page ajout de livret en mode admin avec une methode POST
     *
     * @return Un ModelAndView correspondant à la page "ajout_livre_admin" avec
     * indication de si l'ajout de livret s'est faite correctement ou non
     * @throws Exception
     */
    @RequestMapping(value = "ajout_livret_admin", method = RequestMethod.POST)
    protected ResponseEntity<?> ajoutLivret(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);

            String nomCompte = jObj.getString("nom_compte");
            String utilisateur = jObj.getString("utilisateur");

            try {
                compteController.service.ajoutLivret(nomCompte, utilisateur);
                status = HttpStatus.OK;
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", "Email non valide").toString();
            }
        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //----------------------
    /**
     * Affichage de la page suppression de livret en mode admin avec une methode
     * POST
     *
     * @return Un ModelAndView correspondant à la page "supprimer_livret_admin"
     * avec indication de si la suppression de livret s'est faite correctement
     * ou non
     * @throws Exception
     */
    @RequestMapping(value = "supprimer_livret_admin", method = RequestMethod.POST)
    protected ResponseEntity<?> supprimerLivret(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);

            String idCompteStr = jObj.getString("id");
            Long idCompte = Long.parseLong(idCompteStr);

            try {
                compteController.service.supprimerLivret(idCompte, false);
                status = HttpStatus.OK;
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", "Email non valide").toString();
            }

        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //----------------------
    /**
     * Affichage de la page suppression de compte joint en mode admin avec une
     * methode POST
     *
     * @return Un ModelAndView correspondant à la page "consultation" avec
     * indication de si la suppression de compte joint s'est faite correctement
     * ou non
     * @throws Exception
     */
    @RequestMapping(value = "supprimer_compte_joint_admin", method = RequestMethod.POST)
    protected ResponseEntity<?> supprimerCompteJoint(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);

            String idCompteStr = jObj.getString("id");
            Long idCompte = Long.parseLong(idCompteStr);

            try {
                compteController.service.supprimerCompteJoint(idCompte, false);
                status = HttpStatus.OK;
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", "Email non valide").toString();
            }

        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }

    //--------------------
    /**
     * Affichage de la page virement en mode admin avec une methode GET
     *
     * @return Un ModelAndView correspondant à la page "virement"
     * @throws Exception
     */
    @RequestMapping(value = "virement_admin", method = RequestMethod.GET)
    protected ModelAndView initVirementAdmin(HttpServletRequest request, HttpServletResponse response) {
        if (!ControllerUtils.isUtilisateurConnecte(request)) {
            return new ModelAndView("erreur");
        }
        ModelAndView mv = new ModelAndView("virement");

        StringBuilder options = new StringBuilder();

        List<CompteEntity> accounts = compteController.service.getAllOpenAccounts();

        for (CompteEntity compte : accounts) {
            options.append("<option value=\"");
            options.append(compte.getId());
            options.append("\">");
            options.append(compte).append(" - ").append(compte.getProprietaire()).append(" (").append(compte.getSolde()).append("€)");
            options.append("</option>");
        }

        mv.addObject("options_dest", options.toString());
        mv.addObject("options", options.toString());
        mv.addObject("form", "virement_admin.htm");

        return mv;
    }

    /**
     * Affichage de la page virement en mode admin avec une methode POST
     *
     * @return Un ModelAndView correspondant à la page "virement" avec
     * indication de si le virement s'est fait correctement ou non
     * @throws Exception
     */
    @RequestMapping(value = "virement_admin", method = RequestMethod.POST)
    protected ResponseEntity<?> virementAdmin(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String userResponse = "[]";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        try {
            JSONObject jObj = ControllerUtils.requestToJSONObj(request);
            try {
                String nomCompteSrc = jObj.getString("id");
                Long idCompteSrc = Long.parseLong(nomCompteSrc);

                String montant = jObj.getString("value");
                Double mnt = Double.parseDouble(montant);

                String nomDest = jObj.getString("id_dest");
                Long idCompteDest = Long.parseLong(nomDest);

                compteController.service.virement(idCompteSrc, idCompteDest, mnt, false);
                status = HttpStatus.OK;
            } catch (ServiceException e) {
                userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
            } catch (Exception e) {
                userResponse = new JSONObject().put("errorMessage", "Virement incorrect").toString();
            }

        } catch (Exception e) {
            userResponse = new JSONObject().put("errorMessage", e.getMessage()).toString();
        }
        return new ResponseEntity(userResponse, status);
    }
}
