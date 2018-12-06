<%-- 
    Document   : cadSintoma
    Created on : 08/02/2018, 13:58:55
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
        HttpSession ses2 = request.getSession();                                            // recupera a sess�o
        if(ses2.getAttribute("perfil") == null){                                            // valida se o atributo da sess�o est� nulo, caso sim, foi feito logoff
            request.setAttribute("mensagem", "alert alert-danger");
            request.setAttribute("mensagem2", "Voc� precisa estar logado para continuar!");
            RequestDispatcher dis = request.getRequestDispatcher("login.jsp");              // retorna para tela de Login
            dis.forward(request, response);                 
        }
        if(ses2.getAttribute("perfil").equals("Administrador")){                            // Admin n�o tem acesso a cadastrar sintoma
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
    <title>Gerenciamento de Sintomas</title>
    
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
     <!-- TABLE STYLES-->
    <link href="assets/js/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
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
                <a class="navbar-brand">${pageContext.session.getAttribute("perfil")}</a> 
            </div>
  <div style="color: white;
padding: 15px 50px 5px 50px;
float: right;
font-size: 16px;">  
        <% // Pega o nome do usu�rio da sess�o para mostrar quem est� logado.
            HttpSession ses = request.getSession();              // recupera a sess�o
            String usuario = (String)ses.getAttribute("nome");   // atribui a String o nome que est� na sess�o
            String array[] = new String[1];                      // declara o vetor para receber somente o primeiro nome 
            array = usuario.split(" ");                          // vetor recebe o primeiro nome
            out.print(array[0]);                                 // mostra o primeiro nome na pagina
        %>, seja bem vindo(a)!&nbsp;  
        <a href='LogoutServlet' class="btn btn-success square-btn-adjust">Logout&nbsp<i class="glyphicon glyphicon-log-out fa-1x"></i></a></div>
        </nav>   
           <!-- /. NAV TOP  -->
            <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <jsp:include page='${pageContext.session.getAttribute("pagina")}' />    <!-- Chama o menu de acordo com o tipo de usu�rio salvo na sess�o -->
            </div>
            
        </nav>  
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <center><h2>Gerenciamento de Sintomas</h2></center>
                    </div>
                </div>
                <!-- /. ROW  -->
                <hr />
                <div class="row">
                    <div class="col-md-6">
                        <!-- Form Elements -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                            Cadastro de sintomas
                            </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="SintomaServlet" method="get">
                                    <h3>&nbsp&nbspDigite as informa��es:</h3><br>
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>Sintoma*</label>
                                        <input class="form-control" name="sintoma" /><br>
                                        <button type="submit" class="btn btn-primary" >Cadastrar</button><br>
                                    </div>
                                </div>
                                </form>
                            </div>
                        <!-- /. PAGE INNER  -->
                        </div>
                        <!-- /. PAGE WRAPPER  -->
                        <div class="${mensagem}" role="alert">
                            <b>${mensagem2}</b>
                        </div>
                        </div>
                    </div>
                    <!-- FIM DO CADASTRO -->
               
            <div class="row">
                <div class="col-md-6">
                    <!-- Advanced Tables -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Sintomas j� cadastrados
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>Sintomas</th>
                                            <th>A��o</th>
                                        </tr>
                                    </thead>
                                    
                                    <tbody>
                                    <jsp:useBean id="dao" class="DAO.SintomaDAO"/>
                                    <c:forEach var="u" items="${dao.lista}">
                                        <tr class="odd gradeX">
                                        <td>${u.sintoma}</td>
                                        <td align="center">
                                            <a href='SintomaServlet?id=${u.id}' class="confirm" ><button type="submit" class="btn btn-danger" >Excluir</button></a>
                                        </td>
                                        </tr>
                                    </c:forEach> <!-- N�o esquecer de importar a biblioteca JSTL -->
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
    
    <script src="assets/js/jquery.confirm.js"></script>
    <script src="assets/js/jquery.confirm.min.js"></script>
    <script>
        $(".confirm").confirm();
    </script>
         <!-- CUSTOM SCRIPTS -->
    <script src="assets/js/custom.js"></script>
    
   
</body>
</html>