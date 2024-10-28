/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.CategoriaJpaController;
import com.controller.ServicioJpaController;
import com.dto.Categoria;
import com.dto.Servicio;
import java.io.IOException;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServicioCreateServlet", urlPatterns = {"/ServicioCreateServlet"})
public class ServicioCreateServlet extends HttpServlet {

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
    
    System.out.println("Bandera servlet create Servicio");
    
    System.out.println(request.getParameterMap());
    for (Map.Entry<String, String[]> key : request.getParameterMap().entrySet()) {
      for (String value : key.getValue()) {
        System.out.println("Key: " + key.getKey() + " - Value: " + value);
      }
    }
    try {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

      ServicioJpaController jpacServicio = new ServicioJpaController(emf);
      CategoriaJpaController jpacCategoria = new CategoriaJpaController(emf);
      
      Servicio miServicio = new Servicio();
      Categoria miCategoria = new Categoria();

//      Date dt = new Date();
//      Timestamp ts = new Timestamp(dt.getTime());
//      System.out.println(ts);
//      
//      Obteniendo el departamento en base al Id obtenido de la vista
      miCategoria = jpacCategoria.findCategoria(Long.valueOf(request.getParameter("addCategoriaId")));
      System.out.println("El objeto obtenido fue: " + miCategoria.getDescripcion() +" - "+ miCategoria.getId());

//      Llenando los parámetros del distrito obtenidos de la vista
//            mi_distrito.setIdTelefono(566);                        //No necesario, tiene auto_increment
      miServicio.setUniqueId(String.valueOf(java.util.UUID.randomUUID()));
      miServicio.setDescripcion(request.getParameter("addDescripcion"));
      miServicio.setDetalles(request.getParameter("addDetalles"));
      miServicio.setMinutos(Integer.valueOf(request.getParameter("addMinutos")));
      miServicio.setPrecio(Double.valueOf(request.getParameter("addPrecio")));
      miServicio.setEstado("activo");
      miServicio.setCategoriaId(miCategoria);
//      miServicio.setCreatedAt(ts);
//      miServicio.setUpdatedAt(ts);

//      Llamando al método crear del controlador y pasándole el objeto Distrito
      jpacServicio.create(miServicio);

//      Llamando al listALGO.jsp
      ServicioListServlet call = new ServicioListServlet();
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
