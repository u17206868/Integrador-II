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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DetalleComprobanteCreateServlet", urlPatterns = {"/DetalleComprobanteCreateServlet"})
public class DetalleComprobanteCreateServlet extends HttpServlet {

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

        System.out.println("Bandera servlet create DetalleComprobante");
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

            DetalleComprobanteJpaController jpaDetC = new DetalleComprobanteJpaController(emf);
            ComprobanteJpaController jpaComprobante = new ComprobanteJpaController(emf);
            ServicioJpaController jpaServicio = new ServicioJpaController(emf);

            DetalleComprobante miDetC = new DetalleComprobante();
            Comprobante miComprobante = new Comprobante();
            Servicio miServicio = new Servicio();

//            Date dt = new Date();
//            Timestamp ts = new Timestamp(dt.getTime());
//            System.out.println(ts);
//      Obteniendo el objeto en base al Id obtenido de la vista
            miComprobante = jpaComprobante.findComprobante(Long.valueOf(request.getParameter("addComprobanteId")));
            miServicio = jpaServicio.findServicio(Long.valueOf(request.getParameter("addServicioId")));
            System.out.println("La factura obtenida fue: " + miComprobante.getNumero() + " - " + miComprobante.getId());
            System.out.println("El servicio obtenido fue: " + miServicio.getDescripcion() + " - " + miServicio.getId());

//      Llenando los parámetros del distrito obtenidos de la vista
            miDetC.setUniqueId(String.valueOf(java.util.UUID.randomUUID()));
            miDetC.setCantidad(Integer.valueOf(request.getParameter("addCantidad")));
            miDetC.setPrecio(Double.valueOf(request.getParameter("addPrecio")));
            miDetC.setSubtotal(Double.valueOf(request.getParameter("addSubtotal")));
            miDetC.setIgv(Double.valueOf(request.getParameter("addIgv")));
            miDetC.setTotal(Double.valueOf(request.getParameter("addTotal")));
            miDetC.setEstado("activo");
//            miDetC.setCreatedAt(ts);
//            miDetC.setUpdatedAt(ts);
            miDetC.setComprobanteId(miComprobante);
            miDetC.setServicioId(miServicio);

//      Llamando al método crear del controlador y pasándole el objeto Distrito
            jpaDetC.create(miDetC);

//      Llamando al listALGO.jsp
            DetalleComprobanteListServlet call = new DetalleComprobanteListServlet();
            call.processRequest(request, response);

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
