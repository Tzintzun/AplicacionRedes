/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author david
 */
public class Informacion {
    public String[] nombres;
    public long[] tamaños;
    public boolean[] directorio;
    
    public Informacion(String []n, long[] t, boolean[] d){
        nombres = n;
        tamaños = t;
        directorio = d;
    }
}
