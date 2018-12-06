/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Principal.Planta;
import Factory.ConnectionFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tma00185
 */
public class PlantaDAO {
    
    private final Connection conn;

    public PlantaDAO() throws ClassNotFoundException {
        this.conn = new ConnectionFactory().getConnection();    // construtor chama a factory para abrir a conexão
    }
    
    public boolean inserirPlanta(Planta planta, String[] sintomas, String[] regioes, String[] partesPlanta) throws SQLException{
        conn.setAutoCommit(false);                  // inicia uma transaction, alterações no banco só serão realizadas se todas ocorrerem com sucesso
        String sql = "insert into plantas_medicinais.plantas(nomeCientifico, nomePopular, contraIndicacoes, efeitosAdversos, principioAtivo, modoDePreparo, status, diretorioFoto, comentario, idUsuario) values (?,?,?,?,?,?,?,?,?,?)";
        String id = "";     
        String sql2 = "";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);     // prepara objeto pra inserção
            stmt.setString(1, planta.getNomeCientifico());           // pega o nome cientifico
            stmt.setString(2, planta.getNomePopular());              // pega o nome popular
            stmt.setString(3, planta.getContraIndicacoes());         // pega as contra indicações
            stmt.setString(4, planta.getEfeitosAdversos());          // pega os efeitos adversos
            stmt.setString(5, planta.getPrincipioAtivo());           // pega o principio ativo
            stmt.setString(6, planta.getModoDePreparo());            // pega o modo de preparo
            stmt.setString(7, planta.getStatus());                   // pega o status
            stmt.setString(8, planta.getLocal());
            stmt.setString(9, planta.getFeedback());
            stmt.setInt(10, (int)planta.getIdResponsável());
            stmt.execute();                                          // executa a query
            stmt.close();
            
            PreparedStatement stmt2 = conn.prepareStatement("SELECT LAST_INSERT_ID() as ID;");      // pega o ID do ultimo insert de planta
            ResultSet rs = stmt2.executeQuery();
        
            while(rs.next())
                id = (rs.getString("ID"));          // seta o ID retornado do banco na variavel id

            PreparedStatement stmt3 = null;         // inicializada a variável
            for (String sintoma : sintomas) {       // varrer o vetor de sintomas clicados pelo usuário
                sql2 = "insert into plantas_medicinais.plantas_sintomas (idPlanta, idSintoma) values ("+id+", (select idSintoma from plantas_medicinais.sintomas where (sintoma = '" + sintoma + "')));";
                stmt3 = conn.prepareStatement(sql2);   // prepara objeto pra inserção
                stmt3.execute();
                stmt3.close();
            }
            
            PreparedStatement stmt4 = null;         // inicializada a variável
            for (String regiao : regioes) {       // varrer o vetor de sintomas clicados pelo usuário
                sql2 = "insert into plantas_medicinais.plantas_regioes (idPlanta, idRegiao) values ("+id+", (select idRegiao from plantas_medicinais.regioes where (regiao = '" + regiao + "')));";
                stmt4 = conn.prepareStatement(sql2);   // prepara objeto pra inserção
                stmt4.execute();
                stmt4.close();
            }
            
            PreparedStatement stmt5 = null;         // inicializada a variável
            for (String partes : partesPlanta) {       // varrer o vetor de sintomas clicados pelo usuário
                sql2 = "insert into plantas_medicinais.plantas_partesplanta (idPlanta, idPartesPlanta) values ("+id+", (select idPartePlanta from plantas_medicinais.partesplanta where (partePlanta = '" + partes + "')));";
                stmt5 = conn.prepareStatement(sql2);   // prepara objeto pra inserção
                stmt5.execute();
                stmt5.close();
            }

