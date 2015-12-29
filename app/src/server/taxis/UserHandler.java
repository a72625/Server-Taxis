/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.taxis;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Toshiba
 */
public class UserHandler implements Runnable {

    private Socket socket;
    private Connect c;
    private Facade data;

    public UserHandler(Socket socket, Facade facade) throws IOException {
        this.socket = socket;
        this.data = facade;
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
        int op;
        do{
           switch(op=c.in.read()) {
                case 1:data.();
                    break;
                    
                case 2:registar();
                    break;
           }
        }while(op!=0);
    }
}
