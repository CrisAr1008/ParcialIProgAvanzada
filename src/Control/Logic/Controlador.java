package Control.Logic;

import Modelo.QuesoVO;
import Vista.Ventana;
import java.awt.event.ActionEvent;
import Control.Conexion.Conexion;

/**
 *
 *
 */
public class Controlador {

    private Ventana ventana;
    private Conexion conector;
    private QuesoVO queso;

    public Controlador(Ventana ventana, Conexion conector, QuesoVO queso) {

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.ventana.getBtnAgregar()) {

            this.queso.setGrasa(ventana.getjComboBox1().getSelectedItem().toString());
            //this.queso.setGusto(ventana.getComboBoxGusto..getSelectedItem().toString());
            //this.queso.setMaduracion(ventana.getComboBoxMaduracion..getSelectedItem().toString());
            //this.queso.setTextura(ventana.getComboBoxTextura..getSelectedItem().toString());
            //this.queso.setTipoLeche(ventana.getComboBoxTipoLeche..getSelectedItem().toString());
            //this.queso.setTratLeche(ventana.getComboBoxTratLeche..getSelectedItem().toString());
       //     this.conector.Insertar(this.queso);

        }

     /*   if (e.getSource() == this.ventana.getBtnConsultar()) {

            if (conector.Consultar(this.ventana.getjTextFieldName().getText()) != null) {//verificar q lo encuentre
                this.queso = conector.Consultar(this.ventana.getjTextFieldName().getText());
                this.ventana.getjComboBox1().setEnabled(false);//setEnabled o setEditable
                this.ventana.getjComboBox1().setSelectedItem(this.queso.getGrasa());
                //las 2 lineas anteriores para cada combo box y el text field
            } else {
                this.ventana.getjLabel1().setText("Queso no encontrado, prueba con otro nombre");
            }
        }

        if (e.getSource() == this.ventana.getBtnEliminar()) {
            //Como posibilidad estaria mostrar el queso seleccionado y verificar que es el queso correcto (consulta previa?)
            this.conector.Eliminar(this.queso.getID());
        }

        if (e.getSource() == this.ventana.getBtnEditar()) {
            /* Este if se puede agregar al boton de consulta con un if ya que solo se puede editar un queso ya consultado
         * (mientras no haya consulta el boton esta deshabilitado), con esto nos ahorramos tener que volver a poner la siquiente linea
         * this.queso = conector.Consultar(this.ventana.getjLabelName().getText());
             */

  //          this.conector.Modificar(queso);
        }

     /*   if (e.getSource() == this.ventana.getBtnFinalizar()) {
            //Override close window (window Adapter listener)

            for (int i = 0; i < this.conector.getDBSize(); i++) {
                
            }
        }*/

    }
