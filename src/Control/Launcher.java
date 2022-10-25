
package Control;

import Modelo.Queso;
import Vista.Login;
import Vista.Ventana;

/**
 *
 * 
 */
public class Launcher {
    
    public static void main(String[] args){
        Ventana ventana = new Ventana();
        ventana.setVisible(true);
        Login login = new Login();
        login.setVisible(true);
        Queso queso = new Queso();
        Conexion conector = new Conexion();
        
        Controlador controller = new Controlador(ventana, conector, queso);
    }
}
