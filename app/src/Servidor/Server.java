/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rcamposinhos
 */
public class Server {
    
    
    public static void main(String[] args) {
        BD baseDados = null;
        String bdFilePath = null;
        int port;
        if(args.length == 0){
            port=2000;//2000 por omissao
//            try {
//                baseDados = load("bd.obj");
//            } catch (Exception ex) {
//                System.err.println(ex.getMessage()+".\nErro a ler bd.");
//            }
            baseDados = new BD();//nova BD vazia
            baseDados.loadSample();
            System.out.println("Atribuída porta 2000."
                    + "\nBase de dados inicializada vazia.");
            
        }
        else if(args.length == 1){
            try{
                port = Integer.parseInt(args[0]);
                baseDados = new BD();//nova BD vazia
                baseDados.loadSample();
                System.out.println("Base de dados inicializada vazia.");
            }
            catch(NumberFormatException e){
                System.err.println(e.getMessage()+".\nAtribuída porta 2000.");
                port=2000;
            } 
        }
        else{
            try{
                port = Integer.parseInt(args[0]);
            }
            catch(NumberFormatException e){
                System.err.println(e.getMessage()+".\nAtribuída porta 2000.");
                port=2000;
            } 
            bdFilePath = args[1];
            try{
                baseDados = load(bdFilePath);//carrega ficheiro
            }
            catch(Exception e){
                System.err.println("Não foi possível abrir o ficheiro."
                        + "\nBase de dados inicializada vazia.");
                baseDados = new BD();//nova BD vazia
            }
        }



        try{
            ServerSocket ss = new ServerSocket(port);
            Socket cs = null;
            Rede rede = new Rede();//iniciada vazia

            while((cs = ss.accept()) != null){
                ServerThread t = new ServerThread(cs, baseDados, rede);
                t.start();
            }
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        
    }
    
    public static BD load(String file) throws Exception{
        BD res=null;
        Object read;
        try{
           ObjectInputStream in = 
                   new ObjectInputStream(new FileInputStream(file));
           read = in.readObject(); 
           if(read instanceof BD){
               res = (BD) read;
               res.setBdFilepath(file);
           }
           in.close();
        }catch(IOException | ClassNotFoundException i){
           i.getMessage();
        }
        return res;
    }
    

    
}
