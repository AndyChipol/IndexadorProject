
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author andre
 */
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
    }
    
    public void run(){
        vista.setLocationRelativeTo(null);
        //vista.setSize(1079, 696);
    }
    
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == vista.cargarLista){
            modelo.cargarArchivos(vista.model,vista.model3);
        }
        if(e.getSource() == vista.eliminarCargaLista){
            modelo.limpiarListaDeCarga(vista.model);
            
        }
        if(e.getSource() == vista.btnHacerConsulta){
            modelo.consulta(vista.palabraBuscar,vista.model2,vista.model4);
            
        }
        if(e.getSource() == vista.btnEliminarConsulta){
            
        }
    }
    
    
}
