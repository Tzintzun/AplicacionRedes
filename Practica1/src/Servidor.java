
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
    
    
    public static Informacion recibirInfo(Socket s){
       try{
           //Socket info = s.accept();
           ObjectInputStream dis = new ObjectInputStream(s.getInputStream());
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
           s.close();
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
    
    
    public static void recibir_archivos(Socket sc, ServerSocket s2, String ruta){
        
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
                
                int tipo = dis.readInt();
                switch(tipo){
                        //case 0 = oprimir boton sendFiles
                    case 0: System.out.println("Server: case send files to server");
                        recibir_archivos(sc,s2,ruta);
                        System.out.println("\n******************************");
                        System.out.println("**** OPERACION COMPLETADA ****");
                        System.out.println("******************************\n");
                        break;
                    case 1:
                        break;
                }
                
            } //end For
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
}
