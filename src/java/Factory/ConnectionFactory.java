package Factory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Connection con = new ConnectionFactory().getConnection();

public class ConnectionFactory {
    
    public Connection getConnection() throws ClassNotFoundException {
        
        Class.forName("com.mysql.jdbc.Driver"); // "carregar" o driver, de tal maneira que o Java saiba que ele existe, obrigatório!!
        
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/plantas_medicinais", "root", "1234");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}

