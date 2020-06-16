
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dell
 */
public class ConsultaNoEncontraExcepcion extends Exception{
    Excepcion e = new Excepcion("NO ENCONTRADA");
        public ConsultaNoEncontraExcepcion(){
        e.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        e.setVisible(true);
        e.setResizable(false);
    }
}
