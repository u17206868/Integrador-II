package com.servlets;

import com.controller.CitaJpaController;
import com.controller.DistritoJpaController;
import com.controller.PersonaJpaController;
import com.controller.SalaJpaController;
import com.controller.ServicioJpaController;
import com.dto.Cita;
import com.dto.Distrito;
import com.dto.Persona;
import com.dto.Sala;
import com.dto.Servicio;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CitaListServlet", urlPatterns = {"/CitaListServlet"})
public class CitaListServlet extends HttpServlet {

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

    System.out.println("Entrando al List Servlet");
    try {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

      CitaJpaController jpacCita = new CitaJpaController(emf);
      PersonaJpaController jpacPersona = new PersonaJpaController(emf);
      ServicioJpaController jpacServicio = new ServicioJpaController(emf);
      SalaJpaController jpacSala = new SalaJpaController(emf);

      List<Cita> citas = new ArrayList<>();
      List<Persona> personas = new ArrayList<>();
      List<Servicio> servicios = new ArrayList<>();
      List<Sala> salas = new ArrayList<>();

//      System.out.println(jpacontroller_object.findDistritoEntities());
      citas = jpacCita.findCitaEntities();
      personas = jpacPersona.findPersonaEntities();
      servicios = jpacServicio.findServicioEntities();
      salas = jpacSala.findSalaEntities();

      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

      for (Cita c : citas) {
        System.out.println(
                c.getId() + ": "
                + c.getSalaId().getNroSala() + ": "
                + c.getServicioId().getDescripcion() + "-----"
                + c.getFechaHora());
//                        + df.format(c.getFechaHora()));
      }
//            for (Persona p : personas) {
//                System.out.println(p.getId() + " - " + p.getNombres());
//            }


      Gson gson = new GsonBuilder().setPrettyPrinting().addSerializationExclusionStrategy(estrategia).create();
      String citasJson = gson.toJson(citas);
//            System.out.println(citasJson);
      request.setAttribute("citasJson", citasJson);

      request.setAttribute("mi_lista_de_citas", citas);
      request.setAttribute("mi_lista_de_personas", personas);
      request.setAttribute("servicios", servicios);
      request.setAttribute("salas", salas);
      //request.getRequestDispatcher("/SendEmailServlet").include(request, response);
      request.getRequestDispatcher("Cita.jsp").forward(request, response);

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
