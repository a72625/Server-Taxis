/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.taxis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Toshiba
 */
public class Connect {
    
    private Socket socket;
    BufferedReader in, stdin;
    PrintWriter out;
    
    public Connect(Socket socket) throws IOException{
        this.socket = socket;
        in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(),true);
        stdin = new BufferedReader(new InputStreamReader(System.in));
    }
}
