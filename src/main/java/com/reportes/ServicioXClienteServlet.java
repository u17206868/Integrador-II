package com.reportes;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@WebServlet(name = "ServicioXClienteServlet", urlPatterns = {"/ServicioXClienteServlet"})
public class ServicioXClienteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("------------------------------EjemServlet------------------------------");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Connection conexion = em.unwrap(Connection.class);

        try {
            ServletContext context = request.getServletContext();
            File jasperFile = new File(context.getRealPath("reportes/ServPorCliente.jasper"));
//      File jasperFile = new File(request.getContextPath(""));
            System.out.println("Ruta: " + jasperFile);
            Map parametro = new HashMap();
            parametro.put("nn", "nn");

            System.out.println("---Conexion: " + conexion.getCatalog());
//      byte[] bytess = JasperRunManager.runReportToPdf("D:\\report1.jasper", parametro, con);
            byte[] bytess = JasperRunManager.runReportToPdf(jasperFile.getPath(), null, conexion);

            response.setContentType("application/pdf");

            response.setContentLength(bytess.length);
            ServletOutputStream output = response.getOutputStream();
            response.getOutputStream();
            output.write(bytess, 0, bytess.length);
            output.flush();
            output.close();
        } catch (IOException | SQLException | JRException e) {
            System.out.println(e);
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
