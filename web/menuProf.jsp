<%-- 
    Document   : menuProf
    Created on : 06/02/2018, 16:29:27
    Author     : Jeferson Pacheco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
             
    <ul class="nav" id="main-menu">	
        <li class="text-center">
            <a href="avaliaPlanta.jsp"><img src="imagens/logo.png" /></a>
        </li>

        <!-- plantas -->
        <li>
            <a href="#"><i class="fa fa-leaf fa-3x"></i> Plantas <span class="fa arrow"></span></a>
            <ul class="nav nav-second-level">
                <li>
                    <a href="cadPlanta.jsp">Cadastrar</a>
                </li>
                <li>
                    <a href="avaliaPlanta.jsp">Aprovar Cadastro</a>
                </li>
                <li>
                    <a href="consultaPlanta.jsp">Consultar</a>
                </li>
                <li>
                    <a href="gerenciaPlanta.jsp">Gerenciar</a>
                </li>
            </ul>
         </li>  <!-- plantas -->

        <li>
            <a  href="cadSintoma.jsp"><i class="fa fa-ambulance fa-3x"></i> Sintomas</a>
        </li>

        <li>
            <a  href="consultaSugestao.jsp"><i class="glyphicon glyphicon-envelope fa-3x"></i> Consultar Sugestões</a>
        </li>

        <!-- Minha conta -->
        <li>
            <a href="#"><i class="fa fa-user fa-3x"></i> Minha conta <span class="fa arrow"></span></a>
            <ul class="nav nav-second-level">
                <li>
                    <a href="alterarSenha.jsp">Alterar Senha</a>
                </li>
            </ul>
        </li>  <!-- Minha conta -->
    </ul>
         
