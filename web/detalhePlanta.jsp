<%-- 
    Document   : detablePlanta
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
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Detalhamento de planta</title>
           <script type='text/javascript'> // refresh da pagina
    (function()
        {
          if( window.localStorage )
          {
            if( !localStorage.getItem('firstLoad') )
            {
              localStorage['firstLoad'] = true;
              window.location.reload();
            }  
            else
            {
              localStorage.removeItem('firstLoad');
            }
          }
        })();
    </script>
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
            if(ses.getAttribute("perfil") == null){              // valida se o atributo da sessão está nulo, caso sim, foi feito logoff
            request.setAttribute("mensagem", "alert alert-danger");
            request.setAttribute("mensagem2", "Você precisa estar logado para continuar!");
            RequestDispatcher dis = request.getRequestDispatcher("login.jsp");   // retorna para tela de Login
            dis.forward(request, response);
            }else{
            String usuario = (String)ses.getAttribute("nome");   // atribui a String o nome que está na sessão
            String array[] = new String[1];                      // declara o vetor para receber somente o primeiro nome 
            array = usuario.split(" ");                          // vetor recebe o primeiro nome
            out.print(array[0]);                                 // mostra o primeiro nome na pagina
            }
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
                <br>
                <div class="row">
                    <div class="col-md-10 centralizado">
                        <!-- Form Elements -->
                        <div class="panel panel-default " >
                            <div class="panel-heading">
                                <center><h2>Detalhes da Planta</h2></center>
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
                                            <label>Professor responsável:</label><br>  
                                            <p class="form-control-static">${professor}</p><br>
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
    <script src="assets/js/zoom.js"></script>
   
</body>
</html>
                                        
                                        