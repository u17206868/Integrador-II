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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ComprobanteEditServlet", urlPatterns = {"/ComprobanteEditServlet"})
public class ComprobanteEditServlet extends HttpServlet {

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
        System.out.println(request.getParameter("editId"));
        System.out.println(request.getParameter("editNumero"));
        System.out.println(request.getParameter("editSerie"));
        System.out.println(request.getParameter("editTipo"));
        System.out.println(request.getParameter("editFecha"));
        System.out.println(request.getParameter("editHora"));
        System.out.println(request.getParameter("editPersonaId"));
        System.out.println(request.getParameter("editEstado"));

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
//      Inicialización de objetos
            ComprobanteJpaController jpaComprobante = new ComprobanteJpaController(emf);
            PersonaJpaController jpaPersona = new PersonaJpaController(emf);
            Comprobante oldComprobante;
            Persona miPersona;

//      Lo relacionado a la fecha
            SimpleDateFormat sdf_fecha = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf_hora = new SimpleDateFormat("HH:mm");

            Date fecha = sdf_fecha.parse(String.valueOf(request.getParameter("editFecha")));
            Date hora = sdf_hora.parse(String.valueOf(request.getParameter("editHora")));

            Date dt = new Date();
            Timestamp ts = new Timestamp(dt.getTime());
            System.out.println(ts);

//      Obteniendo el objeto con foreign key en base al Id que nos da la vista
            miPersona = jpaPersona.findPersona(Long.valueOf(request.getParameter("editPersonaId")));

            //  Ahora necesitamos obtener el objeto a editar para chancar los nuevos valores encima
            oldComprobante = jpaComprobante.findComprobante(Long.valueOf(request.getParameter("editId")));
            System.out.println("El Comprobante obtenido es: " + oldComprobante);

//      Comparando y asignando nuevos valores al objeto
            if (oldComprobante.getNumero() == null || !oldComprobante.getNumero().equals(request.getParameter("editNumero"))) {
                oldComprobante.setNumero(request.getParameter("editNumero"));
            }
            if (oldComprobante.getSerie() == null || !oldComprobante.getSerie().equals(request.getParameter("editSerie"))) {
                oldComprobante.setSerie(request.getParameter("editSerie"));
            }
            if (oldComprobante.getTipo() == null || !oldComprobante.getTipo().equals(request.getParameter("editTipo"))) {
                oldComprobante.setTipo(request.getParameter("editTipo"));
            }

            //Comparando fecha y hora por separado
            if (oldComprobante.getFecha() == null || oldComprobante.getFecha().compareTo(fecha) != 0) {
                oldComprobante.setFecha(fecha);
            }
            if (oldComprobante.getHora() == null || oldComprobante.getHora().compareTo(hora) != 0) {
                oldComprobante.setHora(hora);
            }

            if (!oldComprobante.getPersonaId().equals(miPersona)) {
                oldComprobante.setPersonaId(miPersona);
            }
            if (oldComprobante.getEstado() == null || !oldComprobante.getEstado().equals(request.getParameter("editEstado"))) {
                oldComprobante.setEstado(request.getParameter("editEstado"));
            }
            oldComprobante.setUpdatedAt(ts);

            System.out.println("La Factura actualizada es: "
                    + oldComprobante.getId() + " - " + oldComprobante.getNumero() + " - "
                    + oldComprobante.getEstado() + " - " + oldComprobante.getPersonaId().getNombres() + " - "
                    + oldComprobante.getCreatedAt() + " - " + oldComprobante.getUpdatedAt());

            jpaComprobante.edit(oldComprobante);

            ComprobanteListServlet call = new ComprobanteListServlet();
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
