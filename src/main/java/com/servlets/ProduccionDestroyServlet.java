package com.servlets;

import com.controller.PersonaJpaController;
import com.controller.ProduccionJpaController;
import com.controller.SalaJpaController;
import com.controller.ServicioJpaController;
import com.dto.Persona;
import com.dto.Produccion;
import com.dto.Sala;
import com.dto.Servicio;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProduccionDestroyServlet", urlPatterns = {"/ProduccionDestroyServlet"})
public class ProduccionDestroyServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

        ProduccionJpaController jpacProduccion = new ProduccionJpaController(emf);

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            jpacProduccion.destroy(id);
        } catch (Exception ex) {
        }

        response.sendRedirect("ProduccionListServlet");
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
