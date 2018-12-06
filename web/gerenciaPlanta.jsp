<%-- 
    Document   : gerenciaPlanta
    Created on : 23/02/2018, 11:11:23
    Author     : Jeferson Pacheco
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Principal.Usuario"%>
<%@page import="DAO.UsuarioDAO"%>
<%@ page errorPage = "LogoutServlet" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script language="JavaScript" src="js/Alertas.js"></script>
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
    <meta charset="utf-8" />
    <meta http-equiv="Content-Type" content="text/html" charset="iso-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    	<!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
     <!-- MORRIS CHART STYLES-->
    <title>Gerenciamento de Plantas Medicinais</title>
    
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
     <!-- TABLE STYLES-->
    <link href="assets/js/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
</head>
    <body onload="javascript:history.replaceState(null, null, 'gerenciaPlanta.jsp')">
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
                <jsp:include page='${pageContext.session.getAttribute("pagina")}' />    <!-- Chama o menu de acordo com o tipo de usuário salvo na sessão -->
            </div>
            
        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <center><h2>Plantas Medicinais Cadastradas</h2></center>
                    </div>
                </div>
                <!-- /. ROW  -->
                <hr />
            <div class="col-md-12 centralizado">
                <div class="${mensagem}" role="alert">         <!-- recebe o estilo do CSS  -->
                    <center><b>${mensagem2}</b></center>         <!-- recebe a mensagem a ser exibida  -->
                </div>
            </div>   
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
                                            <td>${u.nomePopular}</td>
                                            <td>${u.nomeCientifico}</td>
                                            <td>${u.principioAtivo}</td>
                                            <td>${u.sintomas}</td>
                                            <td><a href='PlantaServlet?id=${u.id}&pagina=editar'><button type="submit" class="btn btn-primary">Editar</button></a>
                                                <a href='PlantaServlet?id=${u.id}&pagina=excluir' class="confirm" ><button type="submit" class="btn btn-danger">Excluir</button></a>
                                            </td>
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
         <!-- CUSTOM SCRIPTS -->
    <script src="assets/js/custom.js"></script>
    
    <script src="assets/js/jquery.confirm.js"></script>
    <script src="assets/js/jquery.confirm.min.js"></script>
    <script>
        $(".confirm").confirm();
    </script>
   
</body>
</html>
