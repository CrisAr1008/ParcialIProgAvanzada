package Control.Logic;

import Control.Conexion.Conexion;
import Control.DAO.QuesoDAO;
import Modelo.QuesoVO;
import Vista.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 *
 */
public class Controlador implements ActionListener {

    private Ventana ventana;
    private Conexion conexion;
    private QuesoDAO conector = new QuesoDAO();
    private QuesoVO queso;
    private int fila;
    private DefaultTableModel model;
    private File fl;
    RandomAccessFile archivo;
    ArrayList<QuesoVO> Quesos;

    public Controlador(Ventana ventana, Conexion conexion, QuesoVO queso) {
        
        this.conexion = conexion;
        this.ventana = ventana;
        this.queso = queso;
        
        // Importar los queso del file al DB y finalmente a la tabla en la interfaz
        try {
            fl = new File("MisQuesos.dat");
            archivo = new RandomAccessFile(fl, "rw");
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        }

        cargarBD();
    }

    /*
     * Acciones de cada boton en la interfaz de Quesos y de Inicio de sesion
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.ventana.getBtnAgregar()) {

            /*
             * Para insertar un queso a la BD, primero verificamos que todos los campos 
             * en la interfaz tengan un valor y posteriormente lo insertamos con el DAO.
             */
            if (this.ventana.getTxtNombre().toString().equals("") && this.ventana.getComboBoxGrasa().getSelectedIndex() != 0 && this.ventana.getComboBoxGusto().getSelectedIndex() != 0
                    && this.ventana.getComboBoxMaduracion().getSelectedIndex() != 0 && this.ventana.getComboBoxTextura().getSelectedIndex() != 0
                    && this.ventana.getComboBoxTipoLeche().getSelectedIndex() != 0 && this.ventana.getComboBoxTratamiento().getSelectedIndex() != 0) {//Todos los campos con algun valor

                this.queso.setNombre(this.ventana.getTxtNombre().getText());
                this.queso.setGrasa(this.ventana.getComboBoxGrasa().getSelectedItem().toString());
                this.queso.setGusto(this.ventana.getComboBoxGusto().getSelectedItem().toString());
                this.queso.setMaduracion(this.ventana.getComboBoxMaduracion().getSelectedItem().toString());
                this.queso.setTextura(this.ventana.getComboBoxTextura().getSelectedItem().toString());
                this.queso.setTipoLeche(this.ventana.getComboBoxTipoLeche().getSelectedItem().toString());
                this.queso.setTratLeche(this.ventana.getComboBoxTratamiento().getSelectedItem().toString());
                this.conector.insertarDatos(this.queso);
                this.ventana.getLblWarnings().setText("");
            } else {
                this.ventana.getLblWarnings().setText("Ingresa un valor valido en todas las caracteristicas");
            }
        }

        if (e.getSource() == this.ventana.getBtnConsultar()) {

            /*
             * Para consultar un queso, tomamos el indice de los elementos en el comboBox de busqueda
             * y el texto de la busqueda y lo pasamos a traves del DAO. Esto nos retorna un queso el cual 
             * si tiene valores en sus atributos es mostrado a traves de la tabla.
             *
             * A su vez, activamos el boton de editar para modificar el queso seleccionado.
             */
            this.ventana.getLblWarnings().setText("");
            switch (this.ventana.getComboBoxBusqueda().getSelectedIndex()) {
                case 0:
                    this.ventana.getLblWarnings().setText("Selecciona el filtro de busqueda");
                    break;
                case 1:
                    this.queso = this.conector.consultarQuesoVO("NOMBRE", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 2:
                    this.queso = this.conector.consultarQuesoVO("TIPOLECHE", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 3:
                    this.queso = this.conector.consultarQuesoVO("GRASA", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 4:
                    this.queso = this.conector.consultarQuesoVO("MADURACION", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 5:
                    this.queso = this.conector.consultarQuesoVO("TEXTURA", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 6:
                    this.queso = this.conector.consultarQuesoVO("GUSTO", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 7:
                    this.queso = this.conector.consultarQuesoVO("TRATLECHE", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
            }

            if (this.queso != null) {
                model = (DefaultTableModel) this.ventana.getTablaQuesos().getModel();
                model.setRowCount(0);//                check 
                model.addRow(new Object[]{this.queso.getID(), this.queso.getNombre(), this.queso.getTipoLeche(), this.queso.getGrasa(),
                    this.queso.getMaduracion(), this.queso.getTextura(), this.queso.getGusto(), this.queso.getTratLeche()});
            } else {
                this.ventana.getLblWarnings().setText("Queso no encontrado, intenta una nueva busqueda.");
            }
        }

        /*if (e.getSource() == this.ventana.getBtnEliminar()) {
            //SQL 
            this.conector.Eliminar(this.queso.getID());
        }*/

        if (e.getSource() == this.ventana.getBtnEditar()) {

            /*
             * Para editar un queso, primero leemos los datos en la tabla y los guardamos dinamicamente en un 
             * objeto tipo QuesoVO, luego lo agregamos a la interfaz para que se puedan modificar sus datos con 
             * los comboBox de la misma y reinsertar el Queso.
             *
             *
             */
            fila = this.ventana.getTablaQuesos().getSelectedRow();

            this.queso.setID(Integer.parseInt(this.ventana.getTablaQuesos().getValueAt(fila, 0).toString()));
            this.queso.setNombre(this.ventana.getTablaQuesos().getValueAt(fila, 1).toString());
            this.queso.setTipoLeche(this.ventana.getTablaQuesos().getValueAt(fila, 2).toString());
            this.queso.setGrasa(this.ventana.getTablaQuesos().getValueAt(fila, 3).toString());
            this.queso.setMaduracion(this.ventana.getTablaQuesos().getValueAt(fila, 4).toString());
            this.queso.setTextura(this.ventana.getTablaQuesos().getValueAt(fila, 5).toString());
            this.queso.setGusto(this.ventana.getTablaQuesos().getValueAt(fila, 6).toString());
            this.queso.setTratLeche(this.ventana.getTablaQuesos().getValueAt(fila, 7).toString());

            this.ventana.getTxtNombre().setText(this.ventana.getTablaQuesos().getValueAt(fila, 1).toString());
            this.ventana.getComboBoxTipoLeche().setSelectedItem(this.ventana.getTablaQuesos().getValueAt(fila, 2).toString());
            this.ventana.getComboBoxGrasa().setSelectedItem(this.ventana.getTablaQuesos().getValueAt(fila, 3).toString());
            this.ventana.getComboBoxMaduracion().setSelectedItem(this.ventana.getTablaQuesos().getValueAt(fila, 4).toString());
            this.ventana.getComboBoxTextura().setSelectedItem(this.ventana.getTablaQuesos().getValueAt(fila, 5).toString());
            this.ventana.getComboBoxGusto().setSelectedItem(this.ventana.getTablaQuesos().getValueAt(fila, 6).toString());
            this.ventana.getComboBoxTratamiento().setSelectedItem(this.ventana.getTablaQuesos().getValueAt(fila, 7).toString());

         //   this.conector.modificarQueso(this.queso);
            this.ventana.getBtnEditar().setEnabled(false);
        }

        if (e.getSource() == this.ventana.getBtnFinalizar()) {

            guardarArchivo();
            try {
                archivo.close();
            } catch (IOException ex) {
            }
            //Override close window (window Adapter listener)
            this.ventana.dispose();
        }

        if (e.getSource() == this.ventana.getBtnLimpiar()) {
            this.ventana.getTxtBusqueda().setText("");
            this.ventana.getComboBoxBusqueda().setSelectedItem("---");
            this.ventana.getComboBoxGrasa().setSelectedItem("---");
            this.ventana.getComboBoxGusto().setSelectedItem("---");
            this.ventana.getComboBoxMaduracion().setSelectedItem("---");
            this.ventana.getComboBoxTextura().setSelectedItem("---");
            this.ventana.getComboBoxTipoLeche().setSelectedItem("---");
            this.ventana.getComboBoxTratamiento().setSelectedItem("---");
            this.ventana.getTxtNombre().setText("");
            this.ventana.getTablaQuesos().removeRowSelectionInterval(0, this.ventana.getTablaQuesos().getRowCount() - 1);
        }
    }

    private void guardarArchivo() {

        Quesos = this.conector.listaDeQuesos();
        for (int i = 0; i < Quesos.size(); i++) {
            try {
                this.queso = Quesos.get(i);
                archivo.writeInt(this.queso.getID());
                archivo.writeChars(this.queso.getNombre());
                archivo.writeChars(this.queso.getTipoLeche());
                archivo.writeChars(this.queso.getGrasa());
                archivo.writeChars(this.queso.getMaduracion());
                archivo.writeChars(this.queso.getTextura());
                archivo.writeChars(this.queso.getGusto());
                archivo.writeChars(this.queso.getTratLeche());
            } catch (FileNotFoundException fnfe) {
                System.out.println(fnfe);
            } catch (IOException exIO) {
                System.out.println(exIO);
            }
        }
    }

    private void cargarBD() {
        /*try {
            archivo.seek(0);
            for(int i = 0; i < archivo.length()/[tam registro]; i++)
            {
                this.queso.setID(archivo.readInt());
                
                
        
                this.conector.insertarDatos(this.queso);
            }
        } catch (FileNotFoundException fnfe) {/* Archivo no encontrado 
        } catch (IOException ioe) {
            /* Error al escribir 
        }
        */
    }
}
