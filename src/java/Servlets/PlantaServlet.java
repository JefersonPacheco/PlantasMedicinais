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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jeferson Pacheco
 */
@MultipartConfig(location = "C:\\Users\\User\\Documents\\NetBeansProjects\\Teste\\web\\imgCadastros")
public class PlantaServlet extends HttpServlet {

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
       response.setContentType("text/html;charset=UTF-8");
       request.setCharacterEncoding("UTF-8");
        try {
            // pega os parametros do formulário
            String id = request.getParameter("id");           // ok
            pagina = request.getParameter("pagina");           // ok

            if(pagina.equals("aprovar"))
            {
                HttpSession ses = request.getSession();              // recupera a sessão
                int idUsuario = Integer.parseInt((String) ses.getAttribute("id"));   // atribui a int id o id de usuário que está na sessão
                PlantaDAO p = null;
                try{
                    p = new PlantaDAO();
                }catch(Exception e){
                    request.setAttribute("mensagem",falha);
                    request.setAttribute("mensagem2","Erro! Não foi possível se conectar ao banco de dados!");
                    RequestDispatcher dis = request.getRequestDispatcher("avaliaPlanta.jsp");   
                    dis.forward(request, response); 
                }
                if(p.aprovarPlanta(id, idUsuario) == true)
                {
                    processarResultado(request, response, "Planta aprovada com sucesso!");
                }
            }
            else if(pagina.equals("editar"))        // professor edita planta que já está aprovada 
            {
                processarResultado(request, response, id, "editaPlanta.jsp");
            }
            else if(pagina.equals("editar2"))       // assistente editar plantas reprovadas/pendentes
            {
                processarResultado(request, response, id, "editaPlantaCF.jsp");
            }
            else if(pagina.equals("editar3"))       // professor editar planta e aprovar no momento da avaliação
            {
                processarResultado(request, response, id, "editaPlantaEA.jsp");
            }
            else if(pagina.equals("excluir")) // Exclusão feita por professor
            {
                PlantaDAO p = null;
                try{
                    p = new PlantaDAO();
                }catch(Exception e){
                    request.setAttribute("mensagem",falha);
                    request.setAttribute("mensagem2","Não possível se conectar ao banco de dados!");
                    RequestDispatcher dis = request.getRequestDispatcher("gerenciaPlanta.jsp");   
                    dis.forward(request, response);  
                    return;
                }
                if(p.excluirPlanta(id) == true)
                {
                    request.setAttribute("mensagem",sucesso);
                    request.setAttribute("mensagem2","Planta removida com sucesso!");
                    RequestDispatcher dis = request.getRequestDispatcher("gerenciaPlanta.jsp");   
                    dis.forward(request, response); 
                }
                else
                {
                    request.setAttribute("mensagem",falha);
                    request.setAttribute("mensagem2","Não foi possível remover a planta!");
                    RequestDispatcher dis = request.getRequestDispatcher("gerenciaPlanta.jsp");   
                    dis.forward(request, response); 
                }
            }
            else if(pagina.equals("excluir2"))  // Exclusão feita por assistente
            {
                PlantaDAO p = null;
                try{
                    p = new PlantaDAO();
                }catch(Exception e){
                    request.setAttribute("mensagem",falha);
                    request.setAttribute("mensagem2","Não possível se conectar ao banco de dados!");
                    RequestDispatcher dis = request.getRequestDispatcher("plantasPendentes.jsp");   
                    dis.forward(request, response);  
                    return;
                }
                if(p.excluirPlanta(id) == true)
                {
                    request.setAttribute("mensagem",sucesso);
                    request.setAttribute("mensagem2","Planta removida com sucesso!");
                    RequestDispatcher dis = request.getRequestDispatcher("plantasPendentes.jsp");   
                    dis.forward(request, response); 
                }
                else
                {
                    request.setAttribute("mensagem",falha);
                    request.setAttribute("mensagem2","Ocorreu erro ao remover a planta!");
                    RequestDispatcher dis = request.getRequestDispatcher("plantasPendentes.jsp");   
                    dis.forward(request, response); 
                }
            }
            else         // Pegar os dados de planta para editar
            {
                processarResultado(request, response, id, pagina);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(cadUsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlantaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            // pega os parametros do formulário
            String id = request.getParameter("id");           // ok
            pagina = request.getParameter("pagina");           // ok

            if(pagina.equals("aprovar"))
            {
                HttpSession ses = request.getSession();              // recupera a sessão
                int idUsuario = Integer.parseInt((String) ses.getAttribute("id"));   // atribui a int id o id de usuário que está na sessão
                PlantaDAO p = new PlantaDAO();
                if(p.aprovarPlanta(id, idUsuario) == true)
                {
                    processarResultado(request, response, "Planta aprovada com sucesso!");
                }
            }
            else if(pagina.equals("editar"))
            {
                processarResultado(request, response, id, "editaPlanta.jsp");
            }
            else if(pagina.equals("excluir")) // Exclusão feita por professor
            {
                PlantaDAO p = null;
                try{
                    p = new PlantaDAO();
                }catch(Exception e){
                    request.setAttribute("mensagem",falha);
                    request.setAttribute("mensagem2","Não possível se conectar ao banco de dados!");
                    RequestDispatcher dis = request.getRequestDispatcher("gerenciaPlanta.jsp");   
                    dis.forward(request, response);  
                    return;
                }
                if(p.excluirPlanta(id) == true)
                {
                    request.setAttribute("mensagem",sucesso);
                    request.setAttribute("mensagem2","Planta removida com sucesso!");
                    RequestDispatcher dis = request.getRequestDispatcher("gerenciaPlanta.jsp");   
                    dis.forward(request, response);
                    return;
                }
                else
                {
                    request.setAttribute("mensagem",falha);
                    request.setAttribute("mensagem2","Não foi possível remover a planta!");
                    RequestDispatcher dis = request.getRequestDispatcher("gerenciaPlanta.jsp");   
                    dis.forward(request, response); 
                    return;
                }
            }
            else if(pagina.equals("excluir2"))  // Exclusão feita por assistente
            {
                PlantaDAO p = null;
                try{
                    p = new PlantaDAO();
                }catch(Exception e){
                    request.setAttribute("mensagem",falha);
                    request.setAttribute("mensagem2","Não possível se conectar ao banco de dados!");
                    RequestDispatcher dis = request.getRequestDispatcher("plantasPendentes.jsp");   
                    dis.forward(request, response);  
                    return;
                }
                
                if(p.excluirPlanta(id) == true)
                {
                    request.setAttribute("mensagem",sucesso);
                    request.setAttribute("mensagem2","Planta removida com sucesso!");
                    RequestDispatcher dis = request.getRequestDispatcher("plantasPendentes.jsp");   
                    dis.forward(request, response); 
                }
                else
                {
                    request.setAttribute("mensagem",falha);
                    request.setAttribute("mensagem2","Não foi possível remover a planta!");
                    RequestDispatcher dis = request.getRequestDispatcher("plantasPendentes.jsp");   
                    dis.forward(request, response); 
                    return;
                }
            }
            else         // Pegar os dados de planta para editar
            {
                processarResultado(request, response, id, pagina);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(cadUsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PlantaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
    
    protected void processarRetorno(HttpServletRequest request, HttpServletResponse response, String css, String msg) throws ServletException, IOException, ClassNotFoundException
    {
        request.setAttribute("mensagem",css);
        request.setAttribute("mensagem2",msg);
        RequestDispatcher dis = request.getRequestDispatcher("avaliaPlanta.jsp");   
        dis.forward(request, response);  
    }
        
    protected void processarResultado(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException, ClassNotFoundException
    {
        request.setAttribute("mensagem",sucesso);
        request.setAttribute("mensagem2",msg);
        RequestDispatcher dis = request.getRequestDispatcher("avaliaPlanta.jsp");   
        dis.forward(request, response);  
    }
   
    protected void processarResultado(HttpServletRequest request, HttpServletResponse response, String id, String pagina) throws ServletException, IOException, ClassNotFoundException, SQLException
    {
        PlantaDAO p = null;
        try{
            p = new PlantaDAO();
        }catch(Exception e){
            // Página a ser redirencionada
            request.setAttribute("mensagem",falha);
            request.setAttribute("mensagem2","Não possível se conectar ao banco de dados!");
            RequestDispatcher dis = request.getRequestDispatcher(pagina);   
            dis.forward(request, response);  
            return;
        }
        
        Planta planta;
        planta = p.consultarPlantaById(id);
        
        request.setAttribute("id",id);
        request.setAttribute("nomePopular",planta.getNomePopular());
        request.setAttribute("nomeCientifico",planta.getNomeCientifico());
        request.setAttribute("principioAtivo",planta.getPrincipioAtivo());
        request.setAttribute("contraIndicacoes",planta.getContraIndicacoes());
        request.setAttribute("efeitosAdversos",planta.getEfeitosAdversos());
        request.setAttribute("modoDePreparo",planta.getModoDePreparo());
        request.setAttribute("sintomas",planta.getSintomas());
        request.setAttribute("partesPlanta",planta.getPartesPlanta());
        request.setAttribute("regioes",planta.getRegioes());
        request.setAttribute("local", planta.getLocal());
        request.setAttribute("comentario", planta.getFeedback());
        request.setAttribute("professor", planta.getProfessor()); 
        
        // Página a ser redirencionada
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);  
    }  
}
