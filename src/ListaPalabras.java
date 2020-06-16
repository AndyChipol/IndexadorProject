//Proyecto Final
public class ListaPalabras {
    NodoFilePalabra raiz;
    NodoFilePalabra actual;
    
    ListaPalabras(){
        this.raiz = null;
        this.actual = null;
    }
    ListaPalabras(NodoFilePalabra p){
        raiz = p;
        actual = p;
    }
    
    void insertar(NodoFilePalabra p){
        if(raiz == null){
            raiz = p;
            actual = p;
        }else{
            actual.sig = p;
            actual = actual.sig;
        }
    }
    
       NodoFilePalabra getRaiz(){
       return this.raiz;
    }
    
    boolean comparar(NodoFilePalabra palabra){
        NodoFilePalabra p = raiz;
        while(p != null){
            if(p.termino.equals(palabra.termino) && palabra.getTermino().length() > 3){                
                if(p.archivo.getNombre().equals(palabra.archivo.nombre)){
                    p.archivo.setPlusContador();
                    p.archivo.setLinea(palabra.archivo.getLinea());
                }else{
                    p.insertarDocumento(palabra.getNodoNombreDocumento());
                    p.setPlusFreq();
                }
                return true;                
            }
            p = p.sig;
        }
        return false;
    }
    
    NodoFilePalabra getNodoPalabra(String palabra){
        NodoFilePalabra p = this.raiz;
        while(p != null){
            if(p.termino.equals(palabra)){
                //System.out.println("Buscando..." + p.termino);
                break;
            }       
            p = p.sig;
        }
        return p;
    }
    
    NodoFilePalabra getNodoPalabraNot(String palabra){
        NodoFilePalabra p = this.raiz;
        NodoFilePalabra n = null;
        while(p != null){
            if(!p.termino.equals(palabra)){
                
            }
            p = p.sig;
        }
        return p;
    }
    
    void mostrar(){
        NodoFilePalabra p = raiz;
        NodoNombreDocumento c;
        while(p != null){
            System.out.print("\n"+ p.getTermino() );
            c = p.getNodoNombreDocumento();
            System.out.print("\n\tFrecuencia:" + p.getFrecuencia() );
            while(c!= null){
                System.out.print( "\n\t-> Documento:   " + c.getNombre() +"\n\t\t\tNo. en Documento: " + c.getContador() + "\n\t\t\tNo.Linea " + c.getLinea());
                c = c.sig;
            }
            p = p.sig;
        }
    }
    
    int size(){
        int contador = 0;
        NodoFilePalabra p = raiz;
        while(p != null){
            contador++;
           p = p.sig;
        }
        return contador;
    }
    
    boolean isEmpty(){
        if(raiz == null)
            return true;
        return false;
    }
    
    
}
