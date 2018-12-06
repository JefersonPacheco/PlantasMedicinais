/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAO.UsuarioDAO;
import Principal.Usuario;
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
 * @author Susulo
 */
public class UsuarioServlet extends HttpServlet {

     /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String pagina = "gerenciaUsuario.jsp";            // pagina para ir após excluir usuario
    String pagina2 = "editaUsuario.jsp";           // pagina para editar usuario
    final String sucesso = "alert alert-success";
    final String falha = "alert alert-danger";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            // pega os parametros da URL
            String id = request.getParameter("id");           
            String acao = request.getParameter("acao");           

            if(acao.equals("excluir"))
            {
                UsuarioDAO u = null;
                try{
                    u = new UsuarioDAO();
                }catch(Exception e){
                    processarResultado(request, response, falha, "Não foi conectar ao banco de dados!");
                    return;
                }
                if(u.excluir(id) == true)                   // exclui o usuario pelo ID
                {
                    processarResultado(request, response, sucesso, "Usuário removido com sucesso!");
                }
                else
                {
                    processarResultado(request, response, falha, "Não foi possível excluir o usuário!");
                }
            }
            if(acao.equals("editar"))
            {
                processarResultado2(request, response, id, pagina2);
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
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
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

     protected void processarResultado(HttpServletRequest request, HttpServletResponse response, String css, String msg) throws ServletException, IOException, ClassNotFoundException
    {
        request.setAttribute("mensagem", css);
        request.setAttribute("mensagem2",msg);
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);  
    }
   
    protected void processarResultado2(HttpServletRequest request, HttpServletResponse response, String id, String pagina) throws ServletException, IOException, ClassNotFoundException, SQLException
    {
        UsuarioDAO u = null;
        try{
            u = new UsuarioDAO();
        }catch(Exception e){
            request.setAttribute("mensagem", falha);
            request.setAttribute("mensagem2", "Não foi possível conectar ao banco de dados!");
            request.setAttribute("nome","");
            request.setAttribute("email","");
            request.setAttribute("senha","");
            RequestDispatcher dis = request.getRequestDispatcher(pagina);   
            dis.forward(request, response);  
            return;
        }
        
        Usuario usuario;
        
        usuario = u.consultarUsuario(id);
        
        request.setAttribute("id",id);
        request.setAttribute("nome",usuario.getNome());
        request.setAttribute("email",usuario.getEmail());
        request.setAttribute("senha",usuario.getSenha());
        request.setAttribute("perfil",usuario.getPerfil());
        
        // Página a ser redirencionada
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);  
    }  
}
