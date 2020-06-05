import javax.swing.JFrame;
/**
 *
 * @author andre
 */
public class Main {
    public static void main(String[] args){
        indexadorVista v = new indexadorVista();
        indexadorModelo m = new indexadorModelo();
        indexadorControlador c = new indexadorControlador(m,v);
        c.run();
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        v.setVisible(true);
    } 
}
