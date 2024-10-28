/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.PersonaJpaController;
import com.dto.Persona;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jasypt.util.password.BasicPasswordEncryptor;

@WebServlet(name = "PersonaLoginServlet", urlPatterns = {"/PersonaLoginServlet"})
public class PersonaLoginServlet extends HttpServlet {

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

    System.out.println("Entrando a Persona Login Servlet");
    try {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
      PersonaJpaController jpacPersona = new PersonaJpaController(emf);
//      TipoPersonaJpaController jpacTdPer = new TipoPersonaJpaController(emf);
      List<Persona> miListaDePersonas = new ArrayList<>();
      Persona miPersonaObtenida = new Persona();
//      List<TipoPersona> miListaDeTdPer = new ArrayList<>();
      int banderaLogin = 0;
      BasicPasswordEncryptor passEnc = new BasicPasswordEncryptor();
      HttpSession sesion = request.getSession();

//      System.out.println(jpacontroller_object.findDistritoEntities());
      miListaDePersonas = jpacPersona.findPersonaEntities();
//      miListaDeTdPer = jpacTdPer.findTipoPersonaEntities();
      for (Persona per : miListaDePersonas) {
        System.out.println(per.getId() + ": " + per.getNombres() + ": " + per.getEmail() + ": " + per.getPassword() + ": " + per.getTipoPersonaId().getDescripcion());
        if (per.getEmail().equals(request.getParameter("loginEmail")) && passEnc.checkPassword(request.getParameter("loginPassword"), per.getPassword())) {
          miPersonaObtenida = per;
          banderaLogin = 1;
        }
      }
      if (banderaLogin == 1) {

//        request.setAttribute("miPersonaObtenida", miPersonaObtenida);
        sesion.setAttribute("miPersonaObtenida", miPersonaObtenida);
        request.getRequestDispatcher("index.jsp").forward(request, response);

//        request.getRequestDispatcher("/NivelUsuarioServlet").include(request, response);

      } else {
        response.sendRedirect("auth/login.jsp");
      }
    } catch (IOException theException) {
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
