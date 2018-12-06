/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAO.UsuarioDAO;
import Ferramentas.Email;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

public class esqueciSenhaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    final String pagina = "esqueciSenha.jsp";
    final String sucesso = "alert alert-success";
    final String falha = "alert alert-danger";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
            String email = request.getParameter("email").trim();
            // validar os dados que o usuario inseriu
            if(controllerValidarEmail(request, response, email) == true)    
            {
                String token = gerarToken(email);           // gera o token para recuperação de senha
                controllerAlterarSenha(request, response, token, email);    // salva o token no banco
            }
            else
                return;

        } catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(esqueciSenhaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  // pega o token da URL
            throws ServletException, IOException {
       response.setContentType("text/html;charset=UTF-8");
       request.setCharacterEncoding("UTF-8");
       // pega os parametros do formulário
       String token = request.getParameter("token");           // ok
       int result;
       
        try {
            UsuarioDAO u = new UsuarioDAO();
            result = u.validarToken(token);
            
            if(result > 0)
            {
                processarTrocaSenha(request, response, Integer.toString(result));
                return;
            }
            if(result == -1)     // Se inseriu com sucesso, devolve mensagem pra tela de sucesso
            {
                String msg = "Ops, erro ao acessar o banco de dados!";
                processarResultado(request, response, falha, msg);       
            }
            if(result == -2)     // Se inseriu com sucesso, devolve mensagem pra tela de sucesso
            {
                String msg = "Este token não é válido!";
                processarResultado(request, response, falha, msg);       
            }
            if(result == -4)
            {
                String msg = "Ops, ocorreu erro ao processar a requisição!";
                processarResultado(request, response, falha, msg);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(esqueciSenhaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private String gerarToken(String email) throws NoSuchAlgorithmException
    {
        String frase = email;
      
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(frase.getBytes());
            return stringHexa(md.digest());
    }
    
    private static String stringHexa(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
           int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
           int parteBaixa = bytes[i] & 0xf;
           if (parteAlta == 0) s.append('0');
           s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }
    
    protected boolean controllerValidarEmail(HttpServletRequest request, HttpServletResponse response, String email) throws ServletException, IOException, ClassNotFoundException
    {
        if(email.trim().equals(""))            // se não preencher todos os campos
        {
            String msg = "Favor inserir seu email!";
            processarResultado(request, response, falha, msg);
            return false;
        }
        
        return true;
    }
     
    protected void controllerAlterarSenha(HttpServletRequest request, HttpServletResponse response, String token, String email) throws ServletException, IOException, SQLException
     {
         // abre a conexão com o banco
            UsuarioDAO dao = null;
            try{
                dao = new UsuarioDAO();
            }catch(ClassNotFoundException e)
            {
                String msg = "Desculpe, não foi possível estabelecer conexão com o banco de dados!";
                processarResultado(request, response, falha, msg);
            }
            
            int result = dao.inserirToken(token, email);
            
            if(result == 1)    
            {
                String msg = "Ops, erro ao acessar o banco de dados!";
                processarResultado(request, response, falha, msg);
                return;
            }
            if(result == 2)   
            {
                String msg = "Este e-mail não faz parte de nosso sistema!";
                processarResultado(request, response, falha, msg);
                return;
            }
            if(result == 3)     // Sucesso
            {
                String URL = "http://localhost:8084/Teste/esqueciSenhaServlet?token="+token+"";
                //URL = "";
                String msg = "Um e-mail foi enviado para sua conta para alteração de senha!";
                processarResultado(request, response, sucesso, msg);       
                Email.EnviarEmail("Alteração de Senha - Plantas Medicinais UMC", email, 
                        "Olá\n \n" +
                        "Por favor, clique no link a seguir para realizar alteração de sua senha: "+ URL +"\n \n " +
                       "Caso não tenha solicitado troca de senha, por favor, desconsidere este email.");   
                return;
            }
            if(result == 4)
            {
                String msg = "Ops, ocorreu erro ao enviar seu email!";
                processarResultado(request, response, falha, msg);
                                return;
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
    
    protected void processarTrocaSenha(HttpServletRequest request, HttpServletResponse response, String id) throws ServletException, IOException
    {
        request.setAttribute("id",id);
        // Página a ser redirencionada
        RequestDispatcher dis = request.getRequestDispatcher("alterSenhaEsq.jsp");   
        dis.forward(request, response);
    }
}
