
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;



/**
 *
 * @author david
 */
public class Cliente {
    private int pto;
    private String dir;
    private Socket cl;
    BufferedOutputStream bos;
    DataOutputStream dos;
    
    public Cliente(){
        this.pto = 8000;
        this.dir = "localhost";
    }
    
    public void createConnection(){
        try{
           cl = new Socket(dir, pto);
           System.out.println("Cliente conectado al server \n "); 
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void instruccion(int i){
        
        try{
            createConnection();
            bos = new BufferedOutputStream(cl.getOutputStream());
            dos = new DataOutputStream(bos);

            dos.writeInt(i);    //enviamos un 0 al server que significa enviar archivos
            dos.flush();
            bos.flush();
            // dos.close();
            // bos.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setPath(String path) {
        try {
            dos.writeUTF(path);
            dos.flush();
            bos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean enviarInfo(File[] f){
        try{
            
            cl = conectar(dir,pto);
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
    
    public boolean enviarArchivo(File f){
        try{
            Socket cl2 = conectar(dir,pto+1);
            DataOutputStream dos = new DataOutputStream(cl2.getOutputStream());
            DataInputStream dis = new DataInputStream(new FileInputStream(f));
            System.out.println("Enviando el archivo \"" +f.getName()+"\": ");
            long tama??oActual = f.length();
            long enviados = 0;
            int porcentaje=0;
            int l=0;
            while(enviados<tama??oActual){
                byte[] b = new byte[1500];
                l=dis.read(b);
                System.out.println("enviados: "+l);
                dos.write(b,0,l);
                dos.flush();
                enviados = enviados + l;
                porcentaje = (int)((enviados*100)/tama??oActual);
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
    public boolean enviar_archivos(File[] f){
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
    
    public ArrayList <String> getServerArchivos() throws IOException{
        ArrayList <String> arrayFilesInServer = new ArrayList();
        
        cl = conectar(dir,pto);
        
        BufferedInputStream bis = new BufferedInputStream(cl.getInputStream());
        DataInputStream dis = new DataInputStream(bis);
            
        int totFiles = dis.readInt();
        String nameFile = "";
        for (int i = 0; i < totFiles; i++) {
            nameFile = dis.readUTF();
            arrayFilesInServer.add(nameFile);
        } 
        bis.close();
        dis.close();
        
        System.out.println("Lista recibida " );
        cl.close();
        
        return arrayFilesInServer;
    }
    
    public void recibirArchivo( String archivo,String ruta,long tam){
        try{
            Socket datos = conectar(dir,pto+1);
            System.out.println("Cliente conectado desde "+datos.getInetAddress()+":"+datos.getPort());
            
            DataInputStream dis = new DataInputStream(datos.getInputStream());
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(ruta +"\\"+ archivo));
            System.out.println("Comienza descarga del archivo \""+archivo+"\" de "+tam+" bytes\n\n");
            System.out.println("En la ruta \"" + ruta +"\"");
            
            long recibidos = 0;
            int l=0,porcentaje=0;
            
            while(recibidos<tam){
                byte[] b = new byte[1500];
                l = dis.read(b);
                System.out.println("leidos: "+l);
                dos.write(b,0,l);
                dos.flush();
                recibidos = recibidos + l;
                porcentaje = (int)((recibidos*100)/tam);
                System.out.print("\rRecibido el "+ porcentaje +" % del archivo");
            }//while
            System.out.println("Archivo recibido..\n\n");
            dos.close();
            dis.close();
            datos.close();
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void recibir_archivo(){
        File f = new File(".\\Descargas\\");
        if(!f.exists()){
            f.mkdirs();
        }
        
        JFileChooser jf = new JFileChooser(f);
        jf.setMultiSelectionEnabled(false);
        jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int r = jf.showOpenDialog(null);
           
        if(r == JFileChooser.APPROVE_OPTION){
            f = jf.getSelectedFile();
        }else{
            return;
        }
        try{
            Socket conexion = conectar(dir,pto);
            DataInputStream dis = new DataInputStream(conexion.getInputStream());
            String nombre = dis.readUTF();
            long tama??o = dis.readLong();
            System.out.println("Informacion Recibida");
            
            
            recibirArchivo(nombre,f.getAbsolutePath(),tama??o);
            
            
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void DownloadFiles(List<String> archivos)throws IOException{
        cl = conectar(dir,pto);
        
        bos = new BufferedOutputStream(cl.getOutputStream());
        dos = new DataOutputStream(bos);
        
        dos.writeInt(archivos.size());
        for(String archivo: archivos) {
            //System.out.println("name archivo:" + archivo);
            dos.writeUTF(archivo);
        }
        dos.flush();
        
        System.out.println("Files Downloaded");
        dos.close();
        cl.close();
    }
    public void deleteFiles(List<String> archivos) throws IOException{
        cl = conectar(dir,pto);
        
        bos = new BufferedOutputStream(cl.getOutputStream());
        dos = new DataOutputStream(bos);
        
        dos.writeInt(archivos.size());
        for(String archivo: archivos) {
            //System.out.println("name archivo:" + archivo);
            dos.writeUTF(archivo);
        }
        dos.flush();
        
        System.out.println("Files deleted");
        dos.close();
        cl.close();
    }
    
    /*public static void main(String[] args){
        /*JFileChooser jf = new JFileChooser();
        jf.setMultiSelectionEnabled(true);
        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int r = jf.showOpenDialog(null);
           
        if(r == JFileChooser.APPROVE_OPTION){
            File[] f = jf.getSelectedFiles();
            enviar_archivos(f);
            
        }
        
        
    }*/
    
    
    
    public Socket conectar(String dir, int puerto) {
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
