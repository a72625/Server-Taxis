/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.taxis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Toshiba
 */
public class User {

    private static Menu menulogreg, menulogop;
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        carregarMenus();
        socket = new Socket("localhost", 2000);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());
        start();
    }

    public static void start() {

        do {
            menulogreg.executa();
            switch (menulogreg.getOpcao()) {
                case 1:
                    out.write(1);
                    out.flush();
                    login();
                    break;
                case 2:
                    out.write(2);
                    out.flush();
                    registar();
                    break;
            }
        } while (menulogreg.getOpcao() != 0);
    }

    public static void login() {

        do {
            menulogop.executa();
            switch (menulogop.getOpcao()) {
                case 1:
                    loginPassageiro();
                    break;
                case 2:
                    loginCondutor();
            }
        } while (menulogop.getOpcao() != 0);
    }

    public static void loginPassageiro(){
        int aux;
        System.out.println("Username: ");
        String email=Input.lerString();
        System.out.println("Password: ");
        String pass=Input.lerString();
        out.write(email+'\n');
        out.flush();
        out.write(pass+'\n');
        out.flush();
        aux=in.read();
        switch (aux){
            case 1:{
                System.out.println("Utilizador inexistente");
                mostraMensagem();
            }
            break;
            case 2:{
                System.out.println("Utilizador ou password incorectos");
                mostraMensagem();
            }
            break;
            case 3:{
                System.out.println("Utilizador já se encontra online");
                mostraMensagem();
            }
            break;
            case 4:{
                execMenuPrincipal();
            }
            break;
        }
        execMenuLogin();
    }
    
    public static void carregarMenus() {
        String[] logreg = {"Login", //login
            "Registar"}; //addUser

        String[] logop = {"Passageiro",
            "Condutor"};

        menulogreg = new Menu(logreg);
        menulogop = new Menu(logop);
    }
}
