package Control.Logic;

import Control.Conexion.Conexion;
import Control.DAO.QuesoDAO;
import Modelo.QuesoVO;
import Vista.Ventana;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
public class Controlador implements ActionListener, WindowListener {

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

        this.ventana.addWindowListener(this);

        this.ventana.getBtnActualizar().addActionListener(this);
        this.ventana.getBtnAgregar().addActionListener(this);
        this.ventana.getBtnConsultar().addActionListener(this);
        this.ventana.getBtnEditar().addActionListener(this);
        this.ventana.getBtnFinalizar().addActionListener(this);
        this.ventana.getBtnLimpiar().addActionListener(this);

        //Creacion del RandomAccessFile
        try {
            fl = new File("MisQuesos.dat");
            archivo = new RandomAccessFile(fl, "rw");
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        }

        //Se importan los datos del RandomAccessFile a la base de datos        
        cargarBD();

        //Se agrega la BD a la interfaz    
        QuesosATabla();

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
            Quesos = null;
            this.ventana.getLblWarnings().setText("");
            /*
            switch (this.ventana.getComboBoxBusqueda().getSelectedIndex()) {
                case 0:
                    this.ventana.getLblWarnings().setText("Selecciona el filtro de busqueda");
                    break;
                case 1:
                    Quesos = this.conector.consultarQuesoVO("NOMBRE", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 2:
                    Quesos = this.conector.consultarQuesoVO("TIPOLECHE", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 3:
                    Quesos = this.conector.consultarQuesoVO("GRASA", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 4:
                    Quesos = this.conector.consultarQuesoVO("MADURACION", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 5:
                    Quesos = this.conector.consultarQuesoVO("TEXTURA", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 6:
                    Quesos = this.conector.consultarQuesoVO("GUSTO", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 7:
                    Quesos = this.conector.consultarQuesoVO("TRATLECHE", this.ventana.getTxtBusqueda().getText());
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
            }

            if (Quesos != null) {
                model = (DefaultTableModel) this.ventana.getTablaQuesos().getModel();
                model.setRowCount(0);
                
                for(int i=0;i<[SizeQuery]; i++)
                {
                    this.queso=Quesos.get(i);
                    model.addRow(new Object[]{this.queso.getID(), this.queso.getNombre(), this.queso.getTipoLeche(), this.queso.getGrasa(),
                    this.queso.getMaduracion(), this.queso.getTextura(), this.queso.getGusto(), this.queso.getTratLeche()});
                }                
            } else {
                this.ventana.getLblWarnings().setText("Queso no encontrado, intenta una nueva busqueda.");
            }*/
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
            this.ventana.getBtnActualizar().setEnabled(true);
            this.ventana.getBtnEditar().setEnabled(false);
        }

        if (e.getSource() == this.ventana.getBtnActualizar()) {
            /*
             * Tras editar el queso permitimos que lo pueda volver a insertar con su ID
             * correspondiente y los datos actualizados.
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
                this.ventana.getBtnActualizar().setEnabled(false);
            } else {
                this.ventana.getLblWarnings().setText("Ingresa un valor valido en todas las caracteristicas");
            }
        }

        if (e.getSource() == this.ventana.getBtnFinalizar()) {

            guardarArchivo();
            try {
                archivo.close();
            } catch (IOException ex) {
            }
            
            this.ventana.dispose();
            this.conector.cleanDB();
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
            this.ventana.getLblWarnings().setText("");
            this.ventana.getTablaQuesos().removeRowSelectionInterval(0, this.ventana.getTablaQuesos().getRowCount() - 1);
        }
    }

    /*
     * Metodos para el manejo del archivo
     */
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
        try {
            archivo.seek(0);
            for (int i = 0; i < archivo.length() / 354; i++) { //354 es el tamaño en bytes por registro
                this.queso.setID(archivo.readInt());
                this.queso.setNombre(leerFileString());
                this.queso.setTipoLeche(leerFileString());
                this.queso.setGrasa(leerFileString());
                this.queso.setMaduracion(leerFileString());
                this.queso.setTextura(leerFileString());
                this.queso.setGusto(leerFileString());
                this.queso.setTratLeche(leerFileString());
                this.conector.insertarDatos(this.queso);
            }
        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioEx) {
        }
    }

    private String leerFileString() {
        String valorPropQueso = "";
        try {
            for (int j = 0; j < 25; j++) {
                valorPropQueso += archivo.readChar();
            }
        } catch (FileNotFoundException fnfe) {
        } catch (IOException ioEx) {
        }
        return valorPropQueso;
    }

    /* Metodo para importar los datos a la interfaz */
    private void QuesosATabla() {
        Quesos = this.conector.listaDeQuesos();
        for (int i = 0; i < Quesos.size(); i++) {

            this.queso = Quesos.get(i);
            model = (DefaultTableModel) this.ventana.getTablaQuesos().getModel();
            model.addRow(new Object[]{this.queso.getID(), this.queso.getNombre(), this.queso.getTipoLeche(), this.queso.getGrasa(),
                this.queso.getMaduracion(), this.queso.getTextura(), this.queso.getGusto(), this.queso.getTratLeche()});
        }
    }

    /*
     * Metodos para el manejo de la ventana
     */
    @Override
    public void windowClosing(WindowEvent arg0) {

        guardarArchivo();
        try {
            archivo.close();
        } catch (IOException ex) {
        }

        this.ventana.dispose();
        this.conector.cleanDB();
    }

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }
}
