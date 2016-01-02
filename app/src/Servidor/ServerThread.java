/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.IOException;
import java.net.*;

/**
 *
 * @author rcamposinhos
 */
public class ServerThread extends Thread{
    private String msg;
    private BD bd;
    private Rede rede;
    private Connect cs;
    
    public ServerThread(Socket s, BD bd, Rede rede) throws IOException{
        this.cs = new Connect(s);
        this.msg = "";
        this.bd = bd;
        this.rede = rede;
        
    }
    
    public void run(){
        try{
            while((msg = cs.readMessage()) != null){ 
                dispacher(msg);
            }
        }
         catch(IOException e){
            System.err.println(e.toString());
        }
    
    }
    
    private String[] mySplit(String mensagem) {
        String[] str;
        str = mensagem.split(",");
        return str;
    }
    
    public void dispacher(String mensagem) throws IOException{
        //partir mensagem em campos:
        String[] msg = mySplit(mensagem);
        //ver codigo da mensagem:
        char codigo = msg[0].charAt(0);
        //executar a mensagem com os campos:
        switch(codigo){
            case '1':
                //login
                login(msg);
                break;
            case '2':
                //register
                registar(msg);
                break;
            case '3':
                //
                break;
            case '4':
                //
                break;
            default:
                //mensagem mal recebida - codigo inexistente
                break;
        }
    }
    
    public void login(String[] msg){
        String user = msg[1];
        String pass = msg[2];
        
        if(!bd.containsKey(user)){
            cs.sendMessage("1,user nao existe");
        }
        else if(bd.login(user,pass)){
            cs.sendMessage("1,ok");
        }
        else{
            cs.sendMessage("1,password errada");
        }   
    }
    
    public void registar(String[] msg){
        String user = msg[1];
        String pass = msg[2];
        
        if(bd.containsKey(user)){
            cs.sendMessage("2,user ja existe");
        }
        else if(bd.registar(user, pass)){
            cs.sendMessage("2,ok");
        }
        else{
            cs.sendMessage("2,impossivel registar");
        }
    }
}
