
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class PalabraRepetidaExcepcion extends Exception{
    Excepcion e = new Excepcion("PALABRA REPETIDA");
    public PalabraRepetidaExcepcion(){      
        e.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        e.setVisible(true);
        e.setResizable(false);
    }
}




