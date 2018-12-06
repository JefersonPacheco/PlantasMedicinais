<%-- 
    Document   : editaPlantaCF
    Created on : 21/03/2018, 16:06:38
    Author     : Jeferson Pacheco
--%>

<%@page import="Principal.Planta"%>
<%@page import="DAO.PlantaDAO"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="Principal.Sintoma"%>
<%@page import="DAO.SintomaDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Principal.Usuario"%>
<%@page import="DAO.UsuarioDAO"%>
<%@ page errorPage = "LogoutServlet" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%
        HttpSession ses2 = request.getSession();                                            // recupera a sessão
        if(ses2.getAttribute("perfil") == null){                                            // valida se o atributo da sessão está nulo, caso sim, foi feito logoff
            request.setAttribute("mensagem", "alert alert-danger");
            request.setAttribute("mensagem2", "Você precisa estar logado para continuar!");
            RequestDispatcher dis = request.getRequestDispatcher("login.jsp");              // retorna para tela de Login
            dis.forward(request, response);                 
        }
    %>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Editar Planta</title>
    
	<!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>
    <div id="wrapper">
        <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" >${pageContext.session.getAttribute("perfil")}</a> 
            </div>
  <div style="color: white;
padding: 15px 50px 5px 50px;
float: right;
font-size: 16px;">        
        <% // Pega o nome do usuário da sessão para mostrar quem está logado.
            HttpSession ses = request.getSession();              // recupera a sessão
            String usuario = (String)ses.getAttribute("nome");   // atribui a String o nome que está na sessão
            String array[] = new String[1];                      // declara o vetor para receber somente o primeiro nome 
            array = usuario.split(" ");                          // vetor recebe o primeiro nome
            out.print(array[0]);                                 // mostra o primeiro nome na pagina
        %>, seja bem vindo(a)!&nbsp; 
        <a href='LogoutServlet' class="btn btn-success square-btn-adjust">Logout&nbsp<i class="glyphicon glyphicon-log-out fa-1x"></i></a></div>
        </nav>   
           <!-- /. NAV TOP  -->
            <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <jsp:include page='${pageContext.session.getAttribute("pagina")}' />    <!-- Chama o menu de acordo com o tipo de usuário -->               
            </div>
            
        </nav>  
        <!-- /. NAV SIDE  -->
       <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-10 centralizado">
                        <center><h2>Editar Planta</h2></center>
                    </div>
                </div>
                 <!-- /. ROW  -->
                <hr />
                <div class="col-md-10 centralizado">
                    <div class="${mensagem}" role="alert">         <!-- recebe o estilo do CSS  -->
                        <center><b>${mensagem2}</b></center>         <!-- recebe a mensagem a ser exibida  -->
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-8 centralizado">
                        <!-- Form Elements -->
                        <div class="panel panel-danger " >
                            <div class="panel-heading">
                                <b>Feedback do Professor</b>
                            </div>
                        <div class="panel-body">
                            <div class="row">
                                <p>&nbsp;&nbsp;${comentario}</p>
                            </div>
                        </div>
                        </div>  
                    </div>    
                </div>
                    
                <div class="row">
                    <div class="col-md-10 centralizado">
                        <!-- Form Elements -->
                        <div class="panel panel-default " >
                            <div class="panel-heading">
                            Formulário
                            </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="alterPlantaServlet" method="post" enctype="multipart/form-data" >
                                    <h3>&nbsp&nbspDigite as informações:</h3><br>
                                <div class="col-md-12 centralizado">
                                    <div class="form-group">
                                        <input class="hidden" name="id" value="${id}" />
                                        <label>Nome Popular*</label>
                                        <input class="form-control" name="nomePopular" value="${nomePopular}" /><br>
                                        <label>Nome Científico*</label>
                                        <input class="form-control" name="nomeCientifico" value="${nomeCientifico}" /><br>
                                        <label>Princípios Ativos*</label>
                                        <input class="form-control" name="principioAtivo" value="${principioAtivo}" /><br>
                                        <label>Contra Indicações*</label>
                                        <input class="form-control" name="contraIndicacoes"  value="${contraIndicacoes}" /><br>

                                        <label>Efeitos Adversos*</label>
                                        <textarea class="form-control" name="efeitosAdversos" />${efeitosAdversos}</textarea><br>
                                        <label>Modo de Preparo*</label>
                                        <textarea class="form-control" name="modoDePreparo" />${modoDePreparo}</textarea><br>
                                        
                                        <div class="form-group"> <!-- checkbox sintomas-->
                                            <br><label>Sintomas amenizados pela planta*</label><br>
                                            <% 
                                               SintomaDAO s = new SintomaDAO();
                                               List<Sintoma> sintomas;                                  // Cria uma lista de sintomas
                                               String id = request.getAttribute("id").toString();       // pega o ID da planta
                                               sintomas = s.getListaSintChecked(id);                    // chama o metodo para pegar os sintomas flegados
                                          
                                               for(Sintoma ss : sintomas)                               // varrer a lista de sintomas
                                               {
                                                   out.print("<div class=\"checkbox-inline\">");
                                                   out.print("<label>");
                                                   out.print("<input type=\"checkbox\" value='" +ss.getSintoma()+ "' name=\"sintoma\" "+ss.getChecked()+" /> "+ss.getSintoma()+"");
                                                   out.print("</label>");
                                                   out.print("</div>");
                                               }
                                            %>
                                        </div>

                                        <div class="form-group"> <!-- checkbox partes planta -->
                                        <br><label>Partes Utilizadas da Planta*</label><br>
                                            <% 
                                               PlantaDAO p = new PlantaDAO();
                                               List<Planta> partes;                                  // Cria uma lista de sintomas
                                               String id2 = request.getAttribute("id").toString();       // pega o ID da planta
                                               partes = p.getListaPartesChecked(id2);                    // chama o metodo para pegar os sintomas flegados
                                          
                                               for(Planta pp : partes)                               // varrer a lista de partes
                                               {
                                                   out.print("<div class=\"checkbox-inline\">");
                                                   out.print("<label>");
                                                   out.print("<input type=\"checkbox\" value='" + pp.getPartesPlanta()+ "' name=\"partesPlanta\" "+ pp.getParteChecked()+" /> "+pp.getPartesPlanta()+"");
                                                   out.print("</label>");
                                                   out.print("</div>");
                                               }
                                            %>
                                        </div> <!-- checkbox partes planta -->
                                        
                                        <div class="form-group"> <!-- checkbox região -->
                                            <br><label>Regiões*</label><br>
                                            <% 
                                               PlantaDAO dao = new PlantaDAO();
                                               List<Planta> regioes;                                       // Cria uma lista de regiões
                                               String idPlanta = request.getAttribute("id").toString();    // pega o ID da planta
                                               regioes = dao.getListaRegioesChecked(idPlanta);               // chama o metodo para pegar os sintomas flegados
                                               
      
                                               for(Planta pp : regioes)                               // varrer a lista de regioes
                                               {
                                                   out.print("<div class=\"checkbox-inline\">");
                                                   out.print("<label>");
                                                   out.print("<input type=\"checkbox\" value='" + pp.getRegioes() + "' name=\"regioes\" "+ pp.getRegiaoChecked()+" /> "+pp.getRegioes()+"");
                                                   out.print("</label>");
                                                   out.print("</div>");
                                               }
                                            %>
                                            </div><br>
                                        <hr>
                                        <div class="centralizado">
                                            <label>Imagem Atual:</label>
                                            <center><img src=${local} height="250" width="250" alt="Planta" class="img-rounded" /></center>
                                        </div><hr>
                                        <div class="form-group">
                                            <label>Insira aqui uma imagem se deseja alterá-la: (Max 5MB, formato .jpg ou .png)</label>
                                            <input type="file" name="file"/>
                                        </div>
                                        <br><button type="submit" class="btn btn-primary">Salvar</button>
                                    </div>
                                </div>
                                </form>
                            </div>
                        <!-- /. PAGE INNER  -->
                        </div>
                        <!-- /. PAGE WRAPPER  -->

                        </div>
                    </div>
                </div>
            </div>
            </div>
     <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="assets/js/jquery-1.10.2.js"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="assets/js/bootstrap.min.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="assets/js/jquery.metisMenu.js"></script>
      <!-- CUSTOM SCRIPTS -->
    <script src="assets/js/custom.js"></script>
    
   
</body>
</html>