package Control;

import Modelo.Queso;
import Vista.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 *
 */
public class Controlador implements ActionListener {

    private Ventana ventana;
    private Conexion conector;
    private Queso queso;
    private int Columna = 0;
    private int fila;

    public Controlador(Ventana ventana, Conexion conector, Queso queso) {
        this.ventana = ventana;
        this.conector = conector;
        this.queso = queso;

        this.ventana.getBtnAgregar().addActionListener(this);
        this.ventana.getBtnConsultar().addActionListener(this);
        this.ventana.getBtnEditar().addActionListener(this);
        this.ventana.getBtnFinalizar().addActionListener(this);
        this.ventana.getBtnLimpiar().addActionListener(this);

    }

    /*
     * Acciones de cada boton en la interfaz de Quesos y de Inicio de sesion
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.ventana.getBtnAgregar()) {
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
                this.conector.Insertar(this.queso);
                this.ventana.getLblWarnings().setText("");
            } else {
                this.ventana.getLblWarnings().setText("Ingresa un valor valido en todas las caracteristicas");
            }
        }

        if (e.getSource() == this.ventana.getBtnConsultar()) {
            this.ventana.getLblWarnings().setText("");
            switch (this.ventana.getComboBoxBusqueda().getSelectedIndex()) {
                case 0:
                    this.ventana.getLblWarnings().setText("Selecciona el filtro de busqueda");
                    break;
                case 1:
                    this.ventana.getTxtBusqueda().getText();
                    //Usar el filtro y el texto de busqueda para realizar una consulta SQL 
                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 2:

                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 3:

                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 4:

                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 5:

                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 6:

                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                case 7:

                    this.ventana.getBtnEditar().setEnabled(true);
                    break;
                default:
                    break;
            }
        }

        /*if (e.getSource() == this.ventana.getBtnEliminar()) {
            //SQL 
            this.conector.Eliminar(this.queso.getID());
        }*/
        if (e.getSource() == this.ventana.getBtnEditar()) {

            fila = this.ventana.getTablaQuesos().getSelectedRow();

            queso.setID(Integer.parseInt(this.ventana.getTablaQuesos().getValueAt(fila, 0).toString()));
            Integer.parseInt(this.ventana.getTablaQuesos().getValueAt(fila, 1).toString()));
            Integer.parseInt(this.ventana.getTablaQuesos().getValueAt(fila, 2).toString()));
            Integer.parseInt(this.ventana.getTablaQuesos().getValueAt(fila, 3).toString()));
            Integer.parseInt(this.ventana.getTablaQuesos().getValueAt(fila, 4).toString()));
            Integer.parseInt(this.ventana.getTablaQuesos().getValueAt(fila, 5).toString()));
            Integer.parseInt(this.ventana.getTablaQuesos().getValueAt(fila, 6).toString()));
            Integer.parseInt(this.ventana.getTablaQuesos().getValueAt(fila, 7).toString()));
            
            //resto de campos de acuerdo al orden en la tabla

            fila = this.ventana.getTablaQuesos().getSelectedRow();
            this.ventana.getTxtNombre().setText(this.ventana.getTablaQuesos().getValueAt(fila, 1).toString());
            this.ventana.getComboBoxGrasa().setSelectedItem(this.ventana.getTablaQuesos().getValueAt(fila, x).toString());         
            this.ventana.getComboBoxGusto().setSelectedItem(this.ventana.getTablaQuesos().getValueAt(fila, x).toString());
            this.ventana.getComboBoxMaduracion().setSelectedItem(this.ventana.getTablaQuesos().getValueAt(fila, x).toString());
            this.ventana.getComboBoxTextura().setSelectedItem(this.ventana.getTablaQuesos().getValueAt(fila, x).toString());
            this.ventana.getComboBoxTipoLeche().setSelectedItem(this.ventana.getTablaQuesos().getValueAt(fila, x).toString());
            this.ventana.getComboBoxTratamiento().setSelectedItem(this.ventana.getTablaQuesos().getValueAt(fila, x).toString());
            //...

            //this.queso.setID(); SQL where 
            this.conector.Modificar(this.queso);
        }

        if (e.getSource() == this.ventana.getBtnFinalizar()) {

            for (int i = 0; i < this.conector.getDBSize(); i++) {
                //write in file
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
}
