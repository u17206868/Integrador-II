package com.reportes;

import com.controller.CitaJpaController;
import com.controller.PersonaJpaController;
import com.dto.Cita;
import com.dto.Persona;
import com.email.ResumenServiciosServlet;
import com.servlets.VentaListServlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@WebServlet(name = "ReporteResumenServiciosServlet", urlPatterns = {"/ReporteResumenServiciosServlet"})
public class ReporteResumenServiciosServlet extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

    System.out.println("----------ReporteResumenServiciosServlet----------");
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.spa_utp2023_war_1.0PU");

    CitaJpaController citaJpa = new CitaJpaController(emf);
    PersonaJpaController personaJpa = new PersonaJpaController(emf);

    Cita cita = new Cita();
    Persona per = new Persona();

    per = personaJpa.findPersona(Long.valueOf(request.getParameter("id")));

    per.getCitaList().forEach(c -> System.out.println("Cita "
            + "Id: " + c.getId()
            + " - Fecha inicio: " + c.getFechaHora()
            + " - Fecha fin: " + c.getFechaHoraEnd()
            + " - Cliente: " + c.getClienteId().getNombres()
            + " - Técnico: " + c.getTecnicoId().getNombres()
            + " - Servicio: " + c.getServicioId().getDescripcion()
            + " - Sala: " + c.getSalaId().getNroSala()
    ));

    JRBeanCollectionDataSource listaCitasXPersona = new JRBeanCollectionDataSource(per.getCitaList());

    ServletContext context = request.getServletContext();
    File jasperFile = new File(context.getRealPath("reportes/ReporteResumenServicios.jrxml"));
    System.out.println("Ruta: " + jasperFile);

//            Map<String, Object> parametro = new HashMap<>();
    Map parametro = new HashMap();
    parametro.put("listaCitasXPersona", listaCitasXPersona);
    parametro.put("nombresCliente", per.getNombres());
    parametro.put("apellidosCliente", per.getApellidos());
    parametro.put("dniCliente", per.getDni());

    String ruta = request.getServletContext().getRealPath("reportes/pdf/ReporteResumenServicios" + per.getUniqueId() + ".pdf");

    System.out.println("Path: " + jasperFile.getPath());
    JasperReport reporte;
    try {
      reporte = JasperCompileManager.compileReport(jasperFile.getPath());
      JasperPrint print = JasperFillManager.fillReport(reporte, parametro, new JREmptyDataSource());
      JasperExportManager.exportReportToPdfFile(print, ruta);

      //añadir el envío por correo
//      request.getRequestDispatcher("/ResumenServiciosServlet").include(request, response);
//      request.setAttribute("email", "mailll");
//      request.setAttribute("ruta", ruta);
      HttpSession session = request.getSession();
      session.setAttribute("ruta", ruta);

      response.sendRedirect("ResumenServiciosServlet?email=" + per.getEmail());


    } catch (JRException ex) {
      Logger.getLogger(ReporteResumenServiciosServlet.class.getName()).log(Level.SEVERE, null, ex);
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
