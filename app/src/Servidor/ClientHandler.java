/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Diogo Duarte
 */
public class ClientHandler implements Runnable {

    private boolean login;
    private Server server;
    private Socket socket;
    private Facade data;
    private Connect c;

    public ClientHandler(Server server, Socket socket, Facade facade) throws IOException {
        this.server = server;
        this.data = facade;
        this.socket = socket;
        this.login = false;
    }

    private void init() throws IOException {
        c = new Connect(socket);
    }

    @Override
    public void run() {
        try {
            init();
        } catch (IOException ex) {
            return;
        }

        int codigo = -27;

        try {
            do {
                codigo = c.readMessage();
                switch (codigo) {
                    case 1:
                        Boolean b = data.login(c.pop(),c.pop());
                        login = b;
                        response(b,"");
                        return; //function for code 1;
                    case 2:// response(data.editUser(cs.popString(), cs.popString()),"");
                        return;//function for code 2;
                    case 3:
                        //response(data.listUser();
                        return;//function for code 3;
                    case 4:// response(data.addObj(cs.popString()),"");
                        break;
                    
                }
            } while (true);
        } catch (myException e) {
            System.err.println(codigo + " - " + e.getMessage());
        }
    }
    
    public void response(Boolean b, String message) throws myException{
        c.sendOK(b, message);
    }
    
}
