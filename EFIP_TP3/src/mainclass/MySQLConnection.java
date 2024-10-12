package mainclass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Clase para conectarse a MySQL con las credenciales correspondientes
public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/basta_lola";
    private static final String USER = "root";  
    private static final String PASSWORD = "500180";  

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
