
import java.io.File;
import java.util.Date;
import javax.swing.JFileChooser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 */
public class ClasePrueba {
    
    public static void main(String[] args){
        /*JFileChooser jf = new JFileChooser();
        jf.setMultiSelectionEnabled(true);
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int r = jf.showOpenDialog(null);
           
        if(r == JFileChooser.APPROVE_OPTION){
            File[] f = jf.getSelectedFiles();
            System.out.println(f[0].getAbsolutePath());
           
            
        }*/
        
        Date fecha=new Date();
        System.out.println(fecha.toString().replace(':', '_'));
    }
    
}
