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

@WebServlet(name = "ServicioEditServlet", urlPatterns = {"/ServicioEditServlet"})
public class ServicioEditServlet extends HttpServlet {

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
    
    System.out.println("Entrando a Servicio Edit Servlet");
    
    System.out.println(request.getParameterMap());
    for (Map.Entry<String, String[]> key : request.getParameterMap().entrySet()) {
      for (String value : key.getValue()) {
        System.out.println("Key: " + key.getKey() + " - Value: " + value);
      }
    }
    
    System.out.println(request.getParameter("editId"));
    System.out.println(request.getParameter("editDescripcion"));
    System.out.println(request.getParameter("editDetalles"));
    System.out.println(request.getParameter("editPrecio"));
    System.out.println(request.getParameter("editEstado"));
    System.out.println(request.getParameter("editCategoriaId"));
    try {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

//      Inicialización de objetos
      ServicioJpaController jpacServicio = new ServicioJpaController(emf);
      CategoriaJpaController jpacCategoria = new CategoriaJpaController(emf);
      Servicio oldServicio;
      Categoria categoria;

//      Lo relacionado a la fecha
      Date dt = new Date();
      Timestamp ts = new Timestamp(dt.getTime());
      System.out.println(ts);

//      Necesitamos una lista de los Direcciones
//      DireccionJpaController jpac_xa_lista_de_Direcciones = new DireccionJpaController();
//      List<Direccion> mi_lista_de_Direcciones = new ArrayList<>();
//      mi_lista_de_Direcciones = jpac_xa_lista_de_Direcciones.findDireccionEntities();

//      Obteniendo el objeto con foreign key en base al Id que nos da la vista
      categoria = jpacCategoria.findCategoria(Long.valueOf(request.getParameter("editCategoriaId")));

      //  Ahora necesitamos obtener el objeto a editar para chancar los nuevos valores encima
      oldServicio = jpacServicio.findServicio(Long.valueOf(request.getParameter("editId")));
      System.out.println("El Servicio obtenido es: " + oldServicio);

      
//      if(oldObject_servicio.getDescripcion().isBlank()){
//        System.out.println("Descripción vacía o con espacios en blanco");
//      }
      
//      Comparando y asignando nuevos valores al objeto
      if (oldServicio.getDescripcion() == null || !oldServicio.getDescripcion().equals(request.getParameter("editDescripcion"))) {
        oldServicio.setDescripcion(request.getParameter("editDescripcion"));
      }
      if (oldServicio.getDetalles() == null || !oldServicio.getDetalles().equals(request.getParameter("editDetalles"))) {
        
        oldServicio.setDetalles(request.getParameter("editDetalles"));
      }
      
//      BigDecimal old = new BigDecimal(oldObject_servicio.getPrecio());
//      BigDecimal nuevo = new BigDecimal(request.getParameter("editPrecio"));
      
      if(oldServicio.getPrecio() == null || oldServicio.getPrecio().compareTo(Double.valueOf(request.getParameter("editPrecio"))) != 0){
        oldServicio.setPrecio(Double.valueOf(request.getParameter("editPrecio")));
      }
      
//      if(old.compareTo(nuevo) != 0){
//        oldObject_servicio.setPrecio(Double.valueOf(request.getParameter("editPrecio")));
//      }
//      if (oldObject_servicio.getPrecio().compareTo(Double.valueOf(request.getParameter("editPrecio")))!=0) {
//        oldObject_servicio.setPrecio(Double.valueOf(request.getParameter("editPrecio")));
//      }
      if (oldServicio.getEstado() == null || !oldServicio.getEstado().equals(request.getParameter("editEstado"))) {
        oldServicio.setEstado(request.getParameter("editEstado"));
      }
      if (!oldServicio.getCategoriaId().equals(categoria)) {
        oldServicio.setCategoriaId(categoria);
      }
      oldServicio.setUpdatedAt(ts);
//      oldObject_distrito.setDireccionCollection(mi_lista_de_Direcciones);

      System.out.println("El Servicio actualizado es: "
          + oldServicio.getId() + " - " + oldServicio.getDescripcion() + " - "
          + oldServicio.getEstado() + " - " + oldServicio.getCategoriaId().getDescripcion() + " - "
          + oldServicio.getCreatedAt() + " - " + oldServicio.getUpdatedAt() + " - "
          + oldServicio.getDetalleComprobanteList());

      jpacServicio.edit(oldServicio);

      ServicioListServlet call = new ServicioListServlet();
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
