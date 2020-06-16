//Proyecto Final
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
import javax.swing.JTextArea;
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
    public void consultaAnd(JTextField palabraBuscar, DefaultListModel  model2, DefaultTableModel model) throws UnaPalabraExcepcion, CaracterInvalidoExcepcion, PalabraTamañoInsuficienteExcepcion, PalabraInsuficienteExcepcion, PalabraNoEncontradaExcepcion, PalabraRepetidaExcepcion,ConsultaNoEncontraExcepcion{
        StringTokenizer stk1 = new StringTokenizer(palabraBuscar.getText().toLowerCase());
        LinkedList<String> palabrasDoc = new LinkedList<String>();
        LinkedList<String> palabrasEncontradas = new LinkedList<String>();
        ListaPalabras queryAnd = new ListaPalabras();
        List<File> files2 = files;
        NodoFilePalabra addPalabra = null;
        NodoFilePalabra verificarPalabra = null;
        String temporal = palabraBuscar.getText().toLowerCase(), verifica = null;
        String[] palexcep = temporal.split(" ");
        String palabra = "", palabra2;
        char[] caracteresInvalid = null;
        boolean bandera = false;
        
        if(palexcep.length <= 1){
           throw new PalabraInsuficienteExcepcion(); //
        }
        if(palexcep.length > 1){
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
        
                while(stk1.hasMoreTokens()){
                    palabra2 = stk1.nextToken();
                    caracteresInvalid = palabra2.toCharArray();
                    for(int l = 0; l<caracteresInvalid.length; l++){
                        if(Character.isUpperCase(caracteresInvalid[l]) == false && Character.isLowerCase(caracteresInvalid[l]) == false  ){
                            throw new CaracterInvalidoExcepcion();
                        }
                    }
                    if(palabra2.length()<=3){
                        throw new PalabraTamañoInsuficienteExcepcion();
                    }
                    else{
                        addPalabra = indexado.getNodoPalabra(palabra2);
                        if(addPalabra == null){
                            throw new PalabraNoEncontradaExcepcion();
                        }
                        if(addPalabra != null){
                        
                            NodoFilePalabra nuevo  = new NodoFilePalabra(addPalabra.getTermino(),addPalabra.raiz );
                            nuevo.setFreq(addPalabra.getFrecuencia());
                            queryAnd.insertar(nuevo);                
                        }
                    }
                }
                
                model2.removeAllElements(); //ELIMINA LA LISTA DE RESULTADOS
                if(model.getRowCount() > 0){//ELIMINA LA ESTADISTICA
                    for(int i=0; i < model.getRowCount(); i++){
                        model.removeRow(i);
                        i-=1;
                    }
                }
        
                NodoFilePalabra p = queryAnd.getRaiz();        
                while(p!=null){
                    palabrasEncontradas.add(p.getTermino());
                    p = p.sig;
                }
        
                boolean banderaList = true;
                p = queryAnd.getRaiz();
                NodoFilePalabra p2 = p;
                NodoNombreDocumento doc = p.getNodoNombreDocumento();
                
                 
                p = queryAnd.getRaiz();
                NodoNombreDocumento p4;
                
                while(p != null){
                    System.out.println(p.getTermino());
                    p4 = p.getNodoNombreDocumento();
                    while(p4 != null){
                        System.out.println("\t" + p4.getNombre());
                        p2 = queryAnd.getRaiz();
                        while(p2 != null){
                            if(!p.termino.equals(p2.termino)){
                                //si lo encuentra, bandera es false
                                if(p2.estaDocumento(p4.getNombre())){
                                    banderaList = false;
                                }
                                else{
                                    banderaList = true;
                                    break;
                                }
                            }   
                            p2 = p2.sig;
                        }
                        if(!banderaList){
                            boolean bandera5 = false;
                            for(String n : palabrasDoc){
                                if(n.equals(p4.getNombre())){
                                    bandera5 = true;
                                    break;
                                }
                            }
                            if(!bandera5){
                                palabrasDoc.add(p4.getNombre());    
                            }
                            
                        }
                        banderaList = true;
                        p4 = p4.sig;
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
                        visualizarDatosAnd(p, p.getNodoNombreDocumento(), model);
                        p = p.sig;
                    }
                }else{
                    throw new ConsultaNoEncontraExcepcion();
                }
               
            }
            if(bandera == true)
                throw new PalabraRepetidaExcepcion();
        }
        
    }
    
    public void consultaNot(JTextField palabraBuscar, DefaultListModel  model2, DefaultTableModel model) throws PalabraInsuficienteExcepcion, CaracterInvalidoExcepcion, UnaPalabraExcepcion, PalabraTamañoInsuficienteExcepcion, PalabraNoEncontradaExcepcion{
        
        NodoFilePalabra addPalabra = null;
        StringTokenizer stk1 = new StringTokenizer(palabraBuscar.getText().toLowerCase());
        NodoNombreDocumento d = null;
        LinkedList<String> listaPalabrasJText = new LinkedList<String>();
        LinkedList<String> listaDocumentos = new LinkedList<String>();
        String palabra = "";
        boolean bandera2 = true;
        String temporal = palabraBuscar.getText().toLowerCase();
        char[] caracteresInvalid = null;
        String[] palexcep = temporal.split(" ");    

      
        if(temporal.isEmpty() == true){
            throw new PalabraInsuficienteExcepcion(); //
        }
        //Almacena todas las palabras a buscar en listaString
        if(palexcep.length == 1){
            while(stk1.hasMoreTokens()){
                palabra = stk1.nextToken();
                caracteresInvalid = palabra.toCharArray();
                for(int l = 0; l<caracteresInvalid.length; l++){
                    if(Character.isUpperCase(caracteresInvalid[l]) == false && Character.isLowerCase(caracteresInvalid[l]) == false  ){
                        throw new CaracterInvalidoExcepcion();
                    }
                }
                if(palabra.length()<=3){
                        throw new PalabraTamañoInsuficienteExcepcion();
                    }
                listaPalabrasJText.add(palabra);
            }
            model2.removeAllElements();
            //ELIMINA LA jtable ESTADISTICA
            if(model.getRowCount() > 0){
                for(int i=0; i < model.getRowCount(); i++){
                    model.removeRow(i);
                    i-=1;
                }
            }
            //Inserta las palabras a buscar
            for(String n : listaPalabrasJText){
                addPalabra = indexado.getNodoPalabra(n);
                if(addPalabra != null){
                    d = addPalabra.getNodoNombreDocumento();
                    while(d != null){
                        if(listaDocumentos.size() == 0){
                            bandera2 = false;                            
                        }else{
                            for(String nombre : listaDocumentos){
                                if(d.getNombre().equals(nombre)){
                                    bandera2 = false;
                                    break;
                                }
                            }
                        }
                        if(!bandera2){
                            listaDocumentos.add(d.getNombre());
                        }
                        bandera2 = false;    
                        d = d.sig;
                    }
                }
                else{
                    throw new PalabraNoEncontradaExcepcion();
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
        }
        else
            throw new UnaPalabraExcepcion(); 
    }
    
    public void consulta(JTextField palabraBuscar, DefaultListModel  model2, DefaultTableModel model) throws UnaPalabraExcepcion, PalabraInsuficienteExcepcion, PalabraNoEncontradaExcepcion, PalabraTamañoInsuficienteExcepcion, CaracterInvalidoExcepcion{
        String palabra;
        NodoFilePalabra addPalabra = null;
        
        StringTokenizer stk1 = new StringTokenizer(palabraBuscar.getText().toLowerCase());
        NodoNombreDocumento d = null;
        String temporal = palabraBuscar.getText().toLowerCase();
        String[] palexcep = temporal.split(" ");
        char[] caracteresInvalid = null;
        boolean bandera = false;
        
        model2.removeAllElements();
        
        if(model.getRowCount() > 0){
            for(int i=0; i < model.getRowCount(); i++){
                model.removeRow(i);
                i-=1;
            }
        }
        if(temporal.isEmpty() == true){
            throw new PalabraInsuficienteExcepcion(); //
        }
        else{
            if(palexcep.length == 1){
                while(stk1.hasMoreTokens()){
                    palabra = stk1.nextToken();
                    caracteresInvalid = palabra.toCharArray();
                    for(int l = 0; l<caracteresInvalid.length; l++){
                        if(Character.isUpperCase(caracteresInvalid[l]) == false && Character.isLowerCase(caracteresInvalid[l]) == false  ){
                            throw new CaracterInvalidoExcepcion();
                        }
                    }
                    if(palabra.length()<=3){
                        throw new PalabraTamañoInsuficienteExcepcion();
                    }
                
                    addPalabra = indexado.getNodoPalabra(palabra);
                
                    if(addPalabra == null){
                        throw new PalabraNoEncontradaExcepcion();
                    }
               
                    visualizarDatos(addPalabra, d, model2, model);
                }
            }
        else
            throw new UnaPalabraExcepcion();
        }
        
    }
    
    public void consultaOR(JTextField palabraBuscar, DefaultListModel  model2, DefaultTableModel model) throws PalabraRepetidaExcepcion, PalabraInsuficienteExcepcion, PalabraNoEncontradaExcepcion, PalabraTamañoInsuficienteExcepcion, CaracterInvalidoExcepcion{
        String palabra;
        NodoFilePalabra addPalabra = null;
        
        StringTokenizer stk1 = new StringTokenizer(palabraBuscar.getText().toLowerCase());
        NodoNombreDocumento d = null;
        String temporal = palabraBuscar.getText().toLowerCase(), verifica = null;
        String[] palexcep = temporal.split(" ");
        boolean bandera = false;
        model2.removeAllElements();
        char[] caracteresInvalid = null;
        if(palexcep.length <= 1){
           throw new PalabraInsuficienteExcepcion(); //
        }
        if(palexcep.length > 1){
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
                    caracteresInvalid = palabra.toCharArray();
                    for(int l = 0; l<caracteresInvalid.length; l++){
                        if(Character.isUpperCase(caracteresInvalid[l]) == false && Character.isLowerCase(caracteresInvalid[l]) == false  ){
                            throw new CaracterInvalidoExcepcion();
                        }
                    }
                    if(palabra.length()<=3){
                        throw new PalabraTamañoInsuficienteExcepcion();
                    }
                    else{
                    
                        addPalabra = indexado.getNodoPalabra(palabra);
                
                        if(addPalabra == null){
                            throw new PalabraNoEncontradaExcepcion();
                        }
                        
                        if(addPalabra != null){
                            
                            if(model2.contains(addPalabra.getNodoNombreDocumento().getNombre())== false){
                              model2.addElement(addPalabra.getNodoNombreDocumento().getNombre());  
                            }
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
                                if(model2.contains(d.nombre) == false){
                                   model2.addElement(d.nombre); 
                                }
                                
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
            }
            if(bandera == true)
                throw new PalabraRepetidaExcepcion();
        }
    }
    
    public void limpiarListaDeCarga(DefaultListModel modeloCargarArchivos) throws ListaVaciaExcepcion{
        if(modeloCargarArchivos.isEmpty() == true){
            throw new ListaVaciaExcepcion();
        }
        else
        modeloCargarArchivos.removeAllElements();
    }

    public void cargarArchivos(DefaultListModel modeloCargarArchivos,DefaultListModel modeloCargaCompleta,DefaultListModel modeloExc) throws ArchivoNoInsertadoExcepcion, ConsultaNoEncontraExcepcion{
        
        modeloCargaCompleta.removeAllElements();
        FileReader lectura = null;
        BufferedReader lectBuff = null;
        int contador = 1;
        String palabra;
        boolean bandera = false, bandera2 = false;
        NodoFilePalabra archivo = null;
        char[] caracteres;
        StringBuffer pal = new StringBuffer();
        String[] subcadena, nombreDoc;
        String separador = Pattern.quote("\\");
        if(modeloCargarArchivos.isEmpty()==true){
            throw new ArchivoNoInsertadoExcepcion();
        }
        else{
            for(int k=0 ; k<modeloCargarArchivos.size(); k++){
                if(files.contains((File)modeloCargarArchivos.getElementAt(k))){
                    bandera2=true;
                    break;
                }
            }
            if(bandera2 == false){
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
                    else{
                        modeloExc.addElement(modeloCargarArchivos.getElementAt(i).toString());
                        
                    }
                }
            //indexado.mostrar();
            }
            else{
                throw new ConsultaNoEncontraExcepcion();
            }
        }
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
    
    public void eliminarDatos(JTextField palabraBuscar, DefaultListModel  model2, DefaultTableModel model, JTextArea model3) {
        palabraBuscar.setText("");
        model3.setText("");
        model2.removeAllElements();
        if(model.getRowCount() > 0){
            for(int i=0; i < model.getRowCount(); i++){
                model.removeRow(i);
                i-=1;
            }
        }
    }
}


