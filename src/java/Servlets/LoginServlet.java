package Servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import DAO.UsuarioDAO;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jeferson Pacheco
 */


@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    String pagina;
    final String sucesso = "alert alert-success";
    final String falha = "alert alert-danger";
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

            String email = request.getParameter("email");
            String senha = request.getParameter("Senha");

            // monta um objeto contato
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setSenha(senha);
            
            // cria um DAO, se der errado chama a pagina de erro
            UsuarioDAO dao = null;
            try{
            dao = new UsuarioDAO();
            }catch(Exception e)
            {
                // Mensagem de erro para a JSP
                String msg = "Desculpe, não foi possível acessar o banco de dados!";
                pagina = "login.jsp";
                processarResultado(request, response, msg, falha, pagina);
            }
            
            usuario = dao.validarLogin(usuario);
            
            if(usuario.getEmail().equals(email) && usuario.getSenha().equals(senha))
            {

                pagina = "login.jsp";
                HttpSession sessao = request.getSession(true);      // Abre a session       
                sessao.setAttribute("email", usuario.getEmail());
                sessao.setAttribute("id", usuario.getId());
                sessao.setAttribute("nome", usuario.getNome());
                sessao.setAttribute("perfil", usuario.getPerfil());
                    
                if(usuario.getPerfil().equals("Administrador"))
                {
                    sessao.setAttribute("pagina", "menuAdmin.jsp");
                    pagina = "gerenciaUsuario.jsp";
                }
                
                if(usuario.getPerfil().equals("Professor"))
                {
                    sessao.setAttribute("pagina", "menuProf.jsp");
                    pagina = "avaliaPlanta.jsp";
                }
                
                if(usuario.getPerfil().equals("Assistente"))
                {
                    sessao.setAttribute("pagina", "menuAssistente.jsp");
                    pagina = "cadPlanta.jsp";
                }

                response.sendRedirect(pagina);      // direciona para a página de acordo com a validação acima
            }
            else
            {
                // imprime a falha
                // Mensagem de erro para a Login.jsp
                String msg = "Usuário ou Senha Inválidos!";
                pagina = "login.jsp";
                processarResultado(request, response, msg, falha, pagina);
            }

    }

@Override  
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
}
@Override  
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
}


    protected void processarResultado(HttpServletRequest request, HttpServletResponse response, String msg, String css, String pagina) throws ServletException, IOException
    {
        request.setAttribute("mensagem", css);             // seta o css do retorno
        request.setAttribute("mensagem2", msg);            // seta a mensagem do retorno
        // Página a ser redirencionada
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);
    }  
}


