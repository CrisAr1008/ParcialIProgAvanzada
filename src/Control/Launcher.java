
package Control;

import Modelo.Queso;
import Vista.Ventana;

/**
 *
 * 
 */
public class Launcher {
    
    public static void main(String[] args){
        Ventana ventana = new Ventana();
        Queso queso = new Queso();
        Conexion conector = new Conexion();
        Controlador controller = new Controlador(ventana, conector, queso);
    }
}
