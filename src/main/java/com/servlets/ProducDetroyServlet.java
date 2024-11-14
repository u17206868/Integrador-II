/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.ProduccionJpaController;
import com.dto.Produccion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HP
 */
@WebServlet(name = "ProducDetroyServlet", urlPatterns = {"/ProducDetroyServlet"})
public class ProducDetroyServlet extends HttpServlet {

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
        System.out.println("Entrando a Produccion Destroy Servlet");
        System.out.println(request.getParameterMap());
    for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
      System.out.println("Key: " + e.getKey());
      for (String s : e.getValue()) {
        System.out.println("ForValue: " + s);
      }
    }
        System.out.println(request.getParameter("destroyId"));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

        try {
            ProduccionJpaController jpacObject = new ProduccionJpaController(emf);
            Produccion objeto_archivado;
            
            objeto_archivado = jpacObject.findProduccion(Long.valueOf(request.getParameter("destroyId")));

            
            objeto_archivado.setEstado("eliminado");
             jpacObject.edit(objeto_archivado);
             
           ProducListServlet call = new ProducListServlet();
          call.processRequest(request, response);
                 

        }catch (Exception theException) {
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
