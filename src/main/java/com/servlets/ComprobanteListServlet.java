/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.ComprobanteJpaController;
import com.controller.PersonaJpaController;
import com.dto.Comprobante;
import com.dto.Persona;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ComprobanteListServlet", urlPatterns = {"/ComprobanteListServlet"})
public class ComprobanteListServlet extends HttpServlet {

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

    System.out.println("Entrando a Factura List Servlet");
    try {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
      
      ComprobanteJpaController jpacComprobante = new ComprobanteJpaController(emf);
      PersonaJpaController jpacPersona = new PersonaJpaController(emf);
      
      List<Comprobante> comprobantes = new ArrayList<>();
      List<Persona> personas = new ArrayList<>();

//      System.out.println(jpacontroller_object.findDistritoEntities());
      comprobantes = jpacComprobante.findComprobanteEntities();
      personas = jpacPersona.findPersonaEntities();

      DateTimeFormatter myFormatFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//      LocalDateTime mi_fecha = LocalDateTime.now();
      for (Comprobante temp1 : comprobantes) {
        temp1.setFecha(temp1.getFecha());
        System.out.println(temp1.getId() + " - " + temp1.getNumero());
      }
      for (Persona temp2 : personas) {
        System.out.println(temp2.getId() + " - " + temp2.getNombres());
      }

      request.setAttribute("mi_lista_de_comprobantes", comprobantes);
      request.setAttribute("mi_lista_de_personas", personas);
      request.getRequestDispatcher("Comprobante.jsp").forward(request, response);

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
