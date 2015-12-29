/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.taxis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ruioliveiras
 */
public class Server implements Runnable {

    private ServerSocket ss;
    private Map<Integer, UserHandler> requests;
    private Facade facade;

    public Server() throws IOException {
        ss = new ServerSocket(1050);
        facade = new BD();
    }

    public void start() throws IOException {
        while (true) {
            Socket cn = ss.accept();
            UserHandler rn = new UserHandler(Server.this, cn, facade);
            Thread t = new Thread(rn);
            t.start();
        }

    }

    public Facade getFacade() {
        return facade;
    }

    public static void main(String[] args) throws IOException, myException {
        final Server rs = new Server();
        Thread t = new Thread(rs);
        t.start();
        Interface ui = new Interface(rs.getFacade());
        ui.start();
    }

    @Override
    public void run() {
        try {
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
