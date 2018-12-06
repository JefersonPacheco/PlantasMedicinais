<%-- 
    Document   : cadSugestao
    Created on : 04/09/2017, 16:26:39
    Author     : Jeferson Pacheco
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Principal.Usuario"%>
<%@page import="DAO.UsuarioDAO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Sugestão de Planta</title>
    <!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
    <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
    <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body onload="javascript:history.replaceState(null, null, 'cadSugestao.jsp')">
    <div id="wrapper">
        <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <span class="navbar-brand">TADS - 2018</span>  
            </div>
  <div style="color: white;
padding: 15px 50px 5px 50px;
float: right;
font-size: 16px;">Seja bem vindo(a)!&nbsp; 
        <a href="login.jsp" class="btn btn-success square-btn-adjust">Login&nbsp<i class="glyphicon glyphicon-log-in fa-1x"></i></a> </div>
        </nav>   
           <!-- /. NAV TOP  -->
            <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">	
                    <li class="text-center">
                        <a href="index.jsp"><img src="imagens/logo.png" /></a>
		    </li>
                     
                    <!-- plantas -->
                    <li>
                        <a href="index.jsp"><i class="fa fa-leaf fa-3x"></i>Consultar Plantas </a>
                    </li>
                    
                    <!-- Sugestão -->
                    <li>
                        <a  href="cadSugestao.jsp"><i class="glyphicon glyphicon-edit fa-3x"></i> Sugerir Planta</a>
                    </li>
                </ul>
            </div>
            
        </nav>  
        <!-- /. NAV SIDE  -->
       <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-10 centralizado">
                        <center><h2>Sugerir Planta</h2></center>
                    </div>
                </div>
                 <!-- /. ROW  -->
                <hr />
                <div class="col-md-10 centralizado">
                    <div class='${pageContext.session.getAttribute("mensagem")}' role="alert">         <!-- recebe o estilo do CSS  -->
                        <center><b>${pageContext.session.getAttribute("mensagem2")}</b></center>         <!-- recebe a mensagem a ser exibida  -->
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-10 centralizado">
                        <!-- Form Elements -->
                        <div class="panel panel-default " >
                            <div class="panel-heading">
                            Formulário de Sugestão
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <form action="sugestaoServlet" method="post" enctype="multipart/form-data">
                                    <div class="col-md-12 centralizado">
                                        <div class="${mensagem}" role="alert">         <!-- recebe o estilo do CSS  -->
                                            <center><b>${mensagem2}</b></center>         <!-- recebe a mensagem a ser exibida  -->
                                        </div>
                                    </div>
                                        <h3>&nbsp&nbspDigite as informações:</h3><br>
                                        <div class="col-md-12 centralizado">
                                            <div class="form-group">
                                                <label>Nome da Planta*</label>
                                                <input class="form-control" name="nome" value="${nomePlanta}"/><br>
                                                <label>Por favor, descreva a utilização da planta*</label>
                                                <textarea class="form-control" name="utilizacao"/>${utilizacao}</textarea><br>
                                                <label>Por favor, nos informe seu e-mail*</label>
                                                <input class="form-control" name="email" value="${emailUser}"/><br>
                                                    <div class="form-group">
                                                        <label>Insira uma imagem da planta se possível: (Max 5MB, formato .jpg ou .png)</label>
                                                        <input type="file" name="file" />
                                                    </div><br><br>
                                                <button type="submit" class="btn btn-primary">Enviar</button>
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