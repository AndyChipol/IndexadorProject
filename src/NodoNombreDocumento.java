public class NodoNombreDocumento {
    public String nombre; //nobre del documento
    public int contador;  //Veces que aparece en el Documento
    public String linea;  // en Que linea aparece 1,3,4,7,8,8,8 
    public NodoNombreDocumento sig; //nodo
    
    NodoNombreDocumento(String nombre, String linea){
        this.nombre = nombre;
        this.contador = 1;
        this.linea = linea;
    }
    
    
    void setPlusContador(){
        contador++;
    }
    
    void setLinea(String i){
        linea = linea + "," + i;
    }
    
    String getNombre(){
        return this.nombre;
    }
    
    String getLinea(){
        return this.linea;
    }
    
    int getContador(){
        return this.contador;
    }
}
