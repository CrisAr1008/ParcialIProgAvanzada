
package Control.Logic;

import Control.Conexion.Conexion;
import Modelo.QuesoVO;
import Vista.Ventana;

/**
 *
 * 
 */
public class Launcher {
    
    public static void main(String[] args){
        Ventana ventana = new Ventana();
        ventana.setVisible(true);
        QuesoVO queso = new QuesoVO();
        Conexion conector = new Conexion();
        Controlador controller = new Controlador(ventana, conector, queso);
    }
}
