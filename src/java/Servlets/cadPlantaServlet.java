/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAO.PlantaDAO;
import Principal.Planta;
import Validadores.Validações;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Jeferson Pacheco
 */

@MultipartConfig(location = "C:\\Users\\User\\Documents\\NetBeansProjects\\Teste\\web\\imgCadastros")
@WebServlet(name = "cadPlantaServlet", urlPatterns = {"/cadPlantaServlet"})
public class cadPlantaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    final String pagina = "cadPlanta.jsp";
    final String sucesso = "alert alert-success";
    final String falha = "alert alert-danger";
    String[] sintomas;
    String[] regioes;
    String[] partesPlanta;
    String[] foto = null;
    final String local = "imgCadastros/";
    final String dirExcluir = "C:\\Users\\User\\Documents\\NetBeansProjects\\Teste\\web\\"; 
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            
            Validações va = new Validações();
            // pega os parametros enviados pelo formulário de cadastro
            String nomePopular = request.getParameter("nomePopular");        
            String nomeCientifico = request.getParameter("nomeCientifico"); 
            String principioAtivo = request.getParameter("principioAtivo"); 
            String contraIndicacoes = request.getParameter("contraIndicacoes"); 
            String efeitosAdversos = request.getParameter("efeitosAdversos");   
            String modoDePreparo = request.getParameter("modoDePreparo");       
            sintomas = request.getParameterValues("sintoma");          
            regioes = request.getParameterValues("regioes");          
            partesPlanta = request.getParameterValues("partesPlanta");          
            foto = request.getParameterValues("foto");
                        
            // monta um objeto de Planta, sintomas
            HttpSession ses = request.getSession();              // recupera a sessão
            int id = Integer.parseInt((String) ses.getAttribute("id"));   // atribui a int id o id de usuário que está na sessão
            Planta planta = new Planta();
            planta.setNomePopular(nomePopular);
            planta.setNomeCientifico(nomeCientifico);
            planta.setContraIndicacoes(contraIndicacoes);
            planta.setEfeitosAdversos(efeitosAdversos);
            planta.setPrincipioAtivo(principioAtivo);
            planta.setModoDePreparo(modoDePreparo);
            planta.setStatus("Pendente"); // ta aqui no migué, tem que pegar o request pra ficar certo
            planta.setIdResponsável(id);
            
            if(ses.getAttribute("perfil").equals("Professor")){     // se for professor que cadastrou, aprova direto
                planta.setStatus("Aprovada");                   // se é professor, planta já é aprovada automaticamente
                planta.setFeedback("");
            }
            else{
                planta.setStatus("Pendente");               // se não for professor, planta fica pendente
                planta.setFeedback("<i>Aguardando avaliação</i>");
            }
            
            Part filePart = request.getPart("file"); // pega o <input type="file" name="file">
            String nomeArquivo = filePart.getSubmittedFileName().toLowerCase().trim().replace(" ","");   // pega o nome do arquivo, inclusive extenção
            
            if(!nomeArquivo.equals("")){  
                if(va.validarTipoArquivo(nomeArquivo) == false){        // valida se é do tipo imagem
                    processarFalha(request, response, falha, "O arquivo para imagem da planta não é válido, favor inserir .jpg ou .png", planta);
                    return;
                }
                
                if(filePart.getSize() > 5000000)   // se Arquivo for maior que 5 megas mostra erro
                {
                    processarFalha(request, response, falha, "Favor inserir um arquivo com menos de 5 MB.", planta);
                    return;
                }
                                
                nomeArquivo = (nomePopular + nomeCientifico).concat(".jpg").replace(" ","");
                planta.setLocal(local+nomeArquivo); 
            }
            else
                planta.setLocal("imagens/Vazio.jpeg");
            
            // validar os dados que o usuario inseriu
            if (controllerValidarPlanta(request, response, planta) == true)
            {
                try {
                    // inserir os dados validados no banco de dados
                    if(controllerInserirPlanta(request, response, planta) == true){
                        //String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // pega o nome do arquivo
                        if(!planta.getLocal().equals("imagens/Vazio.jpeg")){ // se a imagem não for nula, salva no diretorio
                            String fileName = Paths.get(planta.getLocal()).getFileName().toString();
                            filePart.write(fileName);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(cadPlantaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(cadUsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(cadPlantaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(cadPlantaServlet.class.getName()).log(Level.SEVERE, null, ex);
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
    
    
    protected boolean controllerValidarPlanta(HttpServletRequest request, HttpServletResponse response, Planta planta) throws ServletException, IOException
    {
        Validações va = new Validações();
        String msg = "";
        boolean error = false;
        
        if(!va.validarCadPlanta(planta).equals("")){           // validar os dados da planta
            msg = "Favor inserir: " + va.validarCadPlanta(planta);
            error = true;
        }
        
        if(va.validarCadSintomaPlanta(sintomas) == false){  // validar se há algum sintoma ao menos marcado
            msg = msg + "Favor inserir os sintomas!<br>";
            error = true;
        }
        
        if(va.validarCadRegiaoPlanta(regioes) == false){  // validar se há alguma regiao ao menos marcado
            msg = msg + "Favor inserir as regiões!<br>";
            error = true;
        }
        
        if(va.validarCadPartesPlanta(partesPlanta) == false){  // validar se há alguma parte ao menos marcado
            msg = msg + "Favor inserir as partes da planta!";
            error = true;
        }
        
        if(error == true){          // erro identificado nas validações, retorna mensagem ao usuário
            processarFalha(request, response, falha, msg, planta);
            return false;
        }
        else
            return true;            // retorna true para inserir no banco
    }

     protected boolean controllerInserirPlanta(HttpServletRequest request, HttpServletResponse response, Planta planta) throws ServletException, IOException, SQLException
     {
            // abre a conexão com o banco
            PlantaDAO dao = null;
            try{
            dao = new PlantaDAO();
            }catch(ClassNotFoundException e)
            {
                String msg = "Desculpe, não foi possível estabelecer conexão com o banco de dados!";
                processarFalha(request, response, falha, msg, planta);
                return false;
            }
            
            if(dao.inserirPlanta(planta, sintomas, regioes, partesPlanta) == true)
            {
                // imprime o sucesso
                String msg = "Planta " + planta.getNomePopular()+" inserida com sucesso!";
                processarResultado(request, response, sucesso, msg, planta);
                return true;
            }
            else
            {
                String msg = "Erro! Já existe uma planta com este nome no sistema!";
                processarFalha(request, response, falha, msg, planta);
                return false;
            }

    }
     
    protected void processarResultado(HttpServletRequest request, HttpServletResponse response, String css, String msg, Planta planta) throws ServletException, IOException
    {
        request.setAttribute("mensagem",css);
        request.setAttribute("mensagem2",msg);
        // Página a ser redirencionada
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);  
    }
    
        protected void processarFalha(HttpServletRequest request, HttpServletResponse response, String css, String msg, Planta planta) throws ServletException, IOException
    {
        request.setAttribute("mensagem",css);
        request.setAttribute("mensagem2",msg);
        request.setAttribute("nomePopular", planta.getNomePopular());
        request.setAttribute("nomeCientifico", planta.getNomeCientifico());
        request.setAttribute("principioAtivo", planta.getPrincipioAtivo());
        request.setAttribute("contraIndicacoes", planta.getContraIndicacoes());
        request.setAttribute("efeitosAdversos", planta.getEfeitosAdversos());
        request.setAttribute("modoDePreparo", planta.getModoDePreparo());
        // Página a ser redirencionada
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);  
    }
}
