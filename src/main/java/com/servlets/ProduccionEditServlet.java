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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProduccionEditServlet", urlPatterns = {"/ProduccionEditServlet"})
public class ProduccionEditServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

        PersonaJpaController jpacPersona = new PersonaJpaController(emf);
        ServicioJpaController jpacServicio = new ServicioJpaController(emf);
        SalaJpaController jpacSala = new SalaJpaController(emf);
        ProduccionJpaController jpacProduccion = new ProduccionJpaController(emf);

        try {
            String fechaHoraInicioJunta = "";
            String fechaHoraFinJunta = "";
            SimpleDateFormat sdf_fecha = new SimpleDateFormat("yyyy-MM-dd");
            fechaHoraInicioJunta = request.getParameter("fechaInicio") + " " + request.getParameter("fechaHora");
            fechaHoraFinJunta = request.getParameter("fechaEnd") + " " + request.getParameter("fechaHoraEnd");

            Produccion objProd = new Produccion();
            String descripcion = request.getParameter("descripcion");
            Double medida = Double.parseDouble(request.getParameter("medida"));
            String color = request.getParameter("color");
            String fechaHora = fechaHoraInicioJunta;
            String fechaHoraEnd = fechaHoraFinJunta;
            String estado = request.getParameter("estado");
            Long id = Long.parseLong(request.getParameter("id"));
            Long salaId = Long.parseLong(request.getParameter("salas"));
            Long servicioId = Long.parseLong(request.getParameter("servicioId"));
            Long tecnicoId = Long.parseLong(request.getParameter("tecnicoId"));

            Sala sala = jpacSala.findSala(salaId);
            Servicio servicio = jpacServicio.findServicio(servicioId);
            Persona tecnico = jpacPersona.findPersona(tecnicoId);

            Produccion produccion = jpacProduccion.findProduccion(id);
            produccion.setDescripcion(descripcion);
            produccion.setMedida(medida);
            produccion.setColor(color);
            produccion.setFechaHora(fechaHora);
            produccion.setFechaHoraEnd(fechaHoraEnd);
            produccion.setEstado(estado);
            produccion.setSalaId(sala);
            produccion.setServicioId(servicio);
            produccion.setTecnicoId(tecnico);
            produccion.setUpdatedAt(new Date());
            jpacProduccion.edit(produccion);
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
