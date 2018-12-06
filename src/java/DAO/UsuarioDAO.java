
/**
 *
 * @author tma00185
 */

package DAO;

import Factory.ConnectionFactory;
import Principal.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    private final Connection conn;

    public UsuarioDAO() throws ClassNotFoundException {
        this.conn = new ConnectionFactory().getConnection();    // construtor chama a factory para abrir a conexão
    }
    
    public boolean inserirUsuario(Usuario usuario) throws SQLException{

        String sql = "insert into plantas_medicinais.usuarios (nome, email, senha, perfil, status)  values (?, ?, ?, ?, ?)";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara objeto pra inserção
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());               // pega o email
            stmt.setString(3, usuario.getSenha());               // pega a Senha
            stmt.setString(4, usuario.getPerfil());              // pega a Senha
            stmt.setString(5, usuario.getStatus());              // pega o status
            stmt.execute();                                      // executa a query
            stmt.close();                                        // fecha a conexão com o banco
            return true;
        }catch(SQLException e){   
            return false;
        } finally {
            conn.close();
        }
    }
    
    public boolean alterarUsuario(Usuario usuario) throws SQLException{

        String sql = "update plantas_medicinais.usuarios set nome='"+usuario.getNome()+"',"
                + " email='"+usuario.getEmail()+"',"
                + " senha='"+usuario.getSenha()+"', "
                + "perfil='"+usuario.getPerfil()+"' where (idUsuario = "+usuario.getId()+")";
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
    
    public boolean alterarSenha(Usuario usuario) throws SQLException{
        
        String sql = "update plantas_medicinais.usuarios set senha='"+usuario.getSenha2()+"' "
                + "where (idUsuario = "+usuario.getId()+")";
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
    
    public int inserirToken(String token, String email) throws SQLException{

        String id = "";
        
        try{
        PreparedStatement stmt = conn.prepareStatement("Select * from plantas_medicinais.usuarios where (email = '"+email+"')");
        ResultSet rs = stmt.executeQuery();
        
            while(rs.next())
            {
                id = rs.getString("idUsuario");
            }
        
        }catch(SQLException e){   
            return 1;
        } 
        
        if(id.equals(""))
            return 2;
         
        String sql = "update plantas_medicinais.usuarios set token='"+token+"' "
                + "where (idUsuario = "+id+")";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara objeto pra inserção
            stmt.execute();                                      // executa a query
            stmt.close();                                        // fecha a conexão com o banco
            return 3;
        }catch(SQLException e){   
            return 4;
        } finally {
            conn.close();
        }
    }
    
    public int validarToken(String token) throws SQLException{
        String id = "";
        
        try{
        PreparedStatement stmt = conn.prepareStatement("Select * from plantas_medicinais.usuarios where (token = '"+token+"')");
        ResultSet rs = stmt.executeQuery();
        
            while(rs.next())
            {
                id = rs.getString("idUsuario");
            }
        
        }catch(SQLException e){   
            return -1;
        }
        
        if(id.equals(""))
            return -2;
         
        String sql = "update plantas_medicinais.usuarios set token="+null+" "
                + "where (idUsuario = "+id+")";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara objeto pra inserção
            stmt.execute();                                      // executa a query
            stmt.close();                                        // fecha a conexão com o banco
            return Integer.parseInt(id);
        }catch(SQLException e){   
            return -4;
        } finally {
            conn.close();
        }
        
    }
    
    public Usuario validarLogin(Usuario usuario) throws SQLException
    {
        String Email = "";
        String idUsuario = "";
        String Nome = "";
        String Senha = "";
        String Perfil = "";
        
        try{
        PreparedStatement stmt = conn.prepareStatement("Select * from plantas_medicinais.usuarios where (Senha = '"+usuario.getSenha()+"' and email = '"+usuario.getEmail()+"')");
        ResultSet rs = stmt.executeQuery();
        
            while(rs.next())
            {
                Email = rs.getString("email");
                Senha = rs.getString("Senha");
                Perfil = rs.getString("perfil");
                Nome = rs.getString("nome");
                idUsuario = rs.getString("idUsuario");
            }
        
        }catch(SQLException e){   
            throw new RuntimeException(e);
        }  finally {
            conn.close();
        }
        
        usuario.setPerfil(Perfil);
        usuario.setSenha(Senha);
        usuario.setEmail(Email);
        usuario.setNome(Nome);
        usuario.setId(idUsuario);
        
        return usuario;

    }
    
    public String retornarSenha(String id) throws SQLException{
        
        String Senha = null;
        try{
        PreparedStatement stmt = conn.prepareStatement("Select * from plantas_medicinais.usuarios where (idUsuario = '"+id+"')");
        ResultSet rs = stmt.executeQuery();
        
            while(rs.next())
            {
                Senha = rs.getString("senha");
            }
        
        }catch(SQLException e){   
            throw new RuntimeException(e);
        }  finally {
            conn.close();
        }
        
        return Senha;
    }
            
    public List<Usuario> getLista() throws SQLException {
    try {
        List<Usuario> usuarios = new ArrayList<>();
        PreparedStatement stmt = this.conn.prepareStatement("select * from plantas_medicinais.usuarios where usuarios.status='Ativo'");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
         // criando o objeto Contato
        Usuario usuario = new Usuario();
        usuario.setId(rs.getString("idUsuario"));
        usuario.setEmail(rs.getString("email"));
        usuario.setNome(rs.getString("nome"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setPerfil(rs.getString("perfil"));
        // adicionando o objeto à lista
        usuarios.add(usuario);
        }
        
        rs.close();
        stmt.close();
        return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }
    
    public boolean excluir(String id) throws SQLException{
         
        String sql = "update plantas_medicinais.usuarios set usuarios.email = '"+id+"', usuarios.status = 'Inativo' where idUsuario = "+id+"";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql); // prepara objeto pra inserção
            stmt.execute();                                      // executa a query
            stmt.close();                                        // fecha a conexão com o banco
            return true;
        }catch(SQLException e){   
            return false;
            //throw new RuntimeException(e);
        }finally {
            conn.close();
        }
    }
    
    public Usuario consultarUsuario(String id) throws SQLException
    {
        try{
        
        PreparedStatement stmt = conn.prepareStatement("Select * from plantas_medicinais.usuarios where (idUsuario = '"+id+"')");
        ResultSet rs = stmt.executeQuery();
        Usuario usuario = new Usuario();
        
        while(rs.next())
        {

            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setPerfil(rs.getString("perfil"));

        }
        
        rs.close();
        stmt.close();

        return usuario;
        
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }
}
