
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 */
public class Compresor {
   
    
    public void CrearComprimido(File[] files,String ruta,ZipOutputStream zip){
        
        
        try{
           for(File f : files){
               if(f.isDirectory()){
                   
                   CrearComprimido(f.listFiles(),ruta+f.getName()+"\\",zip);
               }else{
                   byte[] buffer = new  byte[1024];
                   int leido = 0;
                   FileInputStream archivo = new FileInputStream(f);
                   zip.putNextEntry(new ZipEntry(ruta+f.getName()));
                   while((leido = archivo.read(buffer)) > 0){
                       System.out.println("Escribiendo...");
                       zip.write(buffer);
                   }
                   zip.closeEntry();
               }
           }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
    
    public File comprimir(File[] f, String nombreZip){
        
        try {
            File crudo = new File(".\\temp\\"+nombreZip);
            FileOutputStream archivo = new FileOutputStream(crudo);
            ZipOutputStream zip = new ZipOutputStream(archivo);
            CrearComprimido(f,"",zip);
            zip.flush();
            zip.close();
            archivo.close();
            return crudo;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public File comprimir(String carpeta,String nombre){
        File f = new File(".\\"+carpeta+"\\");
        return comprimir(f.listFiles(),nombre);
    }
    
    
}
