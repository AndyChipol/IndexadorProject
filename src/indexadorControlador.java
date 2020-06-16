//Proyecto Final
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class indexadorControlador implements ActionListener {
    private indexadorVista vista;
    private indexadorModelo modelo;
    
    public indexadorControlador(indexadorModelo modelo, indexadorVista vista){
        this.modelo = modelo;
        this.vista = vista;
        vista.setLista(modelo.getLista());
        vista.cargarLista.addActionListener(this);
        vista.eliminarCargaLista.addActionListener(this);
        vista.btnHacerConsulta.addActionListener(this);
        vista.btnEliminarConsulta.addActionListener(this);
        vista.jRadioButton3.setSelected(true); 
    }
    
    public void run(){
        vista.setLocationRelativeTo(null);
    }
    
    
    public void actionPerformed(ActionEvent e){
        try{
            if(e.getSource() == vista.cargarLista){
                modelo.cargarArchivos(vista.model,vista.model3,vista.model5);
            }
        }catch(ArchivoNoInsertadoExcepcion | ConsultaNoEncontraExcepcion ex){
            vista.model.removeAllElements();
        }
        
        try{
            if(e.getSource() == vista.eliminarCargaLista){
                modelo.limpiarListaDeCarga(vista.model);
        }
        }catch(ListaVaciaExcepcion ex){
            
        }
        
        try{
            if(e.getSource() == vista.btnHacerConsulta){
                if (vista.jRadioButton3.isSelected()){
                    modelo.consulta(vista.palabraBuscar,vista.model2,vista.model4);
                }
                if (vista.jRadioButton1.isSelected()){
                    modelo.consultaOR(vista.palabraBuscar,vista.model2,vista.model4);
                }
                if(vista.jRadioButton2.isSelected()){
                        modelo.consultaNot(vista.palabraBuscar,vista.model2,vista.model4);
                }
                if(vista.jRadioButton4.isSelected()){
                        modelo.consultaAnd(vista.palabraBuscar,vista.model2,vista.model4);
                }
        }
        }
        catch(ConsultaNoEncontraExcepcion | UnaPalabraExcepcion | PalabraRepetidaExcepcion | PalabraInsuficienteExcepcion | PalabraNoEncontradaExcepcion | PalabraTamañoInsuficienteExcepcion | CaracterInvalidoExcepcion ex ){
             
        }
        if(e.getSource() == vista.btnEliminarConsulta){
            modelo.eliminarDatos(vista.palabraBuscar,vista.model2,vista.model4,vista.mostrarArchivo);
        }
    }
    
    
}
