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
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jasypt.util.password.BasicPasswordEncryptor;

@WebServlet(name = "PersonaCreateServlet", urlPatterns = {"/PersonaCreateServlet"})
public class PersonaCreateServlet extends HttpServlet {

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

        System.out.println("Bandera servlet create Persona");
//Obteniendo todos los parámetros que recibimos de la vista; solo para saber con qué variables llegan
        System.out.println(request.getParameterMap());
        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
            for (String s : e.getValue()) {
                System.out.println("Key: " + e.getKey() + " ForValue: " + s);
            }
        }
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

            PersonaJpaController jpacPersona = new PersonaJpaController(emf);
            TipoPersonaJpaController jpacTdP = new TipoPersonaJpaController(emf);
            DistritoJpaController jpacDistrito = new DistritoJpaController(emf);

            Persona miPersona = new Persona();
            TipoPersona miTdP = new TipoPersona();
            Distrito miDistrito = new Distrito();

            List<Persona> personas = new ArrayList<>();
            List<Distrito> distritos = new ArrayList<>();

            String contrasenia, contrasenia2, contraseniaok = null;
            BasicPasswordEncryptor bpe = new BasicPasswordEncryptor();

//      Date dt = new Date();
//      Timestamp ts = new Timestamp(dt.getTime());
//      System.out.println(ts);
//      System.out.println("Tipo de persona: "+request.getParameter("addTdPersonaId"));
            if (request.getParameter("addTdPersonaId") == null) {
                System.out.println("Tipo de persona vacío, viene del register");
                miTdP = jpacTdP.findTipoPersona(Long.valueOf(2));
                System.out.println("El Tipo de Persona obtenido fue: " + miTdP.getDescripcion() + " - " + miTdP.getId());

            } else {//Llenando datos  que únicamente se reciben desde Persona.jsp
//      Obteniendo el Tipo dePpersona en base al Id obtenido de la vista
                miTdP = jpacTdP.findTipoPersona(Long.valueOf(request.getParameter("addTdPersonaId")));
                System.out.println("El Tipo de Persona obtenido fue: " + miTdP.getDescripcion() + " - " + miTdP.getId());
            }

            miDistrito = jpacDistrito.findDistrito(Long.valueOf(request.getParameter("addDistritoId")));

//      Llenando los parámetros independientes del tipo de persona obtenidos de la vista
//            mi_distrito.setIdTelefono(566);                        //No necesario, tiene auto_increment
            miPersona.setUniqueId(String.valueOf(java.util.UUID.randomUUID()));
            miPersona.setNombres(request.getParameter("addNombres"));
            miPersona.setApellidos(request.getParameter("addApellidos"));
            miPersona.setDni(request.getParameter("addDni"));
            miPersona.setTelefono(request.getParameter("addTelefono"));
            miPersona.setDireccion(request.getParameter("addDireccion"));

            if (request.getParameter("addReferencia").equalsIgnoreCase("")) {
                miPersona.setReferencia("Ninguna");
            } else {
                miPersona.setReferencia(request.getParameter("addReferencia"));
            }
            
            miPersona.setEmail(request.getParameter("addEmail"));

            
            contrasenia = String.valueOf(request.getParameter("addPassword"));
            contrasenia2 = String.valueOf(request.getParameter("addPassword2"));
            
            
            if (contrasenia.equalsIgnoreCase(contrasenia2)) {
                System.out.println("Bandera: Password SI coinciden");
                contraseniaok = contrasenia;
                miPersona.setPassword(bpe.encryptPassword(String.valueOf(contraseniaok)));
                
                /*
                *******************************
                */
    //      if (request.getParameter("addTdPersonaId").equals("2")) {
    //        miPersona.setTurno("noche");
    //      } else {
    //        miPersona.setTurno(request.getParameter("addTurno"));
    //      }
    //            miPersona.setTurno(request.getParameter("addTurno"));
                miPersona.setEstado("activo");
                miPersona.setTipoPersonaId(miTdP);
                miPersona.setDistritoId(miDistrito);
    //      miPersona.setCreatedAt(ts);
    //      miPersona.setUpdatedAt(ts);

    //      Llamando al método crear del controlador y pasándole el objeto
                jpacPersona.create(miPersona);

                if (request.getParameter("addTdPersonaId") == null) {
                    request.getRequestDispatcher("/EmailRegistroPersonaServlet").include(request, response);
                    response.sendRedirect("auth/login.jsp");
                } else {
    //      Llamando al listALGO.jsp
                    PersonaListServlet call = new PersonaListServlet();
                    call.processRequest(request, response);
                }                
                
                /*
                ******************************************
                */
                
            } else {
                System.out.println("Bandera: Password NO coinciden");
                    //request.getRequestDispatcher("/EmailRegistroPersonaServlet").include(request, response);
                    response.sendRedirect("auth/register.jsp");
            }
            


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
