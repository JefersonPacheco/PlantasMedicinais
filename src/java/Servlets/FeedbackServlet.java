    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAO.PlantaDAO;
import Principal.Planta;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jeferson Pacheco
 */
public class FeedbackServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String pagina = "detalhePlanta.jsp";
    final String sucesso = "alert alert-success";
    final String falha = "alert alert-danger";
    
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */

            String id = request.getParameter("id");
            String feedback = request.getParameter("feedback");
            
            if(feedback.trim().equals(""))
            {
                processarFalha(request, response, falha, "Favor inserir um feedback ao assistente", id);
                return;
            }

            Planta planta = new Planta();
            planta.setId(id);
            planta.setFeedback(feedback);
            planta.setStatus("Reprovada");

            PlantaDAO p = null;
            try {
                p = new PlantaDAO();
            } catch (ClassNotFoundException ex) {
                processarRetorno(request, response, falha, "Erro! Não foi possível acessar o banco dados!");
            }

            if(p.reprovarPlanta(planta) == true){
                processarRetorno(request, response, sucesso, "Feedback enviado com sucesso!");
            }
            else{
                processarRetorno(request, response, falha, "Falha no enviado do feedback!");
            }
        } catch (Exception ex) {
            try {
                processarRetorno(request, response, falha, "Erro! Não foi possível acessar o banco dados!");
            } catch (ClassNotFoundException ex1) {
                Logger.getLogger(FeedbackServlet.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } 
    }
    
    protected void processarRetorno(HttpServletRequest request, HttpServletResponse response, String css, String msg) throws ServletException, IOException, ClassNotFoundException
    {
        request.setAttribute("mensagem", css);             // seta o css do retorno
        request.setAttribute("mensagem2", msg);            // seta a mensagem do retorno
        
        RequestDispatcher dis = request.getRequestDispatcher("avaliaPlanta.jsp");   
        dis.forward(request, response);
    }

    protected void processarFalha(HttpServletRequest request, HttpServletResponse response, String css, String msg, String id) throws ServletException, IOException, ClassNotFoundException
    {
        request.setAttribute("mensagem",css);
        request.setAttribute("mensagem2",msg);
        
        RequestDispatcher dis = request.getRequestDispatcher("PlantaServlet?id="+id+"&pagina=aprovaPlanta.jsp");   
        dis.forward(request, response);
    }

}
