
public class NodoFilePalabra {
    public String termino;
    public NodoNombreDocumento raiz;
    public NodoNombreDocumento archivo;//sig
    public int frecuencia; //documento = 1  dosDocumentos = 2
    NodoFilePalabra sig;
    
    NodoFilePalabra(String termino,NodoNombreDocumento archivo){
        this.termino = termino;
        this.raiz = archivo;
        this.archivo = archivo;
        this.frecuencia = 1;
    }
    
    void insertarDocumento(NodoNombreDocumento d){
        archivo.sig = d;
        archivo = archivo.sig;
    }
    
    boolean estaDocumento(String nombre){
        NodoNombreDocumento d;
        d = raiz;
        while(d != null){
            if(d.getNombre().equals(nombre)){
                return true;
            }
            d = d.sig;
        }
        return false;
    }
    
    public void setPlusFreq(){
        this.frecuencia = this.frecuencia + 1;
    }
    
    public void setFreq(int f){
        this.frecuencia = f;
    }
    
    String getTermino(){
        return this.termino;
    }
    
    NodoNombreDocumento getNodoNombreDocumento(){
        return this.raiz;
    }
    
    int getFrecuencia(){
        return this.frecuencia;
    }
}