            conn.commit();      // se tudo for sucesso, ele commita no banco
            return true;
        }catch(SQLException e){
            conn.rollback();
            return false;
            //throw new RuntimeException(e);
        } finally {
            conn.close();
        }
        
    }
    
    public boolean alterarPlanta(Planta planta, String[] sintomas, String[] regioes, String[] partesPlanta) throws SQLException{
        conn.setAutoCommit(false);                  // inicia uma transaction, alterações no banco só serão realizadas se todas ocorrerem com sucesso
        String id = planta.getId();
        String sql = "update plantas_medicinais.plantas set "
                + "nomeCientifico = '" +planta.getNomeCientifico() +"',"
                + "nomePopular = '"+planta.getNomePopular() +"',"
                + "contraIndicacoes = '"+planta.getContraIndicacoes() +"',"
                + "efeitosAdversos = '"+ planta.getEfeitosAdversos() +"',"
                + "status = '"+ planta.getStatus()+"',"
                + "comentario = '"+ planta.getFeedback()+"',"
                + "principioAtivo = '"+ planta.getPrincipioAtivo() +"',"
                + "diretorioFoto = '"+ planta.getLocal()+"',"
                + "modoDePreparo = '"+ planta.getModoDePreparo() +"'"
                //+ "idUsuario = "+ planta.getIdResponsável()+""
                + " where idPlanta = " + id +"";
        String sqlSemFoto = "update plantas_medicinais.plantas set "
                + "nomeCientifico = '" +planta.getNomeCientifico() +"',"
                + "nomePopular = '"+planta.getNomePopular() +"',"
                + "contraIndicacoes = '"+planta.getContraIndicacoes() +"',"
                + "efeitosAdversos = '"+ planta.getEfeitosAdversos() +"',"
                + "status = '"+ planta.getStatus()+"',"
                + "comentario = '"+ planta.getFeedback()+"',"
                + "principioAtivo = '"+ planta.getPrincipioAtivo() +"',"
                + "modoDePreparo = '"+ planta.getModoDePreparo() +"'"
                //+ "idUsuario = "+ planta.getIdResponsável()+""
                + " where idPlanta = "+ id +"";
        String sql2 = "";
        String sqlDelete = "delete from plantas_medicinais.plantas_sintomas where (idPlanta = "+id+")";
        String sqlDelete2 = "delete from plantas_medicinais.plantas_regioes where (idPlanta = "+id+")";        
        String sqlDelete3 = "delete from plantas_medicinais.plantas_partesplanta where (idPlanta = "+id+")";
    
        try{
            PreparedStatement stmt;
            if(planta.getLocal() != null)
                stmt = conn.prepareStatement(sql);            // Se não vier nulo do formulário, insere a nova imagem
            else
                stmt = conn.prepareStatement(sqlSemFoto);     // Se vier nulo do formulário, não altera a imagem existente

            stmt.execute();                                          // executa a query

            stmt = conn.prepareStatement(sqlDelete);      // prepara objeto pra inserção
            stmt.execute();                               // executa a query
            
            stmt = conn.prepareStatement(sqlDelete2);     // prepara objeto pra inserção
            stmt.execute();  
            
            stmt = conn.prepareStatement(sqlDelete3);     // prepara objeto pra inserção
            stmt.execute();  
            
            stmt.close();
            
            PreparedStatement stmt2 = null;         // inicializada a variável
            for (String sintoma : sintomas) {       // varrer o vetor de sintomas clicados pelo usuário
                sql2 = "insert into plantas_medicinais.plantas_sintomas (idPlanta, idSintoma) values ("+id+", (select idSintoma from plantas_medicinais.sintomas where (sintoma = '" + sintoma + "')));";
                stmt2 = conn.prepareStatement(sql2);   // prepara objeto pra inserção
                stmt2.execute();
                stmt2.close();
            }
            
            PreparedStatement stmt4 = null;         // inicializada a variável
            for (String regiao : regioes) {       // varrer o vetor de sintomas clicados pelo usuário
                sql2 = "insert into plantas_medicinais.plantas_regioes (idPlanta, idRegiao) values ("+id+", (select idRegiao from plantas_medicinais.regioes where (regiao = '" + regiao + "')));";
                stmt4 = conn.prepareStatement(sql2);   // prepara objeto pra inserção
                stmt4.execute();
                stmt4.close();
            }
            
            PreparedStatement stmt5 = null;         // inicializada a variável
            for (String partes : partesPlanta) {       // varrer o vetor de sintomas clicados pelo usuário
                sql2 = "insert into plantas_medicinais.plantas_partesplanta (idPlanta, idPartesPlanta) values ("+id+", (select idPartePlanta from plantas_medicinais.partesplanta where (partePlanta = '" + partes + "')));";
                stmt5 = conn.prepareStatement(sql2);   // prepara objeto pra inserção
                stmt5.execute();
                stmt5.close();
            }

            conn.commit();      // se tudo for sucesso, ele commita no banco
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            conn.rollback();
            return false;
            //throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }
    
    public List<Planta> consultarPlanta() throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("Select * from plantas_medicinais.Plantas where plantas.status = 'Aprovada';");

        try{
        List<Planta> plantas = new ArrayList<>();
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next())
        {
            Planta planta = new Planta();
            planta.setNomePopular(rs.getString("nomePopular"));
            planta.setNomeCientifico(rs.getString("nomeCientifico"));
            planta.setContraIndicacoes(rs.getString("contraIndicacoes"));
            planta.setEfeitosAdversos(rs.getString("efeitosAdversos"));
            planta.setPrincipioAtivo(rs.getString("principioAtivo"));
            planta.setModoDePreparo(rs.getString("modoDePreparo"));
            planta.setStatus(rs.getString("status"));
            planta.setId(rs.getString("idPlanta"));
            planta.setLocal(rs.getString("diretorioFoto"));
            // Agora o sistema irá buscar os sintomas associados ao ID desta planta
            stmt = conn.prepareStatement("select group_concat(sintoma) as Sintomas from plantas_medicinais.plantas_sintomas inner join plantas_medicinais.sintomas on plantas_sintomas.idSintoma = sintomas.idSintoma where (idPlanta = "+planta.getId()+")");
            ResultSet rs2 = stmt.executeQuery();
            while(rs2.next())
                planta.setSintomas(rs2.getString("Sintomas").replace(",", ", "));
            
            stmt = conn.prepareStatement("select group_concat(partePlanta) as partesPlanta from plantas_medicinais.plantas_partesplanta inner join plantas_medicinais.partesplanta on plantas_partesplanta.idPartesPlanta = partesplanta.idPartePlanta where (idPlanta = "+planta.getId()+");");
            rs2 = stmt.executeQuery();
            while(rs2.next())
                planta.setPartesPlanta(rs2.getString("partesPlanta").replace(",", ", "));
            
            stmt = conn.prepareStatement("select group_concat(regiao) as regioes from plantas_medicinais.plantas_regioes inner join plantas_medicinais.regioes on plantas_regioes.idRegiao = regioes.idRegiao where (idPlanta = "+planta.getId()+");");
            rs2 = stmt.executeQuery();
            while(rs2.next())
                planta.setRegioes(rs2.getString("regioes").replace(",", ", "));

            plantas.add(planta);
            rs2.close();
        }
        
        rs.close();
        stmt.close();
        return plantas;
        
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }
        
    }
    
    public List<Planta> consultarPlantaPendente() throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("Select * from plantas_medicinais.Plantas where plantas.status = ('Pendente')");

        try{
        List<Planta> plantas = new ArrayList<>();
        
        ResultSet rs = stmt.executeQuery();

        while(rs.next())
        {
            Planta planta = new Planta();
            planta.setNomePopular(rs.getString("nomePopular"));
            planta.setNomeCientifico(rs.getString("nomeCientifico"));
            planta.setContraIndicacoes(rs.getString("contraIndicacoes"));
            planta.setEfeitosAdversos(rs.getString("efeitosAdversos"));
            planta.setPrincipioAtivo(rs.getString("principioAtivo"));
            planta.setModoDePreparo(rs.getString("modoDePreparo"));
            planta.setStatus(rs.getString("status"));
            planta.setId(rs.getString("idPlanta"));
            // Agora o sistema irá buscar os sintomas associados ao ID desta planta
            stmt = conn.prepareStatement("select group_concat(sintoma) as Sintomas from plantas_medicinais.plantas_sintomas inner join plantas_medicinais.sintomas on plantas_sintomas.idSintoma = sintomas.idSintoma where (idPlanta = "+planta.getId()+")");
            ResultSet rs2 = stmt.executeQuery();
            while(rs2.next())
                planta.setSintomas(rs2.getString("Sintomas").replace(",", ", "));
            
            stmt = conn.prepareStatement("select group_concat(partePlanta) as partesPlanta from plantas_medicinais.plantas_partesplanta inner join plantas_medicinais.partesplanta on plantas_partesplanta.idPartesPlanta = partesplanta.idPartePlanta where (idPlanta = "+planta.getId()+");");
            rs2 = stmt.executeQuery();
            while(rs2.next())
                planta.setPartesPlanta(rs2.getString("partesPlanta").replace(",", ", "));
            
            stmt = conn.prepareStatement("select group_concat(regiao) as regioes from plantas_medicinais.plantas_regioes inner join plantas_medicinais.regioes on plantas_regioes.idRegiao = regioes.idRegiao where (idPlanta = "+planta.getId()+");");
            rs2 = stmt.executeQuery();
            while(rs2.next())
                planta.setRegioes(rs2.getString("regioes").replace(",", ", "));

            plantas.add(planta);
            rs2.close();
        }
        
        rs.close();
        stmt.close();
        return plantas;
        
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }
    
    public List<Planta> consultarPlantaReprovada() throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("Select * from plantas_medicinais.Plantas where plantas.status != ('Aprovada')");

        try{
        List<Planta> plantas = new ArrayList<>();
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next())
        {
            Planta planta = new Planta();
            planta.setNomePopular(rs.getString("nomePopular"));
            planta.setNomeCientifico(rs.getString("nomeCientifico"));
            planta.setContraIndicacoes(rs.getString("contraIndicacoes"));
            planta.setEfeitosAdversos(rs.getString("efeitosAdversos"));
            planta.setPrincipioAtivo(rs.getString("principioAtivo"));
            planta.setModoDePreparo(rs.getString("modoDePreparo"));
            planta.setStatus(rs.getString("status"));
            planta.setId(rs.getString("idPlanta"));
            // Agora o sistema irá buscar os sintomas associados ao ID desta planta
            stmt = conn.prepareStatement("select group_concat(sintoma) as Sintomas from plantas_medicinais.plantas_sintomas inner join plantas_medicinais.sintomas on plantas_sintomas.idSintoma = sintomas.idSintoma where (idPlanta = "+planta.getId()+")");
            ResultSet rs2 = stmt.executeQuery();
            while(rs2.next())
                planta.setSintomas(rs2.getString("Sintomas").replace(",", ", "));
            
            stmt = conn.prepareStatement("select group_concat(partePlanta) as partesPlanta from plantas_medicinais.plantas_partesplanta inner join plantas_medicinais.partesplanta on plantas_partesplanta.idPartesPlanta = partesplanta.idPartePlanta where (idPlanta = "+planta.getId()+");");
            rs2 = stmt.executeQuery();
            while(rs2.next())
                planta.setPartesPlanta(rs2.getString("partesPlanta").replace(",", ", "));
            
            stmt = conn.prepareStatement("select group_concat(regiao) as regioes from plantas_medicinais.plantas_regioes inner join plantas_medicinais.regioes on plantas_regioes.idRegiao = regioes.idRegiao where (idPlanta = "+planta.getId()+");");
            rs2 = stmt.executeQuery();
            while(rs2.next())
                planta.setRegioes(rs2.getString("regioes").replace(",", ", "));

            plantas.add(planta);
            rs2.close();
        }
        
        rs.close();
        stmt.close();
        return plantas;
        
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }         
    }
    
    public Planta consultarPlantaById(String id) throws SQLException
    {
        try{
        
        PreparedStatement stmt = conn.prepareStatement("Select * from plantas_medicinais.Plantas join plantas_medicinais.usuarios on plantas.idUsuario = usuarios.idUsuario where (idPlanta = '"+id+"')");
        ResultSet rs = stmt.executeQuery();
        Planta planta = new Planta();
        
        while(rs.next())
        {

            planta.setNomePopular(rs.getString("nomePopular"));
            planta.setNomeCientifico(rs.getString("nomeCientifico"));
            planta.setContraIndicacoes(rs.getString("contraIndicacoes"));
            planta.setEfeitosAdversos(rs.getString("efeitosAdversos"));
            planta.setPrincipioAtivo(rs.getString("principioAtivo"));
            planta.setModoDePreparo(rs.getString("modoDePreparo"));
            planta.setStatus(rs.getString("status"));
            planta.setId(rs.getString("idPlanta"));
            planta.setLocal(rs.getString("diretorioFoto"));
            planta.setFeedback(rs.getString("comentario"));
            planta.setProfessor(rs.getString("nome"));
            // Agora o sistema irá buscar os sintomas associados ao ID desta planta
            stmt = conn.prepareStatement("select group_concat(sintoma) as Sintomas from plantas_medicinais.plantas_sintomas inner join plantas_medicinais.sintomas on plantas_sintomas.idSintoma = sintomas.idSintoma where (idPlanta = "+planta.getId()+")");
            ResultSet rs2 = stmt.executeQuery();
            while(rs2.next())
                planta.setSintomas(rs2.getString("Sintomas").replace(",", ", "));
            
            stmt = conn.prepareStatement("select group_concat(partePlanta) as partesPlanta from plantas_medicinais.plantas_partesplanta inner join plantas_medicinais.partesplanta on plantas_partesplanta.idPartesPlanta = partesplanta.idPartePlanta where (idPlanta = "+planta.getId()+");");
            rs2 = stmt.executeQuery();
            while(rs2.next())
                planta.setPartesPlanta(rs2.getString("partesPlanta").replace(",", ", "));
            
            stmt = conn.prepareStatement("select group_concat(regiao) as regioes from plantas_medicinais.plantas_regioes inner join plantas_medicinais.regioes on plantas_regioes.idRegiao = regioes.idRegiao where (idPlanta = "+planta.getId()+");");
            rs2 = stmt.executeQuery();
            while(rs2.next())
                planta.setRegioes(rs2.getString("regioes").replace(",", ", "));

            //plantas.add(planta);
            rs2.close();
        }
        
        rs.close();
        stmt.close();
        return planta;
        
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } 
        
    }
        
    public List<Planta> getListaPartesPlanta() throws SQLException {
    try {
        List<Planta> partes = new ArrayList<>();
        PreparedStatement stmt = this.conn.prepareStatement("select * from plantas_medicinais.partesplanta");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
         // criando o objeto Contato
        Planta parte = new Planta();
        parte.setPartesPlanta(rs.getString("partePlanta"));
        parte.setId(rs.getString("idPartePlanta"));
        // adicionando o objeto à lista
        partes.add(parte);
        }
        
        rs.close();
        stmt.close();
        return partes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Planta> getListaRegioesPlanta() throws SQLException {
    try {
        List<Planta> partes = new ArrayList<>();
        PreparedStatement stmt = this.conn.prepareStatement("select * from plantas_medicinais.regioes");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
         // criando o objeto Contato
        Planta parte = new Planta();
        parte.setRegioes(rs.getString("regiao"));
        parte.setId(rs.getString("idRegiao"));
        // adicionando o objeto à lista
        partes.add(parte);
        }
        
        rs.close();
        stmt.close();
        return partes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean aprovarPlanta(String id, int idUsuario) throws SQLException{

        String sql = "UPDATE plantas_medicinais.plantas SET status = 'Aprovada', idUsuario = "+idUsuario+" WHERE (idPlanta = '"+id+"')";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara objeto pra inserção
            stmt.execute();                                      // executa a query
            stmt.close();                                        // fecha a conexão com o banco
            return true;
        }catch(SQLException e){   
            return false;
        } 
        
    }
    
    public boolean excluirPlanta(String id) throws SQLException{

        final String local = "imgCadastros/";
        final String dirExcluir = "C:\\Users\\User\\Documents\\NetBeansProjects\\Teste\\web\\";  
        String sql = "delete from plantas_medicinais.plantas WHERE (idPlanta = '"+id+"')";
        
        try{    
            Planta planta;
            planta = consultarPlantaById(id);
            
            File f = new File(dirExcluir+planta.getLocal());
                if(!planta.getLocal().equals("imagens/Vazio.jpeg"))
                    f.delete();
            
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara objeto pra inserção
            stmt.execute();                                      // executa a query
            stmt.close();                                        // fecha a conexão com o banco

            return true;
        }catch(SQLException e){   
            return false;
        }
        
    }
    
    public boolean reprovarPlanta(Planta planta) throws SQLException{
            
        String sql = "update plantas_medicinais.plantas set plantas.comentario='"+planta.getFeedback()+"', plantas.status='"+planta.getStatus()+"' where (idPlanta = "+planta.getId()+")";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara objeto pra inserção
            stmt.execute();                                      // executa a query
            stmt.close();                                        // fecha a conexão com o banco
            return true;
        }catch(SQLException e){   
            return false;
        } 
    }
    
    public List<Planta> getListaPartesChecked(String idPlanta) throws SQLException {
    try {
        String id = idPlanta;                               // recebe o ID da planta a ser trabalhada
        List<Planta> partes = new ArrayList<>();            // abre uma lista de partes de planta
        List<String> partesCheck = new ArrayList<>();       // abre uma lista de partes de planta flegadas
        
        // seleciona todas as partes de planta cadastradas no banco de dados
        PreparedStatement stmt = this.conn.prepareStatement("select * from plantas_medicinais.partesplanta");
        
        // seleciona todas as partes de planta flegadas para este id de planta recebido
        PreparedStatement stmt2 = this.conn.prepareStatement("select partePlanta as Partes from plantas_medicinais.plantas_partesplanta inner join plantas_medicinais.partesplanta on plantas_partesplanta.idPartesPlanta = partesplanta.idPartePlanta where (idPlanta = "+id+")");
        
        ResultSet rs = stmt.executeQuery();
        ResultSet rs2 = stmt2.executeQuery();
        
                
        while (rs2.next()) {
            partesCheck.add(rs2.getString("Partes"));           // preenche a lista com as partes de planta flegadas 
        }
        
        while (rs.next()) {

        Planta parte = new Planta();
        parte.setPartesPlanta(rs.getString("partePlanta"));    // insere o nome da parte no objeto
        parte.setId(rs.getString("idPartePlanta"));
        parte.setParteChecked("");
        
        for(String s : partesCheck)  // verifica se essa parte consta na lista de partes flegadas
        {
            if(parte.getPartesPlanta().equals(s))    
            {
                parte.setParteChecked("checked");     
            }
        }
        // adicionando o objeto à lista
        partes.add(parte);
        }
        
        rs.close();
        stmt.close();
        return partes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } 
    }
    
    public List<Planta> getListaRegioesChecked(String idPlanta) throws SQLException {
    try {
        String id = idPlanta;                               // recebe o ID da planta a ser trabalhada
        List<Planta> regioes = new ArrayList<>();            // abre uma lista de partes de planta
        List<String> regioesCheck = new ArrayList<>();       // abre uma lista de partes de planta flegadas
        
        // seleciona todas as partes de planta cadastradas no banco de dados
        PreparedStatement stmt = this.conn.prepareStatement("select * from plantas_medicinais.regioes");
        
        // seleciona todas as partes de planta flegadas para este id de planta recebido
        PreparedStatement stmt2 = this.conn.prepareStatement("select regiao as Regioes from plantas_medicinais.plantas_regioes inner join plantas_medicinais.regioes on plantas_regioes.idRegiao = regioes.idRegiao where (idPlanta = "+id+")");
        
        ResultSet rs = stmt.executeQuery();
        ResultSet rs2 = stmt2.executeQuery();
                
        while (rs2.next()) {
            regioesCheck.add(rs2.getString("Regioes"));           // preenche a lista com as partes de planta flegadas 
        }
        
        while (rs.next()) {
            Planta regiao = new Planta();
            regiao.setRegioes(rs.getString("regiao"));    // insere o nome da parte no objeto, do primeiro select
            //regiao.setId(rs.getString("idRegiao"));
            regiao.setRegiaoChecked("");

            for(String s : regioesCheck)  // verifica se essa parte consta na lista de partes flegadas 
            {
                if(regiao.getRegioes().equals(s))    
                {
                    regiao.setRegiaoChecked("checked");     
                }
            }
            // adicionando o objeto à lista
            regioes.add(regiao);
        }
        
        rs.close();
        stmt.close();
        return regioes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
