package com.servlets;

import com.controller.CitaJpaController;
import com.controller.PersonaJpaController;
import com.controller.SalaJpaController;
import com.controller.ServicioJpaController;
import com.dto.Cita;
import com.dto.Persona;
import com.dto.Sala;
import com.dto.Servicio;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CitaCreateServlet", urlPatterns = {"/CitaCreateServlet"})
public class CitaCreateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        System.out.println("Bandera servlet create Cita");
        try {
            //Obteniendo todos los parámetros que recibimos de la vista; solo para saber con qué variables llegan
            System.out.println(request.getParameterMap());
            for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
                for (String s : e.getValue()) {
                    System.out.println("Key: " + e.getKey() + "--------Value: " + s);
                }
            }

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

            CitaJpaController jpacCita = new CitaJpaController(emf);
            PersonaJpaController jpacPersona = new PersonaJpaController(emf);
            ServicioJpaController jpacServicio = new ServicioJpaController(emf);
            SalaJpaController jpacSala = new SalaJpaController(emf);

            Cita cita = new Cita();
            Persona p_tecnico = new Persona();
            Persona p_cliente = new Persona();
            Servicio servicio = new Servicio();
            Sala sala = new Sala();

//      Obteniendo el objeto en base al Id obtenido de la vista
            p_tecnico = jpacPersona.findPersona(Long.valueOf(request.getParameter("addTecnicoId")));
            System.out.println("Técnico obtenido: " + p_tecnico.getNombres() + ", " + p_tecnico.getApellidos() + ", " + p_tecnico.getTipoPersonaId().getDescripcion());
            p_cliente = jpacPersona.findPersona(Long.valueOf(request.getParameter("addClienteId")));
            System.out.println("Cliente obtenido: " + p_cliente.getNombres() + ", " + p_cliente.getApellidos() + ", " + p_cliente.getTipoPersonaId().getDescripcion());
            servicio = jpacServicio.findServicio(Long.valueOf(request.getParameter("addServicioId")));
            System.out.println("Servicio obtenido: " + servicio.getMinutos() + ", " + servicio.getPrecio() + "," + servicio.getDescripcion());
            sala = jpacSala.findSala(Long.valueOf(request.getParameter("addSalaId")));
//
//      mi_objeto_persona = jpac_obj_persona.findPersona(Long.valueOf(request.getParameter("addApellidoId")));
//      System.out.println("El apellido obtenido fue: " + mi_objeto_vehiculo.getPlaca() + " - " + mi_objeto_vehiculo.getModelo());

            cita.setUniqueId(String.valueOf(java.util.UUID.randomUUID()));
            cita.setFechaHora(request.getParameter("addFecha") + " " + request.getParameter("addHora"));
            cita.setFechaHoraEnd(request.getParameter("addFechaEnd") + " " + request.getParameter("addHoraEnd"));
//      cita.setHora(request.getParameter("addHora"));
            cita.setClienteId(p_cliente);
            cita.setTecnicoId(p_tecnico);
            cita.setServicioId(servicio);
            cita.setSalaId(sala);
            cita.setEstado("activo");
//      mi_cita.setCreatedAt(ts);
//      mi_cita.setUpdatedAt(ts);

//      Llamando al método crear del controlador y pasándole el objeto Distrito
            jpacCita.create(cita);

//      Llamando al listALGO servlet
            VentaListServlet call = new VentaListServlet();
            call.processRequest(request, response);
//      response.sendRedirect("Distrito/List.jsp").forward(request, response);

        } catch (IOException | ServletException theException) {
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
