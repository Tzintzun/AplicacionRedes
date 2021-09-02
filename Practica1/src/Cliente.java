
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;



/**
 *
 * @author david
 */
public class Cliente {
    private static int pto = 8000;
    private static String dir = "localhost";
    
    public static boolean enviarInfo(File[] f){
        try{
            Socket cl = conectar(dir,pto);
            int tam = f.length;
            String[] nombres = new String[tam];
            boolean[] directorio = new boolean[tam];
            long[] tams = new long[tam];
            for(int i =0;i<tam;i++){
                nombres[i] = f[i].getName();
                if(f[i].isDirectory()){
                    tams[i] = 0;
                    directorio[i]=true;  
                }else{
                    tams[i] = f[i].length();
                    directorio[i]=false;  
                }
                
            }
            ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
            oos.writeObject(nombres);
            oos.flush();
            oos.writeObject(tams);
            oos.flush();
            oos.writeObject(directorio);

            oos.close();
            cl.close();
            System.out.println("Preparandose pare enviar " + f.length+ " archivos.\n");
            for(int i=0;i<f.length;i++){
              System.out.println("\t"+i+". "+ nombres[i] +" "+tams[i]+"bytes" );
            } 
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        
    }
    
    public static boolean enviarArchivo(File f){
        try{
            Socket cl2 = conectar(dir,pto+1);
            DataOutputStream dos = new DataOutputStream(cl2.getOutputStream());
            DataInputStream dis = new DataInputStream(new FileInputStream(f));
            System.out.println("Enviando el archivo \"" +f.getName()+"\": ");
            long tamañoActual = f.length();
            long enviados = 0;
            int porcentaje=0;
            int l=0;
            while(enviados<tamañoActual){
                byte[] b = new byte[1500];
                l=dis.read(b);
                System.out.println("enviados: "+l);
                dos.write(b,0,l);
                dos.flush();
                enviados = enviados + l;
                porcentaje = (int)((enviados*100)/tamañoActual);
                System.out.print("\rEnviado el "+porcentaje+" % del archivo");
            }//while
            System.out.println("\nArchivo enviado..\n\n");
            cl2.close();
            dos.close();
            dis.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean enviar_archivos(File[] f){
        if(!enviarInfo(f)){
            return false;
        }
        
        for(int i = 0; i<f.length;i++){
            if(f[i].isDirectory()){
                if(!enviar_archivos(f[i].listFiles())){
                    System.out.println("Error al enviar el directorio \""+f[i].getAbsolutePath()+"\"");
                }
            }else{
                if(!enviarArchivo(f[i])){
                    System.out.println("El archivo \""+f[i].getName()+"\" no pudo enviarse");
                }
            }
        }
        return true;
    }
    
    public static void main(String[] args){
        JFileChooser jf = new JFileChooser();
        jf.setMultiSelectionEnabled(true);
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int r = jf.showOpenDialog(null);
           
        if(r == JFileChooser.APPROVE_OPTION){
            File[] f = jf.getSelectedFiles();
            enviar_archivos(f);
            
        }
        
        
    }
    
    
    public static Socket conectar(String dir, int puerto) {
        Socket socket;
        for(;;){
            try{
                socket = new Socket(dir,puerto);
                break;
            }catch (Exception e){
                System.out.println("Contectando...");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    e.printStackTrace();
                }
            }
        }
        return socket;
    }
}
