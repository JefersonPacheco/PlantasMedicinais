/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAO.SintomaDAO;
import Principal.Sintoma;
import Validadores.Validações;
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

/**
 *
 * @author Jeferson Pacheco
 */
public class SintomaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    final String pagina = "cadSintoma.jsp";
    final String sucesso = "alert alert-success";
    final String falha = "alert alert-danger";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
         
            String sintoma = request.getParameter("sintoma");
            String id = request.getParameter("id");
            
            if(id != null) //Se o ID não for nulo, significa que o usuário chamou a exclusão de um Sintoma
            {
                SintomaDAO s = null;
                try{
                    s = new SintomaDAO();
                }catch(Exception e){
                    String msg = "Não possível conectar ao banco de dados! Tente mais tarde!";
                    processarResultado(request, response, falha, msg); 
                    return;
                }
                if(s.excluir(id) == true)
                {
                    String msg = "Sintoma excluído com sucesso!";
                    processarResultado(request, response, sucesso, msg); 
                }
                else
                {
                    String msg = "Você não pode excluir este sintoma pois ele já está associado a uma planta!<br>É necessário removê-lo da planta primeiro para obter a exclusão!";
                    processarResultado(request, response, falha, msg); 
                }
            }
            else             // Se não, o usuário chamou o cadastro de sintoma
            {
                // monta um objeto contato
                Sintoma sint = new Sintoma();
                sint.setSintoma(sintoma);

                // validar os dados que o usuario inseriu
                if(controllerValidarSintoma(request, response, sint) == true)
                {
                    // salvar o usuario validado no banco de dados
                    sint.setSintoma(formatarPrimeiraMaiuscula(sintoma));      // converte a primeira letra para maiusculo
                    controllerInserirSintoma(request, response, sint);        // chama a inserção no banco 
                }
                else
                {
                    return;
                }
            }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SintomaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SintomaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SintomaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SintomaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     
     protected boolean controllerValidarSintoma(HttpServletRequest request, HttpServletResponse response, Sintoma sintoma) throws ServletException, IOException
     {
        Validações va = new Validações();
        if(va.validarCadSintoma(sintoma) == 1)
        {
            String msg = "Favor inserir os dados!";
            processarResultado(request, response, falha, msg); 
            return false;
        }
        if(va.validarCadSintoma(sintoma) == 2)
        {
            String msg = "Favor inserir um sintoma com menos de 100 caracteres!";
            processarResultado(request, response, falha, msg); 
            return false;
        }
            return true;
     }
     
    protected void controllerInserirSintoma(HttpServletRequest request, HttpServletResponse response, Sintoma sintoma) throws ServletException, IOException, SQLException
    {
        // abre a conexão com o banco
        SintomaDAO dao = null;
        try{
            dao = new SintomaDAO();
        }catch(Exception e)
        {
            String msg = "Desculpe, não foi possível estabelecer conexão com o banco de dados!";
            processarResultado(request, response, falha, msg); 
            return;
        }

        if(dao.inserirSintoma(sintoma) == true)
        {
            String msg = "Sintoma '" + sintoma.getSintoma()+"' inserido com sucesso!";
            processarResultado(request, response, sucesso, msg); 
        }
        else
        {
            String msg = "Erro! O sintoma '" + sintoma.getSintoma()+"' já existe no banco de dados!";
            processarResultado(request, response, falha, msg); 
        }
    }
    
    protected void processarResultado(HttpServletRequest request, HttpServletResponse response, String css, String msg) throws ServletException, IOException
    {
        request.setAttribute("mensagem",css);
        request.setAttribute("mensagem2",msg);
        // Página a ser redirencionada
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);  
    }        
    
    public String formatarPrimeiraMaiuscula(String palavra)
    {
        palavra = palavra.toLowerCase();
        return((palavra.substring(0,1).toUpperCase()) + palavra.substring (1));
    }
}