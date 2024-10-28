/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.DistritoJpaController;
import com.controller.PersonaJpaController;
import com.controller.TipoPersonaJpaController;
import com.dto.Distrito;
import com.dto.Persona;
import com.dto.TipoPersona;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jasypt.util.password.BasicPasswordEncryptor;

@WebServlet(name = "PersonaEditServlet", urlPatterns = {"/PersonaEditServlet"})
public class PersonaEditServlet extends HttpServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

    System.out.println("--------------------Entrando a Persona Edit Servlet--------------------");
    //Obteniendo todos los parámetros que recibimos de la vista; solo para saber con qué variables llegan
    System.out.println(request.getParameterMap());
    for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
      for (String s : e.getValue()) {
        System.out.println("Key: " + e.getKey() + " ForValue: " + s);
      }
    }
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
    try {
//      Inicialización de objetos
      PersonaJpaController jpacPersona = new PersonaJpaController(emf);
      TipoPersonaJpaController jpacTdPersona = new TipoPersonaJpaController(emf);
      DistritoJpaController jpacDistrito = new DistritoJpaController(emf);
      Persona oldPersona;
      TipoPersona TdPersona;
      Distrito distrito;
      BasicPasswordEncryptor passEnc = new BasicPasswordEncryptor();

//      Lo relacionado a la fecha
      Date dt = new Date();
      Timestamp ts = new Timestamp(dt.getTime());
      System.out.println(ts);

//      Obteniendo el objeto con foreign key en base al Id que nos da la vista
      TdPersona = jpacTdPersona.findTipoPersona(Long.valueOf(request.getParameter("editTdPersonaId")));
      distrito = jpacDistrito.findDistrito(Long.valueOf(request.getParameter("editDistritoId")));

      //  Ahora necesitamos obtener el objeto a editar para chancar los nuevos valores encima
      oldPersona = jpacPersona.findPersona(Long.valueOf(request.getParameter("editId")));

      String test = request.getParameter("editPassword");
      System.out.println("La Persona obtenida es: " + oldPersona);
      System.out.println("------------------------------Contrasenia de la vista: " + test);
      System.out.println("------------------------------Contrasenia de la vista encriptada: " + passEnc.encryptPassword(request.getParameter("editPassword")));
      System.out.println("------------------------------Contrasenia de la DB: " + oldPersona.getPassword());
      System.out.println("Comprobación: " + passEnc.checkPassword(test, passEnc.encryptPassword(request.getParameter("editPassword"))));

//      Comparando y asignando nuevos valores al objeto
      if (oldPersona.getNombres() == null || !oldPersona.getNombres().equals(request.getParameter("editNombres"))) {
        oldPersona.setNombres(request.getParameter("editNombres"));
      }
      if (oldPersona.getApellidos() == null || !oldPersona.getApellidos().equals(request.getParameter("editApellidos"))) {
        oldPersona.setApellidos(request.getParameter("editApellidos"));
      }
      if (oldPersona.getDni() == null || !oldPersona.getDni().equals(request.getParameter("editDni"))) {
        oldPersona.setDni(request.getParameter("editDni"));
      }
      if (oldPersona.getTelefono() == null || !oldPersona.getTelefono().equals(request.getParameter("editTelefono"))) {
        oldPersona.setTelefono(request.getParameter("editTelefono"));
      }
      if (oldPersona.getDireccion() == null || !oldPersona.getDireccion().equals(request.getParameter("editDireccion"))) {
        oldPersona.setDireccion(request.getParameter("editDireccion"));
      }
      if (oldPersona.getReferencia() == null || !oldPersona.getReferencia().equals(request.getParameter("editReferencia"))) {
        oldPersona.setReferencia(request.getParameter("editReferencia"));
      }
      if (oldPersona.getEmail() == null || !oldPersona.getEmail().equals(request.getParameter("editEmail"))) {
        oldPersona.setEmail(request.getParameter("editEmail"));
      }
      if (oldPersona.getPassword() == null) {
        oldPersona.setPassword(passEnc.encryptPassword("123456"));
      } else {
        if (!request.getParameter("editPassword").equalsIgnoreCase("")) {
          if (!passEnc.checkPassword(request.getParameter("editPassword"), oldPersona.getPassword())) {
            oldPersona.setPassword(passEnc.encryptPassword(request.getParameter("editPassword")));
          }
        }
      }

//            if (oldPersona.getTurno() == null || !oldPersona.getTurno().equals(request.getParameter("editTurno"))) {
//                oldPersona.setTurno(request.getParameter("editTurno"));
//            }
      if (!oldPersona.getTipoPersonaId().equals(TdPersona)) {
        oldPersona.setTipoPersonaId(TdPersona);
      }
      if (!oldPersona.getDistritoId().equals(distrito)) {
        oldPersona.setDistritoId(distrito);
      }
      if (oldPersona.getEstado() == null || !oldPersona.getEstado().equals(request.getParameter("editEstado"))) {
        oldPersona.setEstado(request.getParameter("editEstado"));
      }

      oldPersona.setUpdatedAt(ts);
//      oldObject_distrito.setDireccionCollection(mi_lista_de_Direcciones);

      System.out.println("La Persona actualizada es: "
              + oldPersona.getId() + ": " + oldPersona.getNombres() + ": "
              + oldPersona.getEstado() + ": " + oldPersona.getTipoPersonaId().getDescripcion() + ": "
              + oldPersona.getCreatedAt() + ": " + oldPersona.getUpdatedAt());

      jpacPersona.edit(oldPersona);

      PersonaListServlet call = new PersonaListServlet();
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
