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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PersonaListServlet", urlPatterns = {"/PersonaListServlet"})
public class PersonaListServlet extends HttpServlet {

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

        System.out.println("Entrando a Persona List Servlet");
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

            PersonaJpaController jpacPersona = new PersonaJpaController(emf);
            TipoPersonaJpaController jpacTdP = new TipoPersonaJpaController(emf);
            DistritoJpaController jpacDistrito = new DistritoJpaController(emf);

            List<Persona> personas = new ArrayList<>();
            List<TipoPersona> TdPs = new ArrayList<>();
            List<Distrito> distritos = new ArrayList<>();
//      List<Telefono> telefonos = new ArrayList<>();

//      System.out.println(jpacontroller_object.findDistritoEntities());
            personas = jpacPersona.findPersonaEntities();
            TdPs = jpacTdP.findTipoPersonaEntities();
            distritos = jpacDistrito.findDistritoEntities();

            personas.forEach(p -> System.out.println("Data: " + p.getId() + " - " + p.getNombres()));
            //Expresion lambda reemplazando foreach
//            for (Persona per : personas) {
//                System.out.print(per.getId() + ": " + per.getNombres() + ": " + per.getEmail());
//            }
//      for (TipoPersona tipoPer : TdPs) {
//        System.out.println(tipoPer.getId() + ": " + tipoPer.getDescripcion());
//      }
            for (Persona pe : personas) {
                pe.getComprobanteList().forEach(d -> System.out.println(d.getNumero()));
            }

            TdPs.forEach(p -> System.out.println(p.getId() + " - " + p.getDescripcion()));

            request.setAttribute("mi_lista_de_personas", personas);
            request.setAttribute("mi_lista_de_TdP", TdPs);
            request.setAttribute("miListaDeDistritos", distritos);
            request.getRequestDispatcher("Persona.jsp").forward(request, response);
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
