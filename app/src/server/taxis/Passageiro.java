/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.taxis;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author rcamposinhos
 */
public class Passageiro{
    private String user;
    private String pass;
    private Local posicao;
    private Local destino;
    private ReentrantLock l;
    private Condition cond;
    

    public Passageiro(String u, String p) {
        this.user = u;
        this.pass = p;
        this.posicao = new Local();
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public Local getPosicao() {
        return posicao;
    }
    
    public Local getDestino() {
        return this.destino;
    }
    
    public ReentrantLock getLock(){
        return this.l;
    }
    
    public void block() throws InterruptedException {
        cond.await();
    }

    public void unblock() {
        cond.signal();
    }
    
    @Override
    public String toString() {
        return "Passageiro{" + "nome=" + this.getUser() + ", posicao=" +
                this.getPosicao() + '}';
    }

    
    
    
    
    
}
