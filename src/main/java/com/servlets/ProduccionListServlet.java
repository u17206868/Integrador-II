/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.CategoriaJpaController;
import com.controller.PersonaJpaController;
import com.controller.ProduccionJpaController;
import com.controller.SalaJpaController;
import com.controller.ServicioJpaController;
import com.dto.Categoria;
import com.dto.Persona;
import com.dto.Produccion;
import com.dto.Sala;
import com.dto.Servicio;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProduccionListServlet", urlPatterns = {"/ProduccionListServlet"})
public class ProduccionListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ExclusionStrategy estrategia = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }

            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getName().contains("List");
            }
        };
        try (PrintWriter out = response.getWriter()) {

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

            PersonaJpaController jpacPersona = new PersonaJpaController(emf);
            ServicioJpaController jpacServicio = new ServicioJpaController(emf);
            SalaJpaController jpacSala = new SalaJpaController(emf);
            ProduccionJpaController jpacProduccion = new ProduccionJpaController(emf);

            List<Persona> personas = new ArrayList<>();
            List<Servicio> servicios = new ArrayList<>();
            List<Sala> salas = new ArrayList<>();
            List<Persona> tecnicos = new ArrayList<>();
            List<Produccion> producciones = new ArrayList<>();

            personas = jpacPersona.findPersonaEntities();
            servicios = jpacServicio.findServicioEntities();
            salas = jpacSala.findSalaEntities();
            producciones = jpacProduccion.findProduccionEntities();
            tecnicos = jpacPersona.findPersonaPorTipo(5);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            for (Produccion p : producciones) {
                System.out.println(
                        p.getId() + ": "
                        + p.getSalaId().getNroSala() + ": "
                        + p.getServicioId().getDescripcion() + "-----"
                        + p.getFechaHora() + ".."
                        + p.getFechaHoraEnd());
//                        + df.format(c.getFechaHora()));
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().addSerializationExclusionStrategy(estrategia).create();
            String produccionesJson = gson.toJson(producciones);

            request.setAttribute("mi_lista_de_personas", personas);
            request.setAttribute("servicios", servicios);
            request.setAttribute("salas", salas);
            request.setAttribute("tecnicos", tecnicos);
            request.setAttribute("mi_lista_de_producciones", producciones);
            request.setAttribute("produccionesJson", produccionesJson);
            request.getRequestDispatcher("Produccion.jsp").forward(request, response);

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
