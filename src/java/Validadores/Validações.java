/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validadores;

import Principal.Planta;
import Principal.Sintoma;
import Principal.Sugestao;
import Principal.Usuario;

/**
 *
 * @author Jeferson Pacheco
 */
public class Validações {
    
    public String validarCadUsuario(Usuario usuario){              // verifica se tem campos vazios
        String msg = "";
        if(usuario.getNome().trim().equals(""))
           msg = msg + "Favor inserir o nome!<br>";     
        if(usuario.getNome().length() > 100)
           msg = msg + "O nome deve conter até 100 caracteres!<br>"; 
        
        if(usuario.getEmail().trim().equals(""))
           msg = msg + "Favor inserir o email!<br>";
        if(usuario.getEmail().length() > 1000)
           msg = msg + "O e-mail deve conter até 1000 caracteres!<br>"; 
                
        if(usuario.getSenha().trim().equals("") || usuario.getSenha2().trim().equals(""))
           msg = msg + "Favor inserir as senhas!<br>"; 
        if(usuario.getSenha().length() > 45 || usuario.getSenha2().length() > 45)
           msg = msg + "A senha deve conter até 45 caracteres!<br>"; 
        
        return msg;
    }
    
    public boolean validarAlterarSenha(Usuario usuario){              // verifica se tem campos vazios
        if(
           usuario.getSenhaAtual().trim().equals("") || 
           usuario.getSenha().trim().equals("") ||
           usuario.getSenha2().trim().equals("")
           )
                return false;
        
        return true;
    }
    
    public boolean validarSenha(Usuario usuario){
        if(usuario.getSenha().equals(usuario.getSenha2()))          // se as senhas foram iguais return true
            return true;
        
        return false;
    }
    
    public String validarCadSugestao(Sugestao sugestao){
        String msg = "";
        if(sugestao.getNomePlanta().trim().equals(""))
            msg = "Favor inserir o nome da planta!<br>";
        if(sugestao.getNomePlanta().trim().equals(""))
            msg = "Favor inserir o nome da planta!<br>";
        if(sugestao.getNomePlanta().length() > 100)
            msg = "Favor inserir um nome com menos de 100 caracteres!<br>";
        if(sugestao.getUtilizacao().trim().equals(""))
            msg = msg + "Favor inserir a utilização da planta!<br>";
        if(sugestao.getUtilizacao().length() > 5000)
            msg = "Favor inserir a utilização com menos de 5000 caracteres!<br>";
        if(sugestao.getEmail().trim().equals(""))
            msg = msg + "Favor inserir seu email!";
        if(sugestao.getEmail().length() > 1000)
            msg = msg + "Favor inserir um email com menos de 1000 caracteres!<br>";
        
        return msg;
    }
    
    public boolean validarCadSintomaPlanta(String[] sintomas) {

        if (sintomas == null) { // se não clicou em nenhuma opção retorna falso
            return false;
        }
        
        return true;
    }

    public boolean validarCadRegiaoPlanta(String[] regioes) {

        if (regioes == null) { // se não clicou em nenhuma opção retorna falso
            return false;
        }
        
        return true;
    }
    
    public boolean validarCadPartesPlanta(String[] partes) {

        if (partes == null) { // se não clicou em nenhuma opção retorna falso
            return false;
        }
        
        return true;
    }
    
     public int validarCadSintoma(Sintoma sintoma){
        if(sintoma.getSintoma().trim().equals(""))          // se for vazio retorna falso
            return 1;

        if(sintoma.getSintoma().length() > 100)          // se for vazio retorna falso
            return 2;        
        
        return 3;
    }
    
    public String validarCadPlanta(Planta planta){
        String msgErro = "";
        
        if(planta.getNomePopular().trim().equals(""))
            msgErro = msgErro + "Nome popular <br>";
       if(planta.getNomePopular().length() > 100)
            msgErro = msgErro + "Nome popular com menos de 100 caracteres <br>";
                
        if(planta.getNomeCientifico().trim().equals(""))
            msgErro = msgErro + "Nome Científico <br>";
        if(planta.getNomeCientifico().length() > 100)
            msgErro = msgErro + "Nome científico com menos de 100 caracteres <br>";
               
        if(planta.getPrincipioAtivo().trim().equals(""))
            msgErro = msgErro + "Princípios Ativos <br>";
        if(planta.getPrincipioAtivo().length() > 300)
            msgErro = msgErro + "Princípios ativos com menos de 300 caracteres <br>";
        
        if(planta.getContraIndicacoes().trim().equals(""))
            msgErro = msgErro + "Contra indicações <br>";
        if(planta.getContraIndicacoes().length() > 5000)
            msgErro = msgErro + "Contra indicações com menos de 5000 caracteres <br>";
                
        if(planta.getEfeitosAdversos().trim().equals(""))
            msgErro = msgErro + "Efeitos Adversos <br>";
        if(planta.getEfeitosAdversos().length() > 5000)
            msgErro = msgErro + "Efeitos adversos com menos de 5000 caracteres <br>";
                
        if(planta.getModoDePreparo().trim().equals(""))
            msgErro = msgErro + "Modo de preparo <br>";
        if(planta.getModoDePreparo().length() > 5000)
            msgErro = msgErro + "Modo de preparo com menos de 5000 caracteres <br>";
                
        return msgErro;
    }
    
    public boolean validarTipoArquivo(String nomeArquivo){
        if(nomeArquivo.endsWith(".jpg") || nomeArquivo.endsWith(".jpeg") || nomeArquivo.endsWith(".png"))
            return true;
        else
            return false;
             
    }
}
    

