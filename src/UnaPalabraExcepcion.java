
import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class UnaPalabraExcepcion extends Exception {
    Excepcion e = new Excepcion("UNA PALABRA PERMITIDA");
    public UnaPalabraExcepcion(){
        e.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        e.setVisible(true);
    }
}
