<%-- 
    Document   : menuAssistente
    Created on : 26/02/2018, 16:53:15
    Author     : Jeferson Pacheco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
             
    <ul class="nav" id="main-menu">	
        <li class="text-center">
            <a href="cadPlanta.jsp"><img src="imagens/logo.png" /></a>
        </li>

        <!-- plantas -->
        <li>
            <a href="#"><i class="fa fa-leaf fa-3x"></i> Plantas <span class="fa arrow"></span></a>
            <ul class="nav nav-second-level">
                <li>
                    <a href="cadPlanta.jsp">Cadastrar</a>
                </li>
                <li>
                    <a href="plantasPendentes.jsp">Cadastros Pendentes</a>
                </li>
                <li>
                    <a href="consultaPlanta.jsp">Consultar</a>
                </li>
            </ul>
         </li>  <!-- plantas -->

        <li>
            <a  href="cadSintoma.jsp"><i class="fa fa-ambulance fa-3x"></i> Sintomas</a>
        </li>
        
        <!-- pesquisadores -->
        <li>
            <a href="#"><i class="fa fa-user fa-3x"></i> Minha conta <span class="fa arrow"></span></a>
            <ul class="nav nav-second-level">
                <li>
                    <a href="alterarSenha.jsp">Alterar Senha</a>
                </li>
            </ul>
        </li>  <!-- pesquisadores -->

    </ul>
