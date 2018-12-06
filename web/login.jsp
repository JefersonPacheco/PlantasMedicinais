<%-- 
    Document   : index
    Created on : 16/08/2017, 13:13:02
    Author     : Jeferson Pacheco
--%>

<%@page import="Servlets.LogoutServlet"%>
<%@page import="DAO.PlantaDAO"%>
<%@page import="Principal.Planta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
  <title>Login - Plantas Medicinais</title>
  
<link rel="stylesheet" href="assets/css/bootstrap.css">
<link rel="stylesheet" href="assets/css/style.css">
<script language="JavaScript" src="js/Alertas.js"></script>
</head>

<body onload="javascript:history.replaceState(null, null, 'login.jsp')">
    <div class="responsivo">
        <img src ="imagens/azul-faixa8.png" style="width: 100%" >
    </div>

    <div class="wrapper">
        <form class="form-signin" action="Login" method="POST">       
            <h2 class="form-signin-heading">Login</h2>
            
            <input type="text" class="form-control" name="email" placeholder="Email" required="" autofocus="" />
            <input type="password" class="form-control" name="Senha" placeholder="Senha" required=""/> 
            <button class="btn btn-lg btn-success btn-block" type="submit">Entrar</button><br>
            <a href="esqueciSenha.jsp">Esqueci a senha<a/><br><br>
            <div class="${mensagem}" role="alert">
                <font color='red'>${mensagem2}</font>     <!-- Chama a mensagem do servlet em caso de erro -->
            </div>
        </form>
    </div>
    <hr>
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <center><p>UMC - 2018</p></center>
                </div>
            </div>
        </footer> 
</body>
</html>