import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andres&Valeria
 */
public class indexadorModelo {
    private int x;
    DefaultListModel modeloCargarArchivos;
    DefaultListModel modelDos;
    ListaPalabras indexado;
    List<File>  files;
    public indexadorModelo(){
        indexado = new ListaPalabras();
        files = new ArrayList<File>();
        this.modelDos = new DefaultListModel();
        this.x = 10;
    }
    
    public List<File> getLista(){
        return files;
    }
    
    public void consulta(JTextField palabraBuscar, DefaultListModel  model2, DefaultTableModel model){
        String palabra;
        NodoFilePalabra addPalabra = null;
        ListaPalabras encontradas = new ListaPalabras();
        
        StringTokenizer stk1 = new StringTokenizer(palabraBuscar.getText().toLowerCase());
        NodoNombreDocumento d = null;
        
        model2.removeAllElements();
        
        if(model.getRowCount() > 0){
            for(int i=0; i < model.getRowCount(); i++){
                model.removeRow(i);
                i-=1;
            }
        }

        while(stk1.hasMoreTokens()){
            palabra = stk1.nextToken();
            addPalabra = indexado.getNodoPalabra(palabra);
            if(addPalabra != null){
                model2.addElement(addPalabra.getNodoNombreDocumento().getNombre());
                //System.out.println("Palabra Encontrada: |" + addPalabra.termino + "|");
                model.addRow(
                        new Object[]{
                            addPalabra.termino,
                            addPalabra.frecuencia,
                            addPalabra.archivo.nombre,
                            addPalabra.archivo.contador,
                            addPalabra.archivo.linea
                        }
                );
                
                d = addPalabra.getNodoNombreDocumento();
                d = d.sig;
                while(d != null){                    
                    model2.addElement(d.nombre);
                    model.addRow(
                        new Object[]{
                            "",
                            "",
                            d.nombre,
                            d.contador,
                            d.linea
                        }
                    );
                    d = d.sig;
                }
            }
        }
        
    }
    
    public void limpiarListaDeCarga(DefaultListModel modeloCargarArchivos){
        modeloCargarArchivos.removeAllElements();
    }
    
    //1.- OBTENER LA DIRECCION DE LOS ARCHIVOS DE TXT EN UNA LISTA DE TIPO FILE
    //2.- ABRIR LOS ARCHIVOS DESDE SU DIRECCION ABSOLUTA Y PODER INDEXAR EN LA ListaPalabras INDEXADO
    
    //          METODO DE VALERIA
    public void cargarArchivos(DefaultListModel modeloCargarArchivos,DefaultListModel modeloCargaCompleta){
        
        modeloCargaCompleta.removeAllElements();
        
        File indexando = null;
        FileReader lectura = null;
        BufferedReader lectBuff = null;
        String s,d;               
        int contador = 1;
        String palabra;
        boolean bandera = false;
        NodoFilePalabra archivo2;
        /*
            VERIFICAR LA PALABRA
                VERIFICAR SI ESTA EN EL MISMO ARCHIVO
        
        */
        for(int i=0; i < modeloCargarArchivos.size(); i++){
            contador = 1;
            if(modeloCargarArchivos.getElementAt(i).toString().toLowerCase().endsWith(".txt") ){
                files.add((File)modeloCargarArchivos.getElementAt(i));
                   try {
                    lectura = new FileReader((File)modeloCargarArchivos.getElementAt(i));
                    lectBuff = new BufferedReader(lectura);
                    //LEE LINEAS
                    // SI LA PALABRA ES REPETIDA----
                    
                    while(( lectBuff.readLine())!=null) {
                        
                        indexado.insertar(
                                new NodoFilePalabra(
                                        "Palabra",
                                        new NodoNombreDocumento(
                                                "NombreDelDocumento",
                                                "contador" 
                                        )
                                )
                        );
                        contador++;
                    }
                    lectBuff.close();
                    modeloCargaCompleta.addElement(modeloCargarArchivos.getElementAt(i));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(indexadorVista.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(indexadorVista.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        }
        
        indexado.mostrar();
        
    }
    
}
