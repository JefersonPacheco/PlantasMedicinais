/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAO.SugestaoDAO;
import Principal.Sugestao;
import Validadores.Validações;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
 
/**
 *
 * @author Jeferson Pacheco
 * 
 */

@MultipartConfig(location = "C:\\Users\\User\\Documents\\NetBeansProjects\\Teste\\web\\imgCadastros")
public class sugestaoServlet extends HttpServlet {

    final String pagina = "cadSugestao.jsp";
    final String sucesso = "alert alert-success";
    final String falha = "alert alert-danger";
    final String local = "imgCadastros/";
    final String dirExcluir = "C:\\Users\\User\\Documents\\NetBeansProjects\\Teste\\web\\";  
    
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
        try (PrintWriter out = response.getWriter()) {
            
            // pega os parametros do formulário
            String id = request.getParameter("id");           // ok
            String local = request.getParameter("local");           // ok

            SugestaoDAO s = new SugestaoDAO();
            if(s.excluirSugestao(id)==true)
            {
                File f = new File(dirExcluir+local);
                if(!local.equals("imagens/Vazio.jpeg"))
                    f.delete();
                processarResultado(request, response, "Sugestão removida com sucesso!", sucesso, "consultaSugestao.jsp");
            }
            else
            {
                processarResultado(request, response, "Houve erro na operação!", falha, "consultaSugestao.jsp");
            }
                    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(sugestaoServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(sugestaoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        /*Identifica se o formulario é do tipo multipart/form-data*/
        if (ServletFileUpload.isMultipartContent(request)) {
            try 
            {
                String nomePlanta = request.getParameter("nome");
                String utilizacao = request.getParameter("utilizacao"); // ok 
                String email = request.getParameter("email"); // ok
                
                // monta um objeto de Planta, sintomas
                Sugestao sugestao = new Sugestao();
                sugestao.setNome(nomePlanta);
                sugestao.setUtilizacao(utilizacao);
                sugestao.setEmail(email);
                
                Validações va = new Validações();
                Part filePart = request.getPart("file"); // pega o <input type="file" name="file">
                String nomeArquivo = filePart.getSubmittedFileName().toLowerCase().trim().replace(" ","");   // pega o nome do arquivo, inclusive extenção

                if(!nomeArquivo.equals("")){    // se o arquivo não for vazio
                    if(va.validarTipoArquivo(nomeArquivo) == false){        // valida se é do tipo imagem
                        processarResultadoFalha(request, response, "O arquivo para imagem da planta não é válido, favor inserir .jpg ou .png", falha);
                        return;
                    }
                
                    if(filePart.getSize() > 5000000)   // se Arquivo for maior que 5 megas mostra erro
                    {
                        processarResultadoFalha(request, response, "Favor inserir um arquivo com menos de 5 MB.", falha);
                        return;
                    }
                                
                    nomeArquivo = request.getParameter("email").concat(".jpg").replace(" ","");
                    sugestao.setLocal(local+nomeArquivo); 
                }
                else    // se o arquivo for vazio
                    sugestao.setLocal("imagens/Vazio.jpeg");            // se não tiver arquivo no form, seta o vazio
    
                // validar os dados que o usuario inseriu
                if (controllerValidarSugestao(request, response, sugestao) == true){
                    // inserir os dados validados no banco de dados
                    if(controllerInserirSugestao(request, response, sugestao) == true){
                        if(!sugestao.getLocal().equals("imagens/Vazio.jpeg")){ // se a imagem não for nula, salva no diretorio
                            String fileName = Paths.get(sugestao.getLocal()).getFileName().toString();
                            filePart.write(fileName);
                        }
                        String msg = "Muito obrigado pela sua contribuição! Sugestão enviada com sucesso!";
                        processarResultado(request, response, msg, sucesso);
                        return;
                    }
                    else
                        return;
                }   
            } catch(IOException | SQLException | ServletException ex){
                String msg = "Upload de arquivo falhou devido a "+ ex;
                processarResultadoFalha(request, response, msg, falha);
            }
        } else {
            String msg = "Desculpe este Servlet lida apenas com pedido de upload de arquivos";
            processarResultadoFalha(request, response, msg, falha);
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
    
    protected boolean controllerValidarSugestao(HttpServletRequest request, HttpServletResponse response, Sugestao sugestao) throws ServletException, IOException
    {
        Validações va = new Validações();
        String msg = va.validarCadSugestao(sugestao);       // retorna "" caso não haja erro, caso haja erro retorna as mensagens de erro
        boolean error = false;
        
        if(!msg.equals("")){           // Se mensagem for difernte de "", houve erro na validação
            error = true;
        }
        
        if(error == true){          // erro identificado nas validações, retorna mensagem ao usuário
            processarResultadoFalha(request, response, msg, falha);
            return false;
        }
        else
            return true;            // retorna true para inserir no banco
    }

     protected boolean controllerInserirSugestao(HttpServletRequest request, HttpServletResponse response, Sugestao sugestao) throws ServletException, IOException, SQLException
     {
            // abre a conexão com o banco
            SugestaoDAO dao = null;
            try{
                dao = new SugestaoDAO();
            }catch(Exception e)
            {
                String msg = "Desculpe, não foi possível estabelecer conexão com o banco de dados!";
                processarResultadoFalha(request, response, msg, falha);
                return false;
            }
            
            if(dao.inserirSugestao(sugestao) == true)
            {
                // imprime o sucesso
                return true;
            }
            else
            {
                String msg = "Ocorreu erro ao inserir no banco de dados!";
                processarResultadoFalha(request, response, msg, falha);
                return false;
            }

    }
     
    protected void processarResultado(HttpServletRequest request, HttpServletResponse response, String msg, String css) throws ServletException, IOException
    {   // Em caso de sucesso no cadastro
        request.setAttribute("mensagem", css);             // seta o css do retorno
        request.setAttribute("mensagem2", msg);            // seta a mensagem do retorno
        
        // Página a ser redirencionada
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);  
    }
    
    protected void processarResultadoFalha(HttpServletRequest request, HttpServletResponse response, String msg, String css) throws ServletException, IOException
    {
        request.setAttribute("mensagem", css);             // seta o css do retorno
        request.setAttribute("mensagem2", msg);            // seta a mensagem do retorno
        request.setAttribute("nomePlanta", request.getParameter("nome"));
        request.setAttribute("utilizacao", request.getParameter("utilizacao")); 
        request.setAttribute("emailUser", request.getParameter("email")); 
        // Página a ser redirencionada
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);  
    }
    
    protected void processarResultado(HttpServletRequest request, HttpServletResponse response, String msg, String css, String pagina) throws ServletException, IOException
    {   // processar a exclusão de uma sugestão

        request.setAttribute("mensagem", css);             // seta o css do retorno
        request.setAttribute("mensagem2", msg);            // seta a mensagem do retorno
       
        // Página a ser redirencionada
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);  
    } 
    
    private void setarDados(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException, ServletException, SQLException
    {

        String nomePlanta = request.getParameter("nome");
        String utilizacao = request.getParameter("utilizacao"); // ok 
        String email = request.getParameter("email"); // ok
        // monta um objeto de Planta, sintomas
        Sugestao sugestao = new Sugestao();
        sugestao.setNome(nomePlanta);
        sugestao.setUtilizacao(utilizacao);
        sugestao.setEmail(email);
        sugestao.setLocal(fileName);
        
        // validar os dados que o usuario inseriu
        if (controllerValidarSugestao(request, response, sugestao) == true)
        {
        // inserir os dados validados no banco de dados
            controllerInserirSugestao(request, response, sugestao);
        }
    }
}