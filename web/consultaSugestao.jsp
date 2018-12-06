<%-- 
    Document   : Consulta Planta
    Created on : 19/10/2017, 14:06:33
    Author     : Jeferson Pacheco
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Principal.Sugestao"%>
<%@page import="DAO.SugestaoDAO"%>
<%@ page errorPage = "LogoutServlet" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
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
    <meta http-equiv="Content-Type" content="text/html" charset="iso-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    	<!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
     <!-- MORRIS CHART STYLES-->
    <title>Consulta Sugestão</title>
    
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
     <!-- TABLE STYLES-->
    <link href="assets/js/dataTables/dataTables.bootstrap.css" rel="stylesheet" />
</head>
<body onload="javascript:history.replaceState(null, null, 'consultaSugestao.jsp')">
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
                    <div class="col-md-12">
                        <center><h2>Sugestões de Plantas Medicinais</h2></center>   
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
                <div class="col-md-10 centralizado">
                    <div class="${mensagem}" role="alert">         <!-- recebe o estilo do CSS  -->
                        <center><b>${mensagem2}</b></center>         <!-- recebe a mensagem a ser exibida  -->
                    </div>
                </div>
                <!-- /. ROW  -->
                <!-- /. ROW  -->
                <div class="row">
                    <div class="col-md-10 col-sm-12 col-xs-12 centralizado">
                   
                    <div class="chat-panel panel panel-default chat-boder chat-panel-head" >
                        <div class="panel-heading">
                            <i class="fa fa-comments fa-fw"></i>
                            Sugestões de Usuários
                        </div>
                        
                        <div class="panel-body">
                            <ul class="chat-box">
                                <jsp:useBean id="dao" class="DAO.SugestaoDAO"/>
                                <c:forEach var="u" items="${dao.lista}">
                                <li class="left clearfix">
                                    <span class="chat-img pull-left">
                                        <a href="#" parametro="imagem1.jpg" class="abrirModal"><img src=${u.local} height="80" width="80" alt="Planta" class="img-rounded" /></a>
                                    </span>
                                    <div class="chat-body">
                                        <strong >&nbsp&nbspNome da planta: ${u.nomePlanta} <br> 
                                                 &nbsp&nbspEnviado por:  ${u.email}</strong>
                                            <small class="pull-right text-muted">
                                                <i class="fa fa-clock-o fa-fw"></i>
                                                 ${u.data}
                                                <h1 align="right">
                                                    <a href='sugestaoServlet?id=${u.id}&local=${u.local}' class="confirm" >
                                                        <button type="submit" class="btn btn-default" >Excluir</button>
                                                    </a>
                                                </h1>
                                            </small>                                      
                                        <p>
                                            &nbsp&nbsp${u.utilizacao} 
                                        </p>
                                    </div>
                                </li> 
                                </c:forEach>
                            </ul>
                        </div>                       
                        <div class="panel-footer">
                        </div>
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
    <script src="assets/js/jquery.confirm.js"></script>
    <script src="assets/js/jquery.confirm.min.js"></script>
     <!-- DATA TABLE SCRIPTS -->
    <script src="assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="assets/js/dataTables/dataTables.bootstrap.js"></script>
    <script>
        $(document).ready(function () {
            $('#dataTables-example').dataTable();
        });
    </script>
    <script type="text/javascript">
            function confirma() {
                if(window.confirm("Deseja mesmo excluir a sugestão?")) {
                    document.form["formulario"].submit();
                }
            }
    </script>
    <script>
        $(".abrirModal").click(function() {
            var url = $(this).find("img").attr("src");
            $("#myModal img").attr("src", url);
            $("#myModal").modal("show");
        });
    </script>
    <script>
        $(".confirm").confirm();
    </script>
         <!-- CUSTOM SCRIPTS -->
    <script src="assets/js/custom.js"></script>

</body>
</html>
         
