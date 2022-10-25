package Modelo;

import Control.Conexion.Conexion;
import Control.DAO.QuesoDAO;
import com.sun.jdi.connect.spi.Connection;

/**
 *
 *
 */
public class QuesoVO {

    private int id;
    private String nombre;
    private String tipoLeche;
    private String grasa;
    private String maduracion;
    private String textura;
    private String gusto;
    private String tratLeche;

    public QuesoVO() {
    }

    public QuesoVO(int id, String nombre, String tipoLeche, String grasa, String maduracion, String textura, String gusto, String tratLeche) {
        this.id = id;
        this.nombre = nombre;
        this.tipoLeche = tipoLeche;
        this.grasa = grasa;
        this.maduracion = maduracion;
        this.textura = textura;
        this.gusto = gusto;
        this.tratLeche = tratLeche;
    }

    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String Nombre) {
        this.nombre = Nombre;
    }

    public String getTipoLeche() {
        return tipoLeche;
    }

    public void setTipoLeche(String TipoLeche) {
        this.tipoLeche = TipoLeche;
    }

    public String getGrasa() {
        return grasa;
    }

    public void setGrasa(String Grasa) {
        this.grasa = Grasa;
    }

    public String getMaduracion() {
        return maduracion;
    }

    public void setMaduracion(String Maduracion) {
        this.maduracion = Maduracion;
    }

    public String getTextura() {
        return textura;
    }

    public void setTextura(String Textura) {
        this.textura = Textura;
    }

    public String getGusto() {
        return gusto;
    }

    public void setGusto(String Gusto) {
        this.gusto = Gusto;
    }

    public String getTratLeche() {
        return tratLeche;
    }

    public void setTratLeche(String TratLeche) {
        this.tratLeche = TratLeche;
    }

}
