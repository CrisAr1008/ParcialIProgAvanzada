
package Control.Conexion;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * 
 */
public class Conexion {
    private static Connection cn = null;
    private static Driver driver = new org.apache.derby.jdbc.ClientDriver();
    private static String URLBD = "jdbc:derby://localhost:1527/ParcialProgAI ";
    private static String usuario = "root";
    private static String contrasena = "root";
    
    public static Connection getConexion() throws SQLException {
        DriverManager.registerDriver(driver);
        cn = DriverManager.getConnection(URLBD, usuario, contrasena);
        return cn;
    }
    public static void desconectar(){
      cn = null;
   }

    
}
