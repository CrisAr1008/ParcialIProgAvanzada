package Control;

import Modelo.Queso;

/**
 *
 * 
 */
public class Conexion {

    public Conexion() {     
        //try {} catch(SQLException){}
    }
    
    public Queso Consultar(String consulta){ 
        Queso queso = new Queso();//modificar para q tome los valores de la consulta
        return queso;
    }
    
    public void Insertar(Queso queso){}
    
    public void Eliminar(int ID){}
    
    public void Modificar(Queso queso){}  
    
    public int getDBSize (){
        
        return 0;
    }
}
