/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Diogo Duarte
 */
public class Connect {
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String[] atributos;
    private int atrIndex;
    
    public Connect(Socket socket) throws IOException{
        this.socket = socket;
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        in = new BufferedReader(isr);

        OutputStreamWriter osr = new OutputStreamWriter(socket.getOutputStream());
        out = new PrintWriter(osr);
    }

    public int readMessage() throws myException {
        String[] str;
        try {
            String aux = in.readLine();

            if (aux == null) {
                socket.shutdownOutput();
                socket.shutdownInput();
                return -1;
            }

            str = aux.split(",");

            int codigo, size;

            codigo = Integer.parseInt(str[0]);
            size = Integer.parseInt(str[1]);
            atributos = new String[size];

            for (int i = 0; i < size && i + 2 < str.length; i++) {
                atributos[i] = str[i + 2];
            }
            atrIndex = 0;
            return codigo;

        } catch (IOException | NumberFormatException ex) {
            throw new myException("Erro a ler");
        }
    }
}
