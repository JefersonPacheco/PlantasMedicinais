<%-- 
    Document   : Consulta Planta
    Created on : 19/10/2017, 14:06:33
    Author     : Jeferson Pacheco
--%>

<%@page import="Servlets.LogoutServlet"%>
<%@page import="java.lang.String"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Principal.Usuario"%>
<%@page import="DAO.UsuarioDAO"%>
<%@page session="false" %>
<%@ page errorPage = "error.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    LogoutServlet.encerrarSessao(request);
%>  
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>
    <meta charset="utf-8" />
    <meta http-equiv="Content-Type" content="text/html" charset="iso-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type='text/javascript'>
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
              localStorage.removeItem('firstLoad');
          }
        })();
</script>
    	<!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
     <!-- MORRIS CHART STYLES-->
    <title>Consulta de Plantas Medicinais</title>
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
     <!-- TABLE STYLES-->
    <link href="assets/js/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
</head>
    <body onload="javascript:history.replaceState(null, null, 'index.jsp')">
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
font-size: 16px;"> Seja bem vindo(a)! &nbsp;<a href="login.jsp" class="btn btn-success square-btn-adjust">Login&nbsp<i class="glyphicon glyphicon-log-in fa-1x"></i></a></div>
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
            <br>
                <div class="col-md-10 centralizado">
                    <div class="${mensagem}" role="alert">         <!-- recebe o estilo do CSS  -->
                        <center><b>${mensagem2}</b></center>         <!-- recebe a mensagem a ser exibida  -->
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <center><h2>Plantas Medicinais</h2></center>
                    </div>
                </div>
                
                <!-- Modal -->
<div class="modal fade responsivo" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Imagem da Planta</h4>
      </div>
      <div class="modal-body text-center">
        <img src="" height="500" width="540" />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Sair</button>
      </div>
    </div>
  </div>
</div>
                <!-- /. ROW  -->
                <hr />
               
            <div class="row">
                <div class="col-md-12">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Realize sua pesquisa:
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>Foto</th>
                                            <th>Nome Popular</th>
                                            <th>Nome Científico</th>
                                            <th>Principios Ativos</th>
                                            <th>Sintomas Amenizados</th>
                                            <th>Ações</th>
                                        </tr>
                                    </thead>
                                    
                                    <tbody>

                                    <jsp:useBean id="dao" class="DAO.PlantaDAO"/>
                                    <c:forEach var="u" items="${dao.consultarPlanta()}">
                                        <tr class="odd gradeX">
                                            <td><center><a href="#" parametro="imagem1.jpg" class="abrirModal"><img src=${u.local} height="50" width="50" alt="Planta" class="img-circle"/></a></center></td>
                                            <td>${u.nomePopular}</td>
                                            <td>${u.nomeCientifico}</td>
                                            <td>${u.principioAtivo}</td>
                                            <td>${u.sintomas}</td>
                                            <td><a href='PlantaServlet?id=${u.id}&pagina=detalhePlantaSL.jsp'><button type="submit" class="btn btn-success">Detalhes</button></a></td>
                                        </tr>
                                    </c:forEach> <!-- Não esquecer de importar a biblioteca JSTL -->
                                        
                                    </tbody>
                                </table>
                            </div>
                            
                        </div>
                    </div>
                    <!--End Advanced Tables -->
                </div>
            </div>
                                   
                <!-- /. ROW  -->
            </div>
               
        </div>
             <!-- /. PAGE INNER  -->
    </div>
         <!-- /. PAGE WRAPPER  -->
     <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="assets/js/jquery-1.10.2.js"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="assets/js/bootstrap.min.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="assets/js/jquery.metisMenu.js"></script>
     <!-- DATA TABLE SCRIPTS -->
    <script src="assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="assets/js/dataTables/dataTables.bootstrap.js"></script>
    <script>
            $(document).ready(function () {
                $('#dataTables-example').dataTable();
            });
    </script>
    <script>
        $(".abrirModal").click(function() {
            var url = $(this).find("img").attr("src");
            $("#myModal img").attr("src", url);
            $("#myModal").modal("show");
        });
    </script>
         <!-- CUSTOM SCRIPTS -->
    <script src="assets/js/custom.js"></script>


</SCRIPT>
   
</body>
</html>