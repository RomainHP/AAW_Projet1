/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Bourse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author etienne
 */
@WebServlet(name = "Achat", urlPatterns = {"/Achat"})
public class Achat extends HttpServlet {
    private HashMap<String, Double> hmap;
    private String fakeCompany1 = "Tesla";
    private String fakeCompany2 = "The Boring Company";
    private String fakeCompany3 = "SpaceX";
    private Double fakeValue1 = 220.47;
    private Double fakeValue2 = 117.93;
    private Double fakeValue3 = 50000.00;


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	hmap = new HashMap<>();
	hmap.put(this.fakeCompany1, this.fakeValue1);
	hmap.put(this.fakeCompany2, this.fakeValue2);
	hmap.put(this.fakeCompany3, this.fakeValue3);
	response.setContentType("text/html;charset=UTF-8");
	try (PrintWriter out = response.getWriter()) {
	    /* TODO output your page here. You may use following sample code. */
	    out.println("<!DOCTYPE html>");
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<title>Achat d'action</title>");	    
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>Toutes les actions disponibles : </h1>");
	    out.println("<form action=Achat method=POST>");
	    out.println("<select id=company name=company>");
	    for(String companyName : hmap.keySet()) {
		out.println("<option>" + companyName);
	    }
	    out.println("</select>");
	    String actionValue = request.getParameter("company");
	    out.println("<p>Valeur de l'action : </p>" + hmap.get(actionValue) +" <input type=submit VALUE=Acheter> </form>");
	    /* TODO Afficher la liste des actions disponibles */
	    out.println("</body>");
	    out.println("</html>");
	}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>

}
