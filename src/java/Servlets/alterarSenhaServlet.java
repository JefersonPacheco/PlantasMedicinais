/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAO.UsuarioDAO;
import Ferramentas.Email;
import Principal.Usuario;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jeferson Pacheco
 */
public class alterarSenhaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    final String pagina = "alterarSenha.jsp";
    final String sucesso = "alert alert-success";
    final String falha = "alert alert-danger";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String senha1 = request.getParameter("senha1").trim();
            String senha2 = request.getParameter("senha2").trim();
            String senhaAtual = request.getParameter("senhaatual").trim();         // Só recebe este parametro Troca de senha normal
            
            // monta um objeto contato
            Usuario usuario = new Usuario();
            usuario.setSenha(senha1);
            usuario.setSenha2(senha2);
            usuario.setSenhaAtual(senhaAtual);
            HttpSession ses2 = request.getSession();                                            // recupera a sessÃ£o
                String id = (String) ses2.getAttribute("id");
                usuario.setId(id);

            
            if(controllerValidarSenha(request, response, usuario) == true){     // salvar o usuario validado no banco de dados
                controllerAlterarSenha(request, response, usuario);
                return;
            }
            else
                return;

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
            Logger.getLogger(alterarSenhaServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(alterarSenhaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(alterarSenhaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
    
     protected void controllerAlterarSenha(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws ServletException, IOException, SQLException
     {
        // abre a conexão com o banco
        UsuarioDAO dao = null;
        try{
            dao = new UsuarioDAO();
        }catch(Exception e)
        {
            String msg = "Desculpe, não foi possível estabelecer conexão com o banco de dados!";
            processarResultado(request, response, falha, msg);
            return;
        }

        if(dao.alterarSenha(usuario) == true)     // Se inseriu com sucesso, devolve mensagem pra tela de sucesso
        {
            String msg = "Senha alterada com sucesso!";
            processarResultado(request, response, sucesso, msg);  
        }
        else
        {
            String msg = "Ops, ocorreu erro ao alterar senha!";
            processarResultado(request, response, falha, msg); 
        }
    }
    
    protected boolean controllerValidarSenha(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws ServletException, IOException, ClassNotFoundException, SQLException
    {
        Validações va = new Validações();
        if(va.validarAlterarSenha(usuario) == false)            // se não preencher todos os campos
        {
            String msg = "Favor preencher todos os campos de senha!";
            processarResultado(request, response, falha, msg); 
            return false;
        }
        if(va.validarSenha(usuario) == false)                   // se as senhas não forem iguais
        {
            String msg = "As senhas digitadas estão diferentes!";
            processarResultado(request, response, falha, msg);
            return false;
        }
        
        UsuarioDAO dao = null;
        try{
            dao = new UsuarioDAO();
        }catch(Exception e){
            String msg = "Não foi possível estabelecer conexão com o banco de dados!";
            processarResultado(request, response, falha, msg);
            return false;
        }
        String senhaBanco = dao.retornarSenha(usuario.getId());
        if(!usuario.getSenhaAtual().equals(senhaBanco))
        {
            String msg = "Favor digitar sua senha atual corretamente!";
            processarResultado(request, response, falha, msg);
            return false;
        }
        return true;
    }
        
    protected void processarResultado(HttpServletRequest request, HttpServletResponse response, String css, String msg) throws ServletException, IOException
    {
        request.setAttribute("mensagem",css);
        request.setAttribute("mensagem2",msg);
        // Página a ser redirencionada
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);
    }  

}
