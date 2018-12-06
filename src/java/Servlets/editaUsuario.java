/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAO.UsuarioDAO;
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

/**
 *
 * @author Susulo
 */
public class editaUsuario extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    final String pagina = "gerenciaUsuario.jsp";
    final String sucesso = "alert alert-success";
    final String falha = "alert alert-danger";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String nome = request.getParameter("Nome");
            String email = request.getParameter("Email");
            String senha = request.getParameter("Senha");
            String senha2 = request.getParameter("Senha2");
            String perfil = request.getParameter("Perfil");
            String id = request.getParameter("Id");
            // monta um objeto contato
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuario.setSenha2(senha2);
            usuario.setNome(nome);
            usuario.setPerfil(perfil);
            usuario.setId(id);
            // validar os dados que o usuario inseriu
            if(controllerValidarUsuario(request, response, usuario) == true)            
                // salvar o usuario validado no banco de dados
                controllerAlterarUsuario(request, response, usuario);
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
            Logger.getLogger(cadUsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(editaUsuario.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(cadUsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(editaUsuario.class.getName()).log(Level.SEVERE, null, ex);
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

     protected void controllerAlterarUsuario(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws ServletException, IOException, SQLException
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
            
            if(dao.alterarUsuario(usuario) == true)     // Se inseriu com sucesso, devolve mensagem pra tela de sucesso
            {
                String msg = "Usuário '" + usuario.getNome() + "' alterado com sucesso!";
                processarResultado(request, response, sucesso, msg);
            }
            else
            {
                String msg = "Não foi possível alterar o usuário, este email já está cadastrado!";
                processarResultado(request, response, falha, msg);
            }    
     }
     
     protected boolean controllerValidarUsuario(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws ServletException, IOException
     {
        Validações va = new Validações();
        String msg = va.validarCadUsuario(usuario);
        if(!msg.equals("")){              // se não preencher todos os campos
            request.setAttribute("mensagem",falha);
            request.setAttribute("mensagem2",msg);
            // Página a ser redirencionada
            RequestDispatcher dis = request.getRequestDispatcher("UsuarioServlet?id="+usuario.getId()+"&acao=editar");   
            dis.forward(request, response);
            return false;
        }
        if(va.validarSenha(usuario) == false)                   // se as senhas não forem iguais
        {
            String msgg = "As senhas digitadas estão diferentes!";
            request.setAttribute("mensagem",falha);
            request.setAttribute("mensagem2",msgg);
            // Página a ser redirencionada
            RequestDispatcher dis = request.getRequestDispatcher("UsuarioServlet?id="+usuario.getId()+"&acao=editar");  
            dis.forward(request, response);
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
