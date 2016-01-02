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

    static BD baseDados;

    public static void main(String[] args) throws IOException {
        baseDados = null;
        String bdFilePath = null;
        int port;
        if (args.length == 0) {
            port = 2000;//2000 por omissao
            baseDados = new BD();//nova BD vazia
            try {
                load("bd");
            } catch (IOException | ClassNotFoundException ex) {
                System.err.println(ex.getMessage() + ".\nErro a ler bd.");
                baseDados.loadSample();
                System.out.println("Atribuída porta 2000."
                        + "\nBase de dados inicializada com user rui, miguel e diogo.");
            }

        } else if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage() + ".\nAtribuída porta 2000.");
                port = 2000;
            }
            baseDados = new BD();//nova BD vazia
            try {
                load("bd");
            } catch (IOException | ClassNotFoundException ex) {
                System.err.println(ex.getMessage() + ".\nErro a ler bd.");
                baseDados.loadSample();
                System.out.println("Atribuída porta 2000."
                        + "\nBase de dados inicializada com user rui, miguel e diogo.");
            }
        } else {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println(e.getMessage() + ".\nAtribuída porta 2000.");
                port = 2000;
            }
            bdFilePath = args[1];
            baseDados = new BD();//nova BD vazia
            try {
                load(bdFilePath);
            } catch (IOException | ClassNotFoundException ex) {
                System.err.println(ex.getMessage() + ".\nErro a ler bd.");
                baseDados.loadSample();
                System.out.println("Atribuída porta 2000."
                        + "\nBase de dados inicializada com user rui, miguel e diogo.");
            }
        }

        try {
            ServerSocket ss = new ServerSocket(port);
            Socket cs = null;
            Rede rede = new Rede();//iniciada vazia

            while ((cs = ss.accept()) != null) {
                ServerThread t = new ServerThread(cs, baseDados, rede);
                t.start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public static void load(String file) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        if (baseDados instanceof BD) {
            baseDados = (BD) in.readObject();
        }
    }

}
