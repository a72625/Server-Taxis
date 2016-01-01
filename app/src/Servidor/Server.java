/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;
import java.io.*;
import java.net.*;

/**
 *
 * @author rcamposinhos
 */
public class Server {
    
    private static BD baseDados = null;
    private static String bdFilePath = null;
    
    public static void main(String[] args) {
        int port;
        if(args.length == 0){
            port=2000;//2000 por omissao
            Server.baseDados = new BD();//nova BD vazia
            Server.baseDados.loadSample();
            System.out.println("Atribuída porta 2000."
                    + "\nBase de dados inicializada vazia.");
            
        }
        else if(args.length == 1){
            try{
                port = Integer.parseInt(args[0]);
                Server.baseDados = new BD();//nova BD vazia
                Server.baseDados.loadSample();
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
                Server.baseDados.load(bdFilePath);//carrega ficheiro
            }
            catch(Exception e){
                System.err.println("Não foi possível abrir o ficheiro."
                        + "\nBase de dados inicializada vazia.");
                Server.baseDados = new BD();//nova BD vazia
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
    

    
}
