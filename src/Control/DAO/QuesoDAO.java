package Control.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Control.Conexion.Conexion;
import Modelo.QuesoVO;
import java.util.ArrayList;

/**
 *
 * 
 */
public class QuesoDAO {
    
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public QuesoDAO() {
        con = null;
        st = null;
        rs = null;
    }
    
    //Recibe como parametro un objeto de tipo QuesoVO con todos sus atributos
    public void insertarDatos(QuesoVO queso) {
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            String insercion = "INSERT INTO Estudiantes VALUES('" + queso.getNombre() 
                    + "','" + queso.getTipoLeche() + "', '"+queso.getGrasa()+"', '"+queso.getGrasa()+"',"
                    + "'"+ queso.getMaduracion() +"', '"+ queso.getTextura()+"','"+ queso.getTextura()+"'"
                    + ",'"+ queso.getGusto() +"', '"+queso.getTratLeche()+"');";
            st.executeUpdate(insercion);
            st.close();
            Conexion.desconectar();
        } catch (SQLException ex) {
            System.out.print("No se pudo realizar la insercion");
        }
    }

    //Recibe un parametro el cual es el campo por el cual quiere consultar, y el valor
    public QuesoVO consultarQuesoVO (String parametro, String valor) {
        QuesoVO queso = null;
        String consulta = "SELECT * FROM QUESO where "+ parametro + "='" + valor +"';";
        if(parametro.equalsIgnoreCase("Id"))consulta = "SELECT * FROM QUESO where "+ parametro + "= " + valor +";";
        try {
            con = (Connection) Conexion.getConexion();
            st = con.createStatement();
            rs = st.executeQuery(consulta);
            if (rs.next()) {
                queso = new QuesoVO();
                queso.setID(rs.getInt("ID"));
                queso.setNombre(rs.getString("NOMBRE"));
                queso.setTipoLeche(rs.getString("TIPOLECHE"));
                queso.setGrasa(rs.getString("GRASA"));
                queso.setMaduracion(rs.getString("MADURACION"));
                queso.setTextura(rs.getString("TEXTURA"));
                queso.setTratLeche(rs.getString("TRATLECHE"));
            }
            st.close();
            Conexion.desconectar();
        } catch (SQLException ex) {
            System.out.println("No se pudo realizar la consulta");
        }
        return queso;
    }
    
    //Consulta general de todos los quesos, retorna ArrayList con datos de tipo QuesoVO
    public ArrayList<QuesoVO> listaDeQuesos() {
        ArrayList<QuesoVO> misQuesos = new ArrayList<QuesoVO>();
        String consulta = "SELECT * FROM QUESO;";
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                QuesoVO queso = new QuesoVO();
                queso.setID(rs.getInt("ID"));
                queso.setNombre(rs.getString("NOMBRE"));
                queso.setTipoLeche(rs.getString("TIPOLECHE"));
                queso.setGrasa(rs.getString("GRASA"));
                queso.setMaduracion(rs.getString("MADURACION"));
                queso.setTextura(rs.getString("TEXTURA"));
                queso.setTratLeche(rs.getString("TRATLECHE"));
            }
            st.close();
            Conexion.desconectar();
        } catch (SQLException ex) {
            System.out.println("No se pudo realizar la consulta");
        }
        return misQuesos;
    }
    
    public boolean modificarQueso(String codigo, String parametro, String dato) {
        //Update estudiantes set nombre='Maria Perez' where codigo=202210200031
        String sentencia= "update QUESO set "+ parametro +"='" + dato + "' where ID=" + codigo + ";";
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            st.executeUpdate(sentencia);
            st.close();
            Conexion.desconectar();
            return true;
        } catch (SQLException ex) {
            System.out.println("No se pudo realizar la modifcacion");
        }
        return false;
    }
    
     public boolean eliminarQueso(String codigo) {
        String consulta = "DELETE FROM QUESO where ID=" + codigo + ";";
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            st.executeUpdate(consulta);
            st.close();
            Conexion.desconectar();
            return true;
        } catch (SQLException ex) {
            System.out.println("No se pudo realizar la eliminacion");
        }
        return false;
    }
     
    public int getDBSize(){
        String sentencia ="SELECT COUNT(*) FROM QUESO";
        try{
            con = Conexion.getConexion();
            st = con.createStatement();
            int rowCount = st.executeUpdate(sentencia);
            st.close();
            Conexion.desconectar();
            return rowCount;
        }catch(SQLException ex)
        {            
            System.out.println(ex);
            return 0;
        }
    }
    
    public boolean cleanDB() {
        String consulta = "DELETE FROM QUESO ";
        try {
            con = Conexion.getConexion();
            st = con.createStatement();
            st.executeUpdate(consulta);
            st.close();
            Conexion.desconectar();
            return true;
        } catch (SQLException ex) {
            System.out.println("No se pudo realizar la eliminacion");
        }
        return false;
    }
}
