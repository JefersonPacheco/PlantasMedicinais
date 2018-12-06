<%-- 
    Document   : editaUsuario
    Created on : 15/11/2017, 18:16:05
    Author     : Susulo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page errorPage = "LogoutServlet" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <%
        HttpSession ses2 = request.getSession();                                            // recupera a sessão
        if(ses2.getAttribute("perfil") == null){                                            // valida se o atributo da sessão está nulo, caso sim, foi feito logoff
            request.setAttribute("mensagem", "alert alert-danger");
            request.setAttribute("mensagem2", "Você precisa estar logado para continuar!");
            RequestDispatcher dis = request.getRequestDispatcher("login.jsp");              // retorna para tela de Login
            dis.forward(request, response);                 
        }
        if(!ses2.getAttribute("perfil").equals("Administrador")){                            // Admin não tem acesso a cadastrar planta
            RequestDispatcher dis = request.getRequestDispatcher("acessoNegado.jsp");       // retorna para tela de Acesso Negado
            dis.forward(request, response);     
        }
    %>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Editar Usuário</title>
    
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
                <div class="row">
                    <div class="col-md-12 centralizado">
                        <center><h2>Editar Usuário</h2></center>
                    </div>
                </div>
                                
                 <!-- /. ROW  -->
                <hr />
                <div class="row">
                    <div class="col-md-10 centralizado">
                        <!-- Form Elements -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                            Editar o Usuário
                            </div>
                        
                        <div class="panel-body">
                            <div class="row">
                            <br>
                            <div class="col-md-12 centralizado">
                                <div class="${mensagem}" role="alert">         <!-- recebe o estilo do CSS  -->
                                    <center><b>${mensagem2}</b></center>         <!-- recebe a mensagem a ser exibida  -->
                                </div>
                            </div>
                                <form role="form" action="editaUsuario" method="post">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label>Perfil:</label>
                                                <input class="form-control" name="Perfil" value="${perfil}" readonly />
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label></label>
                                                <input type="hidden" class="form-control" name="Id"  value="${id}" readonly/>
                                            </div>
                                        </div>
 
                                    <hr>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Nome*</label>
                                        <input class="form-control" name="Nome" value="${nome}" /><br>
                                        <label>E-mail*</label>
                                        <input class="form-control" name="Email" value="${email}"/><br>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Senha*</label>
                                        <input class="form-control" name="Senha" type="password" value="${senha}"/><br>
                                        <label>Digite a senha novamente*</label>
                                        <input class="form-control" name="Senha2" type="password" value="${senha}"/><br>
                                        <h3 align="right">
                                            <button type="submit" class="btn btn-primary">Alterar</button>
                                        </h3>
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