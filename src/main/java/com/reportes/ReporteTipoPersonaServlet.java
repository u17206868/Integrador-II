package com.reportes;

import com.controller.TipoPersonaJpaController;
import com.dto.TipoPersona;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@WebServlet(name = "ReporteTipoPersonaServlet", urlPatterns = {"/ReporteTipoPersonaServlet"})
public class ReporteTipoPersonaServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        System.out.println("------------------------------Ejemplo ReporteTipoPersonaServlet------------------------------");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        Connection conexion = em.unwrap(Connection.class);

        TipoPersonaJpaController tdpJPA = new TipoPersonaJpaController(emf);
        List<TipoPersona> tipoDePersonaS = new ArrayList<>();
        
        tipoDePersonaS = tdpJPA.findTipoPersonaEntities();
        
        tipoDePersonaS.forEach(td -> System.out.println("Descripcion: " + td.getDescripcion()));
        
        JRBeanCollectionDataSource listaTdP = new JRBeanCollectionDataSource(tipoDePersonaS);
        
        try {
            ServletContext context = request.getServletContext();
            File jasperFile = new File(context.getRealPath("reportes/TipoPersona2.jrxml"));
            System.out.println("Ruta: " + jasperFile);
            
//            Map<String, Object> parametro = new HashMap<>();
            Map parametro = new HashMap();
            parametro.put("Parametro1", listaTdP);
            
            String ruta = request.getServletContext().getRealPath("reportes/pdf/ejemploPdf33.pdf");
            
            System.out.println("Path: " + jasperFile.getPath());
            JasperReport reporte = JasperCompileManager.compileReport(jasperFile.getPath());
            JasperPrint print = JasperFillManager.fillReport(reporte, parametro, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(print, ruta);
            
//            System.out.println("---Conexion: " + conexion.getCatalog());
//            byte[] bytess = JasperRunManager.runReportToPdf(jasperFile.getPath(), null, conexion);
//
//            response.setContentType("application/pdf");

//            response.setContentLength(bytess.length);
//            ServletOutputStream output = response.getOutputStream();
//            response.getOutputStream();
//            output.write(bytess, 0, bytess.length);
//            output.flush();
//            output.close();
        } catch (JRException e) {
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
