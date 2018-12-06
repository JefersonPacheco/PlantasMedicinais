<%-- 
    Document   : cadPlanta
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
        if(ses2.getAttribute("perfil").equals("Administrador")){                            // Admin não tem acesso a cadastrar planta
            RequestDispatcher dis = request.getRequestDispatcher("acessoNegado.jsp");       // retorna para tela de Acesso Negado
            dis.forward(request, response);     
        }
    %>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Cadastro de Planta</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body onload="javascript:history.replaceState(null, null, 'cadPlanta.jsp')">
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
                                <center><h2>Cadastrar Planta Medicinal</h2></center>
                            </div>
                        <div class="panel-body">
                            <div class="row">
                                <form role="form" action="cadPlantaServlet" method="post" enctype="multipart/form-data" >
                                <h3>&nbsp&nbspDigite as informações:</h3><br>
                                <div class="col-md-12 centralizado">
                                    <div class="form-group">
                                        <label>Nome Popular*</label>
                                        <input class="form-control" name="nomePopular" value="${nomePopular}" /><br>
                                        <label>Nome Científico*</label>
                                        <input class="form-control" name="nomeCientifico" value="${nomeCientifico}"/><br>
                                        <label>Princípios Ativos*</label>
                                        <input class="form-control" name="principioAtivo" value="${principioAtivo}" /><br>
                                        <label>Contra Indicações*</label>
                                        <input class="form-control" name="contraIndicacoes" value="${contraIndicacoes}" /><br>

                                        <label>Efeitos Adversos*</label>
                                        <textarea class="form-control" name="efeitosAdversos" />${efeitosAdversos}</textarea><br>
                                        <label>Modo de Preparo*</label>
                                        <textarea class="form-control" name="modoDePreparo" />${modoDePreparo}</textarea><br>
                                        
                                        <div class="form-group"> <!-- checkbox sintomas-->
                                            <br><label>Sintomas amenizados pela planta*</label><br>
                                            
                                            <jsp:useBean id="dao" class="DAO.SintomaDAO"/>
                                            <c:forEach var="u" items="${dao.lista}">
                                            <div class="checkbox-inline">
                                                <label>
                                                     <input type="checkbox" value="${u.sintoma}" name="sintoma" /> ${u.sintoma}
                                                </label>
                                            </div>
                                            </c:forEach> <!-- Não esquecer de importar a biblioteca JSTL -->
                                        </div> <!-- checkbox sintomas -->
    

                                        <div class="form-group"> <!-- checkbox partes planta -->
                                        <br><label>Partes Utilizadas da Planta*</label><br>
                                            <jsp:useBean id="dao2" class="DAO.PlantaDAO"/>
                                            <c:forEach var="u" items="${dao2.listaPartesPlanta}">
                                            <div class="checkbox-inline">
                                                 <label>
                                                    <input type="checkbox" value="${u.partesPlanta}" name="partesPlanta" />${u.partesPlanta}
                                                </label>
                                            </div>
                                            </c:forEach> <!-- Não esquecer de importar a biblioteca JSTL -->
                                        </div> <!-- checkbox partes planta -->
                                        
                                        <div class="form-group"> <!-- checkbox região -->
                                            <br><label>Regiões*</label><br>
                                            <jsp:useBean id="dao3" class="DAO.PlantaDAO"/>
                                            <c:forEach var="u" items="${dao2.listaRegioesPlanta}">
                                            <div class="checkbox-inline">
                                                <label>
                                                    <input type="checkbox" value="${u.regioes}" name="regioes" />${u.regioes}
                                                </label>
                                            </div>
                                            </c:forEach> <!-- Não esquecer de importar a biblioteca JSTL -->
                                            </div><br>
                                        <div class="form-group">
                                            <label>Insira uma imagem da planta: (Max 5MB, formato .jpg ou .png)</label>
                                            <input type="file" name="file"/>
                                        </div>
                                        <br><button type="submit" class="btn btn-primary">Cadastrar</button>
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