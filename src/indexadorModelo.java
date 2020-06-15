import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
    
    public void consultaAnd(JTextField palabraBuscar, DefaultListModel  model2, DefaultTableModel model) throws UnaPalabraExcepcion{
        StringTokenizer stk1 = new StringTokenizer(palabraBuscar.getText().toLowerCase());
        LinkedList<String> palabrasDoc = new LinkedList<String>();
        LinkedList<String> palabrasEncontradas = new LinkedList<String>();
        ListaPalabras queryAnd = new ListaPalabras();
        List<File> files2 = files;
        NodoFilePalabra addPalabra = null;
        NodoFilePalabra verificarPalabra = null;
        NodoNombreDocumento d = null;
        String palabra = "";
        
        
        model2.removeAllElements(); //ELIMINA LA LISTA DE RESULTADOS
        //ELIMINA LA ESTADISTICA
        if(model.getRowCount() > 0){
            for(int i=0; i < model.getRowCount(); i++){
                model.removeRow(i);
                i-=1;
            }
        }
        
        while(stk1.hasMoreTokens()){                
            addPalabra = indexado.getNodoPalabra(stk1.nextToken());
            if(addPalabra != null){
                NodoFilePalabra nuevo  = new NodoFilePalabra(addPalabra.getTermino(),addPalabra.raiz );
                nuevo.setFreq(addPalabra.getFrecuencia());
                queryAnd.insertar(nuevo);                
            }
            /*if(addPalabra != null){
                verificarPalabra = queryAnd.getNodoPalabra(addPalabra.getTermino());
                if(verificarPalabra == null ){
                    queryAnd.insertar(addPalabra);
                }
            }*/          
        }
        
        //queryAnd.mostrar();
        NodoFilePalabra p = queryAnd.getRaiz();        
        while(p!=null){
            palabrasEncontradas.add(p.getTermino());
            p = p.sig;
        }
        
        boolean banderaList = true;
        p = queryAnd.getRaiz();
        NodoFilePalabra p2 = p;
        NodoNombreDocumento doc = p.getNodoNombreDocumento();
        
        while(p != null){
            while(p.archivo != null){
                while(p2 != null){
                    if(p != p2){
                        //si lo encuentra, bandera es false
                        if(p2.estaDocumento(p.archivo.getNombre())){
                           banderaList = false;
                        }
                    }
                    if(!banderaList){
                        palabrasDoc.add(p.archivo.getNombre());
                    }
                    banderaList = true;
                    p2 = p2.sig;
                }
                p.archivo = p.archivo.sig;
            }
            p = p.sig;
        }
        
        //Si tienes elementos que los imprima
        if(palabrasDoc.size() != 0){
            for(String pa : palabrasDoc){
                model2.addElement(pa);
            }
            p = queryAnd.getRaiz();
            while(p != null){
                visualizarDatosAnd(p, d, model);     
                p = p.sig;
            }
        }
        
        
        
        
    }
    
    public void consultaNot(JTextField palabraBuscar, DefaultListModel  model2, DefaultTableModel model){
        model2.removeAllElements();
        NodoFilePalabra addPalabra = null;
        StringTokenizer stk1 = new StringTokenizer(palabraBuscar.getText().toLowerCase());
        NodoNombreDocumento d = null;
        LinkedList<String> listaPalabrasJText = new LinkedList<String>();
        LinkedList<String> listaDocumentos = new LinkedList<String>();
        String palabra = "";
        boolean bandera2 = true;
            

        //ELIMINA LA jtable ESTADISTICA
        if(model.getRowCount() > 0){
            for(int i=0; i < model.getRowCount(); i++){
                model.removeRow(i);
                i-=1;
            }
        }
        //Almacena todas las palabras a buscar en listaString
        while(stk1.hasMoreTokens()){
            palabra = stk1.nextToken();
            listaPalabrasJText.add(palabra);
           // System.out.println("Palabra a buscar/" + palabra );
        }
        //Inserta las palabras a buscar
        for(String n : listaPalabrasJText){
            addPalabra = indexado.getNodoPalabra(n);
            if(addPalabra != null){
                d = addPalabra.getNodoNombreDocumento();
                while(d != null){
                    for(String nombre : listaDocumentos){
                        if(d.getNombre().equals(nombre)){
                            bandera2 = false;
                        }
                    }
                    if(!bandera2){
                       listaDocumentos.add(d.getNombre());
                    }
                    bandera2 = false;    
                    d = d.sig;
                }
            }
        }

        List<File> files2 = files;
        for(String s : listaDocumentos){
            for(int i = 0; i < files2.size(); i++){
                if(s.equals(files2.get(i).getName() )){
                    files2.remove(i);
                }
            }    
        }
        listaDocumentos = null;
        listaPalabrasJText = null;
        for(File file: files2){
            model2.addElement(file.getName());
        }
        
        files2 = null;
       
    }
    
    public void consulta(JTextField palabraBuscar, DefaultListModel  model2, DefaultTableModel model) throws UnaPalabraExcepcion{
        NodoFilePalabra addPalabra = null;
        StringTokenizer stk1 = new StringTokenizer(palabraBuscar.getText().toLowerCase());
        NodoNombreDocumento d = null;
        String[] palexcep = palabraBuscar.getText().toLowerCase().split(" ");
       
        if(palexcep.length == 1){
            model2.removeAllElements(); //ELIMINA LA LISTA DE RESULTADOS
            //ELIMINA LA ESTADISTICA
            if(model.getRowCount() > 0){
                for(int i=0; i < model.getRowCount(); i++){
                    model.removeRow(i);
                    i-=1;
                }
            }

            while(stk1.hasMoreTokens()){                
                addPalabra = indexado.getNodoPalabra(stk1.nextToken());
                visualizarDatos(addPalabra, d, model2, model);                
            }
        }
        else
            throw new UnaPalabraExcepcion();
       
    }
    
    public void consultaOR(JTextField palabraBuscar, DefaultListModel  model2, DefaultTableModel model) throws PalabraRepetidaExcepcion{
        String palabra;
        NodoFilePalabra addPalabra = null;
        StringTokenizer stk1 = new StringTokenizer(palabraBuscar.getText().toLowerCase());
        NodoNombreDocumento d = null;
        String temporal = palabraBuscar.getText().toLowerCase(), verifica = null;
        String[] palexcep = temporal.split(" ");
        boolean bandera = false;

        model2.removeAllElements();
        for(int n = 0 ; n < palexcep.length ; n++){
            verifica = palexcep[n] ;
            for(int m = n+1; m < palexcep.length ; m++){
                if (verifica.equals(palexcep[m])){
                    bandera = true;
                    break;
                }
                else
                    bandera = false;
            }
        }
        if(bandera == false){
            if(model.getRowCount() > 0){
                for(int i=0; i < model.getRowCount(); i++){
                   model.removeRow(i);
                    i-=1;
                }
            }
            while(stk1.hasMoreTokens()){ 
                palabra = stk1.nextToken();
                addPalabra = indexado.getNodoPalabra(palabra);
                visualizarDatos(addPalabra, d, model2, model);
            }
        }
        if(bandera == true)
            throw new PalabraRepetidaExcepcion();
    }    
    
    public void limpiarListaDeCarga(DefaultListModel modeloCargarArchivos){
        modeloCargarArchivos.removeAllElements();
    }
    //1.- OBTENER LA DIRECCION DE LOS ARCHIVOS DE TXT EN UNA LISTA DE TIPO FILE
    //2.- ABRIR LOS ARCHIVOS DESDE SU DIRECCION ABSOLUTA Y PODER INDEXAR EN LA ListaPalabras INDEXADO
    
    //          METODO DE VALERIA
    public void cargarArchivos(DefaultListModel modeloCargarArchivos,DefaultListModel modeloCargaCompleta){
        
        modeloCargaCompleta.removeAllElements();
        
        FileReader lectura = null;
        BufferedReader lectBuff = null;   
        int contador = 1;
        String palabra;
        boolean bandera = false;
        NodoFilePalabra archivo = null;
        char[] caracteres;
        StringBuffer pal = new StringBuffer();
        String[] subcadena, nombreDoc;
        String separador = Pattern.quote("\\");
        
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
                    nombreDoc = modeloCargarArchivos.getElementAt(i).toString().split(separador);
                    //LEE LINEAS
                    // SI LA PALABRA ES REPETIDA----
                    while(( palabra = lectBuff.readLine())!=null) { 
                        caracteres = palabra.toLowerCase().toCharArray();
                        for(int n = 0; n < caracteres.length ; n++){
                            if( Character.isLowerCase(caracteres[n])){
                                caracteres[n] = caracteres[n];
                            }
                            else
                                caracteres[n] = '-';
                        }
                        
                        for (int j = 0 ; j < caracteres.length ; j++)
                            pal = pal.append(caracteres[j]);
                        
                        subcadena = pal.toString().split("-");
                        pal = new StringBuffer();
                        
                        for (int m=0;m<subcadena.length;m++){
                            if(subcadena[m].length() > 3){
                                bandera = indexado.comparar(new NodoFilePalabra(subcadena[m],new NodoNombreDocumento(nombreDoc[nombreDoc.length-1],Integer.toString(contador))));
                                if(bandera == false)
                                    indexado.insertar(new NodoFilePalabra(subcadena[m],new NodoNombreDocumento(nombreDoc[nombreDoc.length-1],Integer.toString(contador) )));
                               
                                if(lectBuff == null){
                                    archivo = indexado.getNodoPalabra(subcadena[m]);
                                    archivo.setPlusFreq();
                                }
                            }
                        }
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
//      indexado.mostrar();
        
    }
    
    public void visualizarDatos(NodoFilePalabra addPalabra,NodoNombreDocumento d ,DefaultListModel  model2, DefaultTableModel model){
        if(addPalabra != null){
            model2.addElement(addPalabra.getNodoNombreDocumento().getNombre());
            //System.out.println("Palabra Encontrada: |" + addPalabra.termino + "|");
            model.addRow(
                    new Object[]{
                        addPalabra.termino,
                        addPalabra.frecuencia,
                        addPalabra.getNodoNombreDocumento().nombre,
                        addPalabra.getNodoNombreDocumento().contador,
                        addPalabra.getNodoNombreDocumento().linea
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
   
    public void visualizarDatosAnd(NodoFilePalabra addPalabra,NodoNombreDocumento d , DefaultTableModel model){
        if(addPalabra != null){
            //System.out.println("Palabra Encontrada: |" + addPalabra.termino + "|");
            model.addRow(
                    new Object[]{
                        addPalabra.termino,
                        addPalabra.frecuencia,
                        addPalabra.getNodoNombreDocumento().nombre,
                        addPalabra.getNodoNombreDocumento().contador,
                        addPalabra.getNodoNombreDocumento().linea
                    }
            );
            d = addPalabra.getNodoNombreDocumento();
            d = d.sig;
            while(d != null){             
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
