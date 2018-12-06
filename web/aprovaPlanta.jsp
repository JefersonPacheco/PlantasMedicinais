<%-- 
    Document   : AprovaPlanta
    Created on : 04/09/2017, 16:26:39
    Author     : Jeferson Pacheco
--%>
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
        if(!ses2.getAttribute("perfil").equals("Professor")){                               // verifica se o usuário não é Professor
            RequestDispatcher dis = request.getRequestDispatcher("acessoNegado.jsp");       // retorna para tela de Acesso Negado
            dis.forward(request, response);     
        }
    %>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Avaliação de planta</title>
    
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
        <a href='LogoutServlet' class="btn btn-success square-btn-adjust">Logout&nbsp<i class="glyphicon glyphicon-log-out fa-1x"></i></a> </div>
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

                <!-- Modal -->
                <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                  <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="exampleModalLabel">Digite seu feedback para o Assistente:</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>            
                        <form action="FeedbackServlet" method="post">
                            <div class="modal-body">
                                <div class="form-group">
                                    <label for="message-text" class="col-form-label">Mensagem:</label>
                                    <textarea class="form-control" id="message-text" name="feedback"></textarea>
                                </div>
                                <div class="form-group">
                                    <label></label>
                                    <input type="hidden" class="form-control" name="id" value="${id}" readonly/>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                <button type="submit" class="btn btn-primary">Enviar Feedback</button>
                            </div>
                        </form>
                    </div>
                  </div>
                </div>  
                 <!-- /. ROW  -->
                <br>
                <div class="col-md-10 centralizado">
                    <div class="${mensagem}" role="alert">         <!-- recebe o estilo do CSS  -->
                        <center><b>${mensagem2}</b></center>         <!-- recebe a mensagem a ser exibida  -->
                    </div>
                </div>      
                <div class="row">
                    <div class="col-md-10 centralizado">
                        <!-- Form Elements -->
                        <div class="panel panel-default " >
                            <div class="panel-heading">
                                <h4 align="middle">Avalie o cadastro e escolha uma opção:</h4> 
                                <h4 align="middle">
                                <a href='PlantaServlet?id=${id}&pagina=aprovar'><button type="submit" class="btn btn-success">Aprovar</button></a>
                                <a href='PlantaServlet?id=${id}&pagina=editar3'><button type="submit" class="btn btn-primary">Editar</button></a>
                                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#exampleModal">Retornar</button>
                                </h4>
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <form role="form" action="cadPlantaServlet">
                                        <!--<h3>&nbsp&nbspDetalhes da Planta:</h3><br>-->
                                    <div class="col-md-12 centralizado">
                                        <div class="form-group">
                                            <br>
                                            <div class="centralizado">
                                                <center><img src=${local} height="350" width="350" alt="Planta" class="img-rounded" /></center>
                                            </div><hr/>
                                            <label>Nome Popular:</label>
                                            <p class="form-control-static">${nomePopular}</p><br>
                                            <label>Nome Científico:</label>
                                            <p class="form-control-static">${nomeCientifico}</p><br>
                                            <label>Princípios Ativos:</label>
                                            <p class="form-control-static">${principioAtivo}</p><br>
                                            <label>Contra Indicações:</label>
                                            <p class="form-control-static">${contraIndicacoes}</p><br>
                                            <label>Efeitos Adversos: </label>
                                            <p class="form-control-static">${efeitosAdversos}</p><br>
                                            <label>Modo de Preparo</label>:
                                            <p class="form-control-static">${modoDePreparo}</p></textarea><br>
                                            <label>Sintomas amenizados pela planta:</label><br>
                                            <p class="form-control-static">${sintomas}</p></textarea><br>
                                            <label>Partes Utilizadas da Planta:</label><br>
                                            <p class="form-control-static">${partesPlanta}</p></textarea><br>
                                            <label>Regiões:</label><br>                
                                            <p class="form-control-static">${regioes}</p></textarea><br>

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