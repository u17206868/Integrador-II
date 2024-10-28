/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.servlets;

import com.controller.ComprobanteJpaController;
import com.controller.DetalleComprobanteJpaController;
import com.controller.ServicioJpaController;
import com.dto.DetalleComprobante;
import com.dto.Comprobante;
import com.dto.Servicio;
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

@WebServlet(name = "DetalleComprobanteListServlet", urlPatterns = {"/DetalleComprobanteListServlet"})
public class DetalleComprobanteListServlet extends HttpServlet {

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
    
    System.out.println("Entrando a DetalleFactura List Servlet");
    try {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
      
      DetalleComprobanteJpaController jpacDetComprobante = new DetalleComprobanteJpaController(emf);
      ComprobanteJpaController jpacComprobante = new ComprobanteJpaController(emf);
      ServicioJpaController jpacServicio = new ServicioJpaController(emf);
//      PrendaJpaController jpac_object_prenda = new PrendaJpaController(Persistence.createEntityManagerFactory("com.lav_lavanderia115_war_1.0PU"));
      List<DetalleComprobante> detComprobantes = new ArrayList<>();
      List<Comprobante> comprobantes = new ArrayList<>();
      List<Servicio> servicios = new ArrayList<>();
//      List<Prenda> mi_lista_de_prendas = new ArrayList<>();

//      System.out.println(jpacontroller_object.findDistritoEntities());
      detComprobantes = jpacDetComprobante.findDetalleComprobanteEntities();
      comprobantes = jpacComprobante.findComprobanteEntities();
      servicios = jpacServicio.findServicioEntities();
//      mi_lista_de_prendas = jpac_object_prenda.findPrendaEntities();

      for (DetalleComprobante temp1 : detComprobantes) {
        System.out.println(temp1.getId() +" - "+ temp1.getCantidad());
      }
      for (Comprobante temp2 : comprobantes) {
        System.out.println(temp2.getId() + " - " + temp2.getNumero());
      }
      for (Servicio temp3 : servicios) {
        System.out.println(temp3.getId() + " - " + temp3.getDescripcion());
      }
//      for (Prenda temp4 : mi_lista_de_prendas) {
//        System.out.println(temp4.getId() + " - " + temp4.getObservacion());
//      }

      request.setAttribute("mi_lista_de_dcs", detComprobantes);
      request.setAttribute("mi_lista_de_comprobantes", comprobantes);
      request.setAttribute("mi_lista_de_servicios", servicios);
//      request.setAttribute("mi_lista_de_prendas", mi_lista_de_prendas);
      request.getRequestDispatcher("DetalleComprobante.jsp").forward(request, response);

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
