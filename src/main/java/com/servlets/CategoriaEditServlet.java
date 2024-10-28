/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.CategoriaJpaController;
import com.dto.Categoria;
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

@WebServlet(name = "CategoriaEditServlet", urlPatterns = {"/CategoriaEditServlet"})
public class CategoriaEditServlet extends HttpServlet {

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

    System.out.println("Entrando a Categoría Edit Servlet");
    System.out.println(request.getParameter("editId"));
    System.out.println(request.getParameter("editDescripcion"));
    System.out.println(request.getParameter("editEstado"));
    try {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
      CategoriaJpaController jpacCategoria = new CategoriaJpaController(emf);
      Categoria oldCategoria;

//      Lo relacionado a la fecha
      Date dt = new Date();
      Timestamp ts = new Timestamp(dt.getTime());
      System.out.println(ts);

      //  Ahora necesitamos obtener el objeto a editar para chancar los nuevos valores encima
      oldCategoria = jpacCategoria.findCategoria(Long.valueOf(request.getParameter("editId")));
      System.out.println("La Categoría obtenida es: " + oldCategoria);

//      Comparando y asignando nuevos valores al departamento
      if (!oldCategoria.getDescripcion().equals(request.getParameter("editDescripcion"))) {
        oldCategoria.setDescripcion(request.getParameter("editDescripcion"));
      }
      if (!oldCategoria.getEstado().equals(request.getParameter("editEstado"))) {
        oldCategoria.setEstado(request.getParameter("editEstado"));
      }

      oldCategoria.setUpdatedAt(ts);

      //Por alguna razón ya no lo necesita
      //  viejo_objeto_departamento.setDistritoCollection(mi_lista_de_Distritos);
      System.out.println("La Categoria actualizada es: "
              + oldCategoria.getId() + " - " + oldCategoria.getDescripcion() + " - "
              + oldCategoria.getEstado() + " - " + oldCategoria.getCreatedAt() + " - "
              + oldCategoria.getUpdatedAt() + " - " + oldCategoria.getServicioList());

      jpacCategoria.edit(oldCategoria);

      CategoriaListServlet call = new CategoriaListServlet();
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
