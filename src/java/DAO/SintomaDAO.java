/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Factory.ConnectionFactory;
import Principal.Sintoma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeferson Pacheco
 */
public class SintomaDAO {
    
    private final Connection conn;
    
    public SintomaDAO() throws ClassNotFoundException {
        this.conn = new ConnectionFactory().getConnection();    // construtor chama a factory para abrir a conexão
    }
    
    public List<Sintoma> getLista() throws SQLException {
    try {
        List<Sintoma> sintomas = new ArrayList<>();
        PreparedStatement stmt = this.conn.prepareStatement("select * from plantas_medicinais.sintomas");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
         // criando o objeto Contato
        Sintoma sintoma = new Sintoma();
        sintoma.setSintoma(rs.getString("sintoma"));
        sintoma.setId(rs.getString("idSintoma"));
        // adicionando o objeto à lista
        sintomas.add(sintoma);
        }
        
        rs.close();
        stmt.close();
        return sintomas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }
    
    public List<Sintoma> getListaSintChecked(String idd) throws SQLException {
    try {
        String id = idd;
        List<Sintoma> sintomas = new ArrayList<>();
        List<String> sintCheck = new ArrayList<>();
        
        PreparedStatement stmt = this.conn.prepareStatement("select * from plantas_medicinais.sintomas");
        PreparedStatement stmt2 = this.conn.prepareStatement("select sintoma as Sintomas from plantas_medicinais.plantas_sintomas inner join plantas_medicinais.sintomas on plantas_sintomas.idSintoma = sintomas.idSintoma where (idPlanta = "+id+")");
        
        ResultSet rs = stmt.executeQuery();
        ResultSet rs2 = stmt2.executeQuery();
        
                
        while (rs2.next()) {
            sintCheck.add(rs2.getString("Sintomas"));
        }
        
        while (rs.next()) {
         // criando o objeto Contato
        Sintoma sintoma = new Sintoma();
        sintoma.setSintoma(rs.getString("sintoma"));
        sintoma.setId(rs.getString("idSintoma"));
        sintoma.setChecked("");

        for(String s : sintCheck)
        {
            if(sintoma.getSintoma().equals(s))
            {
                sintoma.setChecked("checked");
            }
        }
        // adicionando o objeto à lista
        sintomas.add(sintoma);
        }
        
        rs.close();
        stmt.close();
        return sintomas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }
    
    public boolean inserirSintoma(Sintoma sintoma) throws SQLException{

        String sql = "insert into plantas_medicinais.sintomas (sintoma) values (?)";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara objeto pra inserção
            stmt.setString(1, sintoma.getSintoma());
            stmt.execute();                                      // executa a query
            stmt.close();                                        // fecha a conexão com o banco
            return true;
        }catch(SQLException e){   
            return false;
        } finally {
            conn.close();
        }
        
    }
    
    public boolean editarSintoma(String id, String sintoma) throws SQLException{
        
        int idSintoma = Integer.parseInt(id);
        String sql = "update plantas_medicinais.sintomas SET sintoma = '"+sintoma+"' where (idSintoma = '"+idSintoma+"')";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara objeto pra inserção
            stmt.execute();                                      // executa a query
            stmt.close();                                        // fecha a conexão com o banco
            return true;
        }catch(SQLException e){   
            return false;
        } finally {
            conn.close();
        }
        
    }
     
    public boolean excluir(String id) throws SQLException{
         
        String sql = "delete from plantas_medicinais.sintomas where (idSintoma = "+id+")";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara objeto pra inserção
            stmt.execute();                                      // executa a query
            stmt.close();                                        // fecha a conexão com o banco
            return true;
        }catch(SQLException e){   
            return false;
            //throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }
    
}
