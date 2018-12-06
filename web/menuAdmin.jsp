<%-- 
    Document   : menuAdmin
    Created on : 08/02/2018, 11:11:18
    Author     : Jeferson Pacheco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
             
    <ul class="nav" id="main-menu">	
        <li class="text-center">
            <a href="gerenciaUsuario.jsp"><img src="imagens/logo.png" /></a>
        </li>
        <!-- pesquisadores -->
        <li>
            <a href="#"><i class="fa fa-users fa-3x"></i> Usuários <span class="fa arrow"></span></a>
            <ul class="nav nav-second-level">
                <li>
                    <a href="cadUsuario.jsp">Cadastrar</a>
                </li>
                <li>
                    <a href="gerenciaUsuario.jsp">Gerenciar</a>
                </li>
            </ul>
        </li>  <!-- pesquisadores -->

        <!-- plantas -->
        <li>
            <a href="consultaPlanta.jsp"><i class="fa fa-leaf fa-3x"></i>Consultar Plantas </a>
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
