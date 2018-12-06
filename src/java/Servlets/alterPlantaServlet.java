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
import java.io.PrintWriter;
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
@WebServlet(name = "alterPlantaServlet", urlPatterns = {"/alterPlantaServlet"})
public class alterPlantaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String pagina = "gerenciaPlanta.jsp";
    final String sucesso = "alert alert-success";
    final String falha = "alert alert-danger";
    String[] sintomas;
    String[] regioes;
    String[] partesPlanta;
    String[] foto = null;
    final String local = "imgCadastros/";
    final String dirExcluir = "C:\\Users\\User\\Documents\\NetBeansProjects\\Teste\\web\\"; 
    
   
    
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
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            Validações va = new Validações();
            HttpSession ses = request.getSession();              // recupera a sessão

            // pega os parametros enviados pelo formulário de cadastro
            String nomePopular = request.getParameter("nomePopular");        
            String nomeCientifico = request.getParameter("nomeCientifico"); 
            String principioAtivo = request.getParameter("principioAtivo"); 
            String contraIndicacoes = request.getParameter("contraIndicacoes"); 
            String efeitosAdversos = request.getParameter("efeitosAdversos");   
            String modoDePreparo = request.getParameter("modoDePreparo");
            String id = request.getParameter("id");
            sintomas = request.getParameterValues("sintoma");          
            regioes = request.getParameterValues("regioes");          
            partesPlanta = request.getParameterValues("partesPlanta");          
            foto = request.getParameterValues("foto");

            // monta um objeto de Planta, sintomas
            Planta planta = new Planta();
            planta.setNomePopular(nomePopular);
            planta.setNomeCientifico(nomeCientifico);
            planta.setContraIndicacoes(contraIndicacoes);
            planta.setEfeitosAdversos(efeitosAdversos);
            planta.setPrincipioAtivo(principioAtivo);
            planta.setModoDePreparo(modoDePreparo);
            planta.setId(id);
            planta.setFeedback("");
                            
            if(ses.getAttribute("perfil").equals("Professor")){     // se for professor que cadastrou, aprova direto
                planta.setStatus("Aprovada");                   // se é professor, planta já é aprovada automaticamente
            }
            else{
                planta.setStatus("Pendente");               // se não for professor, planta fica pendente
                planta.setFeedback("<i>Aguardando avaliação</i>");
            }
            
            Part filePart = request.getPart("file"); // pega o <input type="file" name="file">
            String nomeArquivo = filePart.getSubmittedFileName().toLowerCase().replace(" ","");   // pega o nome do arquivo, inclusive extenção
            
            if(!nomeArquivo.equals("")){                                // Se o arquivo no formulário for diferente de vazio, valida o nome  dele
                if(va.validarTipoArquivo(nomeArquivo) == false){        // valida se é do tipo imagem
                    processarFalha(request, response, falha, "O arquivo para imagem da planta não é válido, favor inserir .jpg ou .png", planta);
                    return;
                }    
                
                if(filePart.getSize() > 5000000)   // se Arquivo for maior que 5 megas mostra erro
                {
                    processarFalha(request, response, falha, "Favor inserir um arquivo com menos de 5 MB.", planta);
                    return;
                }
                
                try { // se for o tipo da imagem for correto, salva ela no banco
                    nomeArquivo = id.concat(".jpg").replace(" ","");
                    if(!nomeArquivo.equals(""))
                        planta.setLocal(local+nomeArquivo);
                    else
                        planta.setLocal(null);
                } catch(Exception e){
                    planta.setLocal(null);
                }
            }
            // validar os dados que o usuario inseriu
            if (controllerValidarPlanta(request, response, planta) == true)
            {
                try {
                    // inserir os dados validados no banco de dados
                    if(controllerAlterarPlanta(request, response, planta) == true){
                        if(planta.getLocal() != null){ // se a imagem não for nula, salva no diretorio
                            String fileName = Paths.get(planta.getLocal()).getFileName().toString();      // pega o nome do arquivo mais o diretorio
                            filePart.write(fileName);
                        }
                        String msg = "Planta '" + planta.getNomePopular()+ "' alterada com sucesso!";
                        processarResultado(request, response, sucesso, msg, planta);
                    }
                    else 
                        return;
                } catch (SQLException ex) {
                    Logger.getLogger(cadPlantaServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
    }
    
    
    protected boolean controllerValidarPlanta(HttpServletRequest request, HttpServletResponse response, Planta planta) throws ServletException, IOException
    {
        Validações va = new Validações();
        String msg = "";
        boolean error = false;
        
        if(!va.validarCadPlanta(planta).equals("")){           // validar os dados da planta
            msg = "Favor inserir: " + va.validarCadPlanta(planta) + "<br>";
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

    protected boolean controllerAlterarPlanta(HttpServletRequest request, HttpServletResponse response, Planta planta) throws ServletException, IOException, SQLException
    {
        // abre a conexão com o banco
        PlantaDAO dao = null;
        try{
        dao = new PlantaDAO();
        }catch(Exception e)
        {
            String msg = "Desculpe, não foi possível estabelecer conexão com o banco de dados!";
            processarFalha(request, response, falha, msg, planta);
            return false;
        }

        if(dao.alterarPlanta(planta, sintomas, regioes, partesPlanta) == true)
        {
            return true;
        }
        else
        {
            String msg = "Ocorreu erro ao alterar o banco de dados!";
            processarFalha(request, response, falha, msg, planta);
            return false;
        }

    }
     
    protected void processarResultado(HttpServletRequest request, HttpServletResponse response, String css, String msg, Planta planta) throws ServletException, IOException
    {
        
        request.setAttribute("mensagem",css);
        request.setAttribute("mensagem2",msg);

        HttpSession ses2 = request.getSession();                                            // recupera a sessão
        
        System.out.println(ses2.getAttribute("perfil"));
        if(ses2.getAttribute("perfil").equals("Assistente")){                               // Admin não tem acesso a cadastrar planta
            RequestDispatcher dis = request.getRequestDispatcher("plantasPendentes.jsp");
            dis.forward(request, response);
        }
        if(ses2.getAttribute("perfil").equals("Professor")){                               // Admin não tem acesso a cadastrar planta
            RequestDispatcher dis = request.getRequestDispatcher(pagina);
            dis.forward(request, response);
        } 

    }
    
    protected void processarFalha(HttpServletRequest request, HttpServletResponse response, String css, String msg, Planta planta) throws ServletException, IOException
    {
        pagina = "PlantaServlet?id="+planta.getId()+"&pagina=editar";
        
        request.setAttribute("mensagem",css);
        request.setAttribute("mensagem2",msg);
        request.setAttribute("nomePopular", planta.getNomePopular());
        request.setAttribute("nomeCientifico", planta.getNomeCientifico());
        request.setAttribute("principioAtivo", planta.getPrincipioAtivo());
        request.setAttribute("contraIndicacoes", planta.getContraIndicacoes());
        request.setAttribute("efeitosAdversos", planta.getEfeitosAdversos());
        request.setAttribute("modoDePreparo", planta.getModoDePreparo());
        
        HttpSession ses2 = request.getSession();                                            // recupera a sessão

        if(ses2.getAttribute("perfil").equals("Assistente")){                               // Admin não tem acesso a cadastrar planta
            pagina = "PlantaServlet?id="+planta.getId()+"&pagina=editar";
        }
        
        // Página a ser redirencionada
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);  
    }

    protected void processarResultado(HttpServletRequest request, HttpServletResponse response, String css, String msg, String pagina) throws ServletException, IOException
    {
        request.setAttribute("mensagem",css);
        request.setAttribute("mensagem2",msg);
        
        // Página a ser redirecionada
        RequestDispatcher dis = request.getRequestDispatcher(pagina);   
        dis.forward(request, response);  
    }
        
}
