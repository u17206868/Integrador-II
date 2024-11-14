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
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DetalleComprobanteEditServlet", urlPatterns = {"/DetalleComprobanteEditServlet"})
public class DetalleComprobanteEditServlet extends HttpServlet {

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

        System.out.println("Entrando a Detalle Comprobante Edit Servlet");
        System.out.println(request.getParameter("editId"));
        System.out.println(request.getParameter("editCantidad"));
        System.out.println(request.getParameter("editPrecio"));
        System.out.println(request.getParameter("editSubtotal"));
        System.out.println(request.getParameter("editIgv"));
        System.out.println(request.getParameter("editTotal"));
        System.out.println(request.getParameter("editComprobanteId"));
        System.out.println(request.getParameter("editServicioId"));
//    System.out.println(request.getParameter("editPrendaId"));
        System.out.println(request.getParameter("editEstado"));
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
//      Inicialización de objetos
            DetalleComprobanteJpaController jpaDetC = new DetalleComprobanteJpaController(emf);
            ComprobanteJpaController jpaComprobante = new ComprobanteJpaController(emf);
            ServicioJpaController jpaServicio = new ServicioJpaController(emf);

            DetalleComprobante oldObject_detC;
            Comprobante miComprobante;
            Servicio miServicio;

//      Lo relacionado a la fecha
            Date dt = new Date();
            Timestamp ts = new Timestamp(dt.getTime());
            System.out.println(ts);

//      Obteniendo el objeto con foreign key en base al Id que nos da la vista
            miComprobante = jpaComprobante.findComprobante(Long.valueOf(request.getParameter("editComprobanteId")));
            miServicio = jpaServicio.findServicio(Long.valueOf(request.getParameter("editServicioId")));

            //  Ahora necesitamos obtener el objeto a editar para chancar los nuevos valores encima
            oldObject_detC = jpaDetC.findDetalleComprobante(Long.valueOf(request.getParameter("editId")));
            System.out.println("El Detalle de Comprobante obtenido es: " + oldObject_detC);

//      Comparando y asignando nuevos valores al objeto
            if (oldObject_detC.getCantidad() == null || !oldObject_detC.getCantidad().equals(Integer.valueOf(request.getParameter("editCantidad")))) {
                oldObject_detC.setCantidad(Integer.valueOf(request.getParameter("editCantidad")));
            }
            if (oldObject_detC.getPrecio() == null || oldObject_detC.getPrecio().compareTo(Double.valueOf(request.getParameter("editPrecio"))) != 0) {
                oldObject_detC.setPrecio(Double.valueOf(request.getParameter("editPrecio")));
            }

            if (oldObject_detC.getSubtotal() == null || oldObject_detC.getSubtotal().compareTo(Double.valueOf(request.getParameter("editSubtotal"))) != 0) {
                oldObject_detC.setSubtotal(Double.valueOf(request.getParameter("editSubtotal")));
            }
            if (oldObject_detC.getIgv() == null || oldObject_detC.getIgv().compareTo(Double.valueOf(request.getParameter("editIgv"))) != 0) {
                oldObject_detC.setIgv(Double.valueOf(request.getParameter("editIgv")));
            }

            if (oldObject_detC.getTotal() == null || oldObject_detC.getTotal().compareTo(Double.valueOf(request.getParameter("editTotal"))) != 0) {
                oldObject_detC.setTotal(Double.valueOf(request.getParameter("editTotal")));
            }

            if (!oldObject_detC.getEstado().equals(request.getParameter("editEstado"))) {
                oldObject_detC.setEstado(request.getParameter("editEstado"));
            }

            if (!oldObject_detC.getComprobanteId().equals(miComprobante)) {
                oldObject_detC.setComprobanteId(miComprobante);
            }
            if (!oldObject_detC.getServicioId().equals(miServicio)) {
                oldObject_detC.setServicioId(miServicio);
            }

            oldObject_detC.setUpdatedAt(ts);

            System.out.println("El Detalle de Comprobante actualizado es: "
                    + oldObject_detC.getId() + " - " + oldObject_detC.getCantidad() + " - " + oldObject_detC.getTotal() + " - "
                    + oldObject_detC.getEstado() + " - " + oldObject_detC.getServicioId().getDescripcion() + " - "
                    + oldObject_detC.getCreatedAt() + " - " + oldObject_detC.getUpdatedAt());

            jpaDetC.edit(oldObject_detC);

            DetalleComprobanteListServlet call = new DetalleComprobanteListServlet();
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
