/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rcamposinhos
 */
public class ServerThread extends Thread {

    private String msg;
    private BD bd;
    private Rede rede;
    private Connect cs;

    public ServerThread(Socket s, BD bd, Rede rede) throws IOException {
        this.cs = new Connect(s);
        this.msg = "";
        this.bd = bd;
        this.rede = rede;

    }

    public void run() {
        try {
            while ((msg = cs.readMessage()) != null) {
                dispacher(msg);
            }
        } catch (IOException e) {
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
        switch (codigo) {
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
            case '5':
                //condutor avisa que chegou a partida
                chegouOrigemCC(msg);
                break;
            case '6':
                //servidor avisa passageiro que cond. chegou partida
                chegouOrigemCP(msg);
                break;
            case '7':
                //condutor avisa que chegou ao destino
                chegouDestinoCC(msg);
                break;
            case '8':
                //servidor avisa passageiro que cond. chegou destino
                chegouDestinoCP(msg);
                break;
            case '9':
                //logout
                logout(msg);
                break;
            default:
                //mensagem mal recebida - codigo inexistente
                cs.sendMessage("KO");
                break;
        }
    }

    /**
     * CLIENTE USER INDIFERENCIADO
     * @param msg 
     */
    private void login(String[] msg) {
        //PROTOCOLO:
        //1,user,password
        String user = msg[1];
        String pass = msg[2];

        if (!bd.containsKey(user)) {
            cs.sendMessage("1,user nao existe");
        } else if (bd.isLoggedin(user, pass)) {
            cs.sendMessage("1,user ja esta autenticado");
        } else if (bd.login(user, pass)) {
            cs.sendMessage("1,ok");
        } else {
            cs.sendMessage("1,password errada");
        }
    }

    /**
     * CLIENTE USER INDIFERENCIADO
     * @param msg 
     */
    private void registar(String[] msg) {
        //PROTOCOLO:
        //2,user,password
        String user = msg[1];
        String pass = msg[2];

        if (bd.containsKey(user)) {
            cs.sendMessage("2,user ja existe");
        } else if (bd.registar(user, pass)) {
            cs.sendMessage("2,ok");
        } else {
            cs.sendMessage("KO");
        }
    }
    /**
     * CLIENTE PASSAGEIRO
     * @param msg 
     */
    private void solViagem(String[] msg) {
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
            if(v==null){//erro a criar viagem
                cs.sendMessage("KO");
            }
            else{
                long codViagem = v.getCodigo();
                long espera = v.tempoEspera();
                long chegada = v.tempoViagem();
                float precoEstimado = v.custo();
                //PROTOCOLO envio:
                /*3,condutor atribuido,codigoViagem,matricula,modelo,
                 tempo estimado de chegada à partida,
                 tempo estimado de chegada ao destino,
                 precoEstimado estimado
                 */
                cs.sendMessage("3,condutor atribuido" + "," + v.getCodigo() + 
                        "," +c.getMatricula() + "," + c.getModelo() + "," + 
                        espera + "," + chegada + "," + precoEstimado);

                rede.passageiroWaitChegadaOrigem(p);
                //depois de acordar envia mensagem chegou:
                //6,chegou ao local de partida, codigo da viagem
                cs.sendMessage("6,chegou ao local de partida,"+codViagem);
                //solicita ok para desbloquear condutor
                dispacher(cs.readMessage());
                //adormece ate ao destino
                rede.passageiroWaitChegadaDestino(p);
                //depois de acordar envia mensagem chegou:
                //8,chegou ao local de destino, preco, codigo da viagem
                float preco = v.getPreco();
                cs.sendMessage("8,chegou ao local de destino,"+preco+","+
                                                                    codViagem);
                //solicita ok para desbloquear condutor
                dispacher(cs.readMessage());
            }
            
        } catch (InterruptedException | IOException ex) {
            cs.sendMessage("KO");
        }
    }

    /**
     * CLIENTE CONDUTOR
     * @param msg 
     */
    private void anunDisp(String[] msg){
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
            
            //adormece se o passageiro ainda nao criou viagem
            rede.condutorWaitViagem(c);
            
            //depois de acordar novamente:
            //PROTOCOLO
            /*4,ja foi atribuida uma deslocacao,codigoViagem,
             usernamePassageiro,xAtual,yAtual,xDest,yDest*/      
            long codigo = c.getCodViagem();
            Viagem v = rede.getViagem(codigo);
            String passageiro = v.getPassageiro().getUser();
            Local origem = v.getOrigem();
            Local dest = v.getDestino();

            cs.sendMessage("4,ja foi atribuida uma deslocacao" + "," + codigo + ","
                    + passageiro + "," + origem.getX() + ","
                    + origem.getY() + "," + dest.getX() + ","
                    + dest.getY());

        } catch (InterruptedException ex) {
            cs.sendMessage("KO");
        }

    }

    /**
     * CLIENTE CONDUTOR
     * @param msg 
     */
    private void chegouOrigemCC(String[] msg) {
        //PROTOCOLO:
        //5,chegou ao local de partida, codigo da viagem
        long codViagem = Long.parseLong(msg[2]);
        rede.condutorAcordaPassageiroOrigem(codViagem);
        //adormece e espera resposta do passageiro
        try {
            rede.condutorWaitPassageiroOrigem(codViagem);
            //depois de acordar, envia ok de resposta ao condutor
            cs.sendMessage("5,ok,"+codViagem);
        } catch (InterruptedException ex) {
            cs.sendMessage("KO");  
        } 
    }

    /**
     * CLIENTE PASSAGEIRO
     * @param msg 
     */
    private void chegouOrigemCP(String[] msg) {
        //6,ok,codigo da viagem 
        long codViagem = Long.parseLong(msg[2]);
//        cs.sendMessage("6,ok,"+codViagem);
        rede.passageiroAcordaCondutorOrigem(codViagem);
    }

    /**
     * CLIENTE CONDUTOR
     * @param msg 
     */
    private void chegouDestinoCC(String[] msg) {
        //PROTOCOLO:
        //7,chegou ao local de destino,preco, codigo da viagem
        long codViagem = Long.parseLong(msg[3]);
        rede.condutorAcordaPassageiroDestino(codViagem);
        //adormece e espera resposta do passageiro
        try {
            rede.condutorWaitPassageiroDestino(codViagem);
            //depois de acordar, envia ok de resposta ao condutor
            cs.sendMessage("7,ok,"+codViagem);
        } catch (InterruptedException ex) {
            cs.sendMessage("KO");  
        }
    }


    /**
     * CLIENTE PASSAGEIRO
     * @param msg 
     */
    private void chegouDestinoCP(String[] msg) {
        //PROTOCOLO
        //8,ok,codigo da viagem 
        long codViagem = Long.parseLong(msg[2]);
//        cs.sendMessage("8,ok,"+codViagem);
        rede.passageiroAcordaCondutorDestino(codViagem);
        
    }
    
    /**
     * CLIENTE USER INDIFERENCIADO
     * @param msg 
     */
    private void logout(String[] msg) {
        //PROTOCOLO:
        //9,user
        String user = msg[1];
        if(bd.logout(user)){
            cs.sendMessage("9,ok");
        }
        else{
            cs.sendMessage("KO");
        }
    }
}
