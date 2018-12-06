/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAO.UsuarioDAO;
import Ferramentas.Email;
import Validadores.Validações;
import Principal.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jeferson Pacheco
 */
@WebServlet(name = "cadUsuario", urlPatterns = {"/cadUsuario"})
public class cadUsuarioServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    final String pagina = "cadUsuario.jsp";
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
            
            // monta um objeto contato
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuario.setSenha2(senha2);
            usuario.setNome(nome);
            usuario.setPerfil(perfil);
            usuario.setStatus("Ativo");
            
            // validar os dados que o usuario inseriu
            if(controllerValidarUsuario(request, response, usuario) == false)
                return;        
            
            // salvar o usuario validado no banco de dados
            controllerInserirUsuario(request, response, usuario);

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
            Logger.getLogger(cadUsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(cadUsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
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

     protected void controllerInserirUsuario(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws ServletException, IOException, SQLException
     {
         // abre a conexão com o banco
            UsuarioDAO dao = null;
            try{
            dao = new UsuarioDAO();
            }catch(Exception e)
            {
                String msg = "Desculpe, não foi possível estabelecer conexão com o banco de dados!";
                processarResultado(request, response, falha, msg, usuario);
            }
            
            if(dao.inserirUsuario(usuario) == true)     // Se inseriu com sucesso, devolve mensagem pra tela de sucesso
            {
                String msg = "Usuário " + usuario.getNome() +" inserido com sucesso!";
                processarResultado(request, response, sucesso, msg, usuario);
                Email.EnviarEmail("Cadastro no sistema plantas medicinais da UMC ", usuario.getEmail(), 
                        "Olá " +  usuario.getNome() + "\n \n" +
                        "Você foi cadastrado no sistema de plantas medicinais da UMC como "+usuario.getPerfil()+ "\n \n " +
                        "Credenciais de acesso: \n " +
                        "Login: " + usuario.getEmail() + "\n " +
                        "Senha: " + usuario.getSenha() + "\n\n Atenciosamente.");        
            }
            else
            {
                String msg = "Este e-mail '" + usuario.getEmail()+"' já foi cadastrado para outro usuário!";
                processarResultado(request, response, falha, msg, usuario);
            }
     }
     
     protected boolean controllerValidarUsuario(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws ServletException, IOException
     {
        Validações va = new Validações();
        String msg = va.validarCadUsuario(usuario);
        if(!msg.equals(""))              // se não preencher todos os campos
        {
            processarResultado(request, response, falha, msg, usuario);
            return false;
        }
        if(va.validarSenha(usuario) == false)                   // se as senhas não forem iguais
        {
            String msgSenha = "As senhas digitadas estão diferentes!";
            processarResultado(request, response, falha, msgSenha, usuario);
            return false;
        }
        return true;
     }
     
    protected void processarResultado(HttpServletRequest request, HttpServletResponse response, String css, String msg, Usuario usuario) throws ServletException, IOException
    {
        request.setAttribute("mensagem",css);
        request.setAttribute("mensagem2",msg);
        if(css == falha){
        request.setAttribute("nomeUsuario",usuario.getNome());
        request.setAttribute("emailUsuario",usuario.getEmail());
        }
        // Página a ser redirencionada
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);  
    }  
}
