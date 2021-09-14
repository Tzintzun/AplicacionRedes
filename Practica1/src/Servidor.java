
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 */
public class Servidor {
    
    public static int pto = 8000;
    private Socket cl;
    
    
    public static Informacion recibirInfo(ServerSocket s){
       try{
           Socket info = s.accept();
           ObjectInputStream dis = new ObjectInputStream(info.getInputStream());
           Object aux;
           String[] nombres = null;
           long[] tam = null;
           boolean[] directorio= null;
           aux = dis.readObject();
           if(aux instanceof String[]) nombres = (String[]) aux;
           aux = dis.readObject();
           if(aux instanceof long[]) tam = (long[])aux;
           aux = dis.readObject();
           if(aux instanceof boolean[]) directorio = (boolean[])aux;
           
           dis.close();
           info.close();
           return new Informacion(nombres,tam,directorio);
       }catch(Exception e){
           e.printStackTrace();
           return null;
       }
        
    }
    
    public static void recibirArchivo(ServerSocket s2, String archivo,String ruta,long tam){
        try{
            Socket datos = s2.accept();
            System.out.println("Cliente conectado desde "+datos.getInetAddress()+":"+datos.getPort());
            
            DataInputStream dis = new DataInputStream(datos.getInputStream());
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(ruta + archivo));
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
    
    
    public static void recibir_archivos(ServerSocket sc, ServerSocket s2, String ruta){
        
        Informacion inf = recibirInfo(sc);
        System.out.println("Recibiendo: "+inf.nombres.length + " archivos");
        for (int i = 0; i < inf.nombres.length; i++) {
            System.out.println("\t"+i+". "+ inf.nombres[i] +" "+inf.tamaños[i]+"bytes" );
        }
        for (int i = 0; i < inf.nombres.length; i++) {
            if(inf.directorio[i]){
                File f = new File(ruta+"\\"+inf.nombres[i]+"\\");
                f.mkdirs();
                f.setWritable(true);
                f.setReadable(true);
                recibir_archivos(sc,s2,ruta+"\\"+inf.nombres[i]+"\\");
            }else{
                recibirArchivo(s2,inf.nombres[i],ruta,inf.tamaños[i]);
            }
        }
    }
    public static boolean enviarArchivo(File f, Socket cl2){
        try{
            
            DataOutputStream dos = new DataOutputStream(cl2.getOutputStream());
            DataInputStream dis = new DataInputStream(new FileInputStream(f.getAbsolutePath()));
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
    public static void enviar_archivo(ServerSocket s,ServerSocket s2,String directorio){
        Compresor c = new Compresor();
        Date fecha = new Date();
        String fch = fecha.toString().replace(':','-');
        
        File f = c.comprimir(directorio, fch+".zip");
        
        System.out.println("Comprimiendo Archivo...");
        try {
           
            Socket sc = s.accept();
            DataOutputStream dos = new DataOutputStream(sc.getOutputStream());
            dos.writeUTF(f.getName());
            dos.flush();
            dos.writeLong(f.length());
            System.out.println("Enviando datos del archivo");
            dos.close();
            System.out.println(f.getAbsolutePath());
            enviarArchivo(f,s2.accept());
            f.delete();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public static void getFilesFromServer(ServerSocket s, String path){
        String currentRoute= new File("").getAbsolutePath();
        String serverRoute= currentRoute + "\\MiUnidad\\" + path;
        File directoryServer= new File(serverRoute);
        
        File[] listFilesInServer= directoryServer.listFiles(); 
        try {
            Socket sc = s.accept();
            
            DataOutputStream dos = new DataOutputStream(sc.getOutputStream());
            
            dos.writeInt(listFilesInServer.length);
            dos.flush(); 
            
            //String typeOfFile;
            for (int i = 0; i < listFilesInServer.length; i++) {
                /*if(listFilesInServer[i].isDirectory())
                    typeOfFile = "Directory: ";
                else
                    typeOfFile = "File: ";
                dos.writeUTF(typeOfFile + listFilesInServer[i].getName());*/
                if(listFilesInServer[i].isDirectory())
                    dos.writeUTF( listFilesInServer[i].getName()+"\\");
                else
                    dos.writeUTF(listFilesInServer[i].getName());
                dos.flush();
            }
            System.out.println("Sending files of server");
            dos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public static void deleteFiles(String nombre, String dir) { 
        String filename = dir + nombre;
        System.out.println("name file to delete: " + filename);
        File file = new File(filename);
        if(file.exists()) {
            if(file.isDirectory()){
                deleteDirectories(file.listFiles());
            }
            file.delete();
        }
    }
    public static void deleteDirectories(File[] archivos){
        
        for(File f : archivos){
            if(f.isDirectory()){
                deleteDirectories(f.listFiles());
                f.delete();
            }else{
                f.delete();
            }
        }
    }
    public static void filesSelectedToDelete(ServerSocket s){
        try{
            Socket sc = s.accept();
            BufferedInputStream bis = new BufferedInputStream(sc.getInputStream());
            DataInputStream dis = new DataInputStream(bis);
            
            int totFilesToDelete = dis.readInt();
            System.out.println("Total to delete: " + totFilesToDelete);

            String currentRoute= new File("").getAbsolutePath();
            String serverRoute= currentRoute + "\\MiUnidad\\";

            for(int i = 0; i < totFilesToDelete; i++) {
                deleteFiles(dis.readUTF(), serverRoute);
            }
            
            dis.close();
        }catch(Exception e){
            
        }
    }
    
    
    public static void main(String[] args){
        try{
            ServerSocket s = new ServerSocket(pto);
            ServerSocket s2= new ServerSocket(pto+1);
            s2.setReuseAddress(true);
            s.setReuseAddress(true);
            
            System.out.println("Servidor iniciado esperando por archivos..");
            
            File f = new File(".\\");
            String ruta = f.getAbsolutePath();
            ruta = ruta + "\\MiUnidad\\";
            File f2 = new File(ruta);
            f2.mkdirs();
            f2.setWritable(true);
            f2.setReadable(true);
            
            for(;;){
                Socket sc = s.accept();
                System.out.println("Cliente conectado desde:"+sc.getInetAddress()+":"+sc.getPort());
        
                BufferedInputStream bis = new BufferedInputStream(sc.getInputStream());
                DataInputStream dis = new DataInputStream(bis);
                
                //BufferedOutputStream bos = new BufferedOutputStream(sc.getOutputStream());
                //DataOutputStream dos = new DataOutputStream(bos);
                
                int tipo = -1;
                tipo = dis.readInt(); 
                switch(tipo){
                        //case 0 = oprimir boton sendFiles
                    case 0: 
                        System.out.println("Server: case send files to server");
                        recibir_archivos(s,s2,ruta);
                        System.out.println("\n******************************");
                        System.out.println("**** OPERACION COMPLETADA ****");
                        System.out.println("******************************\n");
                        break;
                    case 1:
                        System.out.println("Server: case send files to client");
                        enviar_archivo(s,s2,"MiUnidad");
                     
                        
                        
                        break;
                    case 2:     //muestra la lista de files en el SEREVER
                        String path = dis.readUTF();
                        System.out.println("Server: Mostrar lista ar archivos de la carpeta" + path);
                        getFilesFromServer(s, path);
                        break;
                    case 3:     //this case is to delete files
                        System.out.println("Server: delete files");   
                        filesSelectedToDelete(s);
                        break;
                    case 4:
                        break;
                }
                
                dis.close();
                sc.close();
                
            } //end For
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
}
