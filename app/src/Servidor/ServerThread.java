/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                //login de utilizador
                login(msg);
                break;
            case '2':
                //registo de novo utilizador
                registar(msg);
                break;
            case '3':
                //passageiro solicita viagem
                solViagem(msg);
                break;
            case '4':
                //condutor anuncia disponibilidade
                anunDisp(msg);
                break;
            default:
                //mensagem mal recebida - codigo inexistente
                break;
        }
    }
    
    public void login(String[] msg){
        //PROTOCOLO:
        //1,user,password
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
        //PROTOCOLO:
        //2,user,password
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
    
    public void solViagem(String[] msg){
        //PROTOCOLO:
        //3,user,xAtual,yAtual,xDest,yDest
        String user = msg[1];
        Local atual = new Local(Integer.parseInt(msg[2]), 
                                    Integer.parseInt(msg[3]));
        Local destino = new Local(Integer.parseInt(msg[4]), 
                                    Integer.parseInt(msg[5]));
        Passageiro p = new Passageiro(user, atual, destino, rede);
        
        //adicionar novo passageiro a fila de espera
        rede.enqueuePassenger(p);
        try {
            Condutor c = rede.closestDriver(p);
            Viagem v = rede.addViagem(c, p);
            long espera = v.tempoEspera();
            long chegada = v.tempoViagem();
            float preco = v.custo();
            //PROTOCOLO envio:
            /*3,condutor atribuido,codigoViagem,matricula,modelo,
                    tempo estimado de chegada à partida,
                    tempo estimado de chegada ao destino,
                    preco estimado
            */
            cs.sendMessage("3,condutor atribuido"+","+v.getCodigo()+","
                    +c.getMatricula()+","+c.getModelo()+","+espera+","
                    +chegada+","+ preco);
        } catch (InterruptedException | myException ex) {
            cs.sendMessage("3,nao foi possivel estabelecer viagem");
        }        
    }
    
    public void anunDisp(String[] msg){
        //PROTOCOLO:
        //4,user,matricula,modelo,xAtual,yAtual
        String user = msg[1];
        String mat = msg[2];
        String mod = msg[3];
        Local atual = new Local(Integer.parseInt(msg[4]), 
                                    Integer.parseInt(msg[5]));
        Condutor c = new Condutor(user, atual, rede, mat, mod);
        
        rede.enqueueDriver(c);
        try {
            Passageiro p = rede.nextPassageiro(c);
            //depois de acordar:
            //PROTOCOLO
            /*4,ja foi atribuida uma deslocacao,codigoViagem,
                        usernamePassageiro,xAtual,yAtual,xDest,yDest*/
            //bloqueia para o passageiro criar viagem
            rede.getLock().lock();
            c.await();
            rede.getLock().unlock();
            //depois do passageiro desbloquear o condutor
            Viagem v = c.getViagem();
            long codigo = v.getCodigo();
            String passageiro = v.getPassageiro().getUser();
            Local origem = v.getOrigem();
            Local dest = v.getDestino();

            cs.sendMessage("4,ja foi atribuida uma deslocacao"+","+codigo+","+
                                passageiro + "," + origem.getX() + "," +  
                                origem.getY() + "," + dest.getX() + "," +
                                dest.getY());
        
        } catch (InterruptedException ex) {
            cs.sendMessage("4,nao foi possivel estabelecer viagem");
        }
        
        
        
        
    }
}
