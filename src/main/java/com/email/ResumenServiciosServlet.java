package com.email;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ResumenServiciosServlet", urlPatterns = {"/ResumenServiciosServlet"})
public class ResumenServiciosServlet extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

    System.out.println("----------Entrando a ResumenServiciosServlet----------");

    HttpSession sessionPre = request.getSession();
    String dato = (String) sessionPre.getAttribute("ruta");
    System.out.println("Dato: " + dato);

    System.out.println((String) request.getParameter("email"));

    String usuario = request.getParameter("email");

    // Ruta del archivo PDF a adjuntar
//    String rutaArchivo = request.getServletContext().getRealPath("reportes/pdf/tdp2.pdf");
    String rutaArchivo = dato;
// Carga el archivo PDF como DataSource
    File file = new File(rutaArchivo);
    DataSource dataSource = new FileDataSource(file);

    // Recipient's email ID needs to be mentioned.
    String to = usuario;
    // Sender's email ID needs to be mentioned
    String from = "lavanderiautp2022iii@gmail.com";
    // Assuming you are sending email from through gmails smtp
    String host = "smtp.gmail.com";
    // Get system properties
    Properties properties = System.getProperties();
    // Setup mail server
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", "465");
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");

    // Get the Session object.// and pass username and password
    Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("lavanderiautp2022iii@gmail.com", "bgqyxqfwvhdzrnrb");
      }
    });
    // Used to debug SMTP issues
    session.setDebug(true);
    try {
// Adjunta el PDF al mensaje
      MimeBodyPart pdfAttachment = new MimeBodyPart();
      MimeBodyPart textPart = new MimeBodyPart();

      textPart.setText("El resumen de los servicios solicitados es el siguiente:");
      pdfAttachment.setDataHandler(new DataHandler(dataSource));
      pdfAttachment.setFileName("miarchivo.pdf");

      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(textPart);
      multipart.addBodyPart(pdfAttachment);

      // Create a default MimeMessage object.
      MimeMessage message = new MimeMessage(session);
      // Set From: header field of the header.
      message.setFrom(new InternetAddress(from));
      // Set To: header field of the header.
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      // Set Subject: header field
      message.setSubject("Su resumen de Servicios");
// Setea el multipart como contenido del mensaje
      message.setContent(multipart);

      System.out.println("sending...");
      // Send message
      Transport.send(message);
      System.out.println("Sent message successfully....");
      
      
      RequestDispatcher dispatcher = request.getRequestDispatcher("CitaListServlet");
      dispatcher.forward(request, response);
    } catch (MessagingException mex) {
      mex.printStackTrace();
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
