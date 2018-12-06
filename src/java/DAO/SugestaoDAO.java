/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Factory.ConnectionFactory;
import Principal.Sugestao;
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
public class SugestaoDAO {
    
    private final Connection conn;
    
    public SugestaoDAO() throws ClassNotFoundException {
        this.conn = new ConnectionFactory().getConnection();    // construtor chama a factory para abrir a conexão
    }
    
     public boolean inserirSugestao(Sugestao sugestao) throws SQLException{

        String sql = "insert into plantas_medicinais.sugestoesplanta (nomePlanta, emailContato, utilizacao, data, local) values (?,?,?,CURDATE(),?)";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara objeto pra inserção
            stmt.setString(1, sugestao.getNomePlanta());
            stmt.setString(2, sugestao.getEmail());
            stmt.setString(3, sugestao.getUtilizacao());
            stmt.setString(4, sugestao.getLocal());
            stmt.execute();                                      // executa a query
            stmt.close();                                        // fecha a conexão com o banco
            return true;
        }catch(SQLException e){   
            return false;
        } finally {
            conn.close();
        }
        
    }
     
    public List<Sugestao> getLista() throws SQLException {
    try {
        List<Sugestao> sugestoes = new ArrayList<>();
        PreparedStatement stmt = this.conn.prepareStatement("select * from plantas_medicinais.sugestoesplanta");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
         // criando o objeto Contato
        Sugestao sugestao = new Sugestao();
        sugestao.setId(rs.getString("idSugestoesPlanta"));
        sugestao.setData(rs.getDate("data"));
        sugestao.setEmail(rs.getString("emailContato"));
        sugestao.setNome(rs.getString("nomePlanta"));
        sugestao.setUtilizacao(rs.getString("utilizacao"));
        sugestao.setLocal(rs.getString("local"));
        // adicionando o objeto à lista
        sugestoes.add(sugestao);
        }
        
        rs.close();
        stmt.close();
        return sugestoes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }
    
    public boolean excluirSugestao(String id) throws SQLException{
         
        String sql = "delete from plantas_medicinais.sugestoesplanta where (idSugestoesPlanta = "+id+")";
        
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
