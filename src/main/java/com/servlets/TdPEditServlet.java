/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.TipoPersonaJpaController;
import com.dto.TipoPersona;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TdPEditServlet", urlPatterns = {"/TdPEditServlet"})
public class TdPEditServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("Entrando a Tipo de Persona Edit Servlet");
        System.out.println(request.getParameter("editId"));
        System.out.println(request.getParameter("editDescripcion"));
        System.out.println(request.getParameter("editEstado"));
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
            TipoPersonaJpaController jpaObject = new TipoPersonaJpaController(emf);
            TipoPersona oldObjet;

//      Lo relacionado a la fecha
            Date dt = new Date();
            Timestamp ts = new Timestamp(dt.getTime());
            System.out.println(ts);

            //  Ahora necesitamos obtener el objeto a editar para chancar los nuevos valores encima
            oldObjet = jpaObject.findTipoPersona(Long.valueOf(request.getParameter("editId")));
            System.out.print("El Tipo de Persona obtenido es: "
                    + oldObjet.getId() + " - " + oldObjet.getDescripcion() + " - "
                    + oldObjet.getEstado() + " - " + oldObjet.getCreatedAt() + " - "
                    + oldObjet.getUpdatedAt());

//      Comparando y asignando nuevos valores al departamento
            if (!oldObjet.getDescripcion().equals(request.getParameter("editDescripcion"))) {
                oldObjet.setDescripcion(request.getParameter("editDescripcion"));
            }
            if (!oldObjet.getEstado().equals(request.getParameter("editEstado"))) {
                oldObjet.setEstado(request.getParameter("editEstado"));
            }
            oldObjet.setUpdatedAt(ts);

            System.out.println("El Tipo de Persona actualizado es: "
                    + oldObjet.getId() + " - " + oldObjet.getDescripcion() + " - "
                    + oldObjet.getEstado() + " - " + oldObjet.getCreatedAt() + " - "
                    + oldObjet.getUpdatedAt());

            jpaObject.edit(oldObjet);

            TdPListServlet call = new TdPListServlet();
            call.processRequest(request, response);

        } catch (Exception theException) {
            System.out.println(theException);
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
