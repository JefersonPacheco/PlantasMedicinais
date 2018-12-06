<%-- 
    Document   : detablePlantaSL (Sem login)
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
                <a class="navbar-brand" >TADS - 2018</a> 
            </div>
  <div style="color: white;
padding: 15px 50px 5px 50px;
float: right;
font-size: 16px;">         
        Seja bem vindo(a)!&nbsp;  
        <a href='login.jsp' class="btn btn-success square-btn-adjust">Login&nbsp<i class="glyphicon glyphicon-log-in fa-1x"></i></a> </div>
        </nav>   
           <!-- /. NAV TOP  -->
            <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">	
                    <li class="text-center">
                        <img src="imagens/logo.png" />
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
                        <center><h2>Detalhes da Planta</h2></center>
                    </div>
                </div>
                 <!-- /. ROW  -->
                <hr />
                <div class="row">
                    <div class="col-md-10 centralizado">
                        <!-- Form Elements -->
                        <div class="panel panel-default " >
                            <div class="panel-heading">
                            <h4>Informações cadastradas</h4>
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
                                            </div><hr>
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
