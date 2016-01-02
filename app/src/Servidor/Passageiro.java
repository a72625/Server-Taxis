/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author rcamposinhos
 */
public class Passageiro implements Serializable{
    
    private String user;
    private Local atual;
    private Local destino;
    private Condition cond;
    
    public Passageiro(String u, Rede r) {
        this.user = u;
        this.atual = new Local();
        this.destino = new Local();
        this.cond = r.getLock().newCondition();
    }
    
    public Passageiro(String u, Local a, Local d, Rede r) {
        this.user = u;
        this.atual = new Local(a);
        this.destino = new Local(d);
        this.cond = r.getLock().newCondition();
    }
    
    public Passageiro(String u, Local a, Rede r){
        this.user = u;
        this.atual = new Local(a);
        this.destino = new Local();
        this.cond = r.getLock().newCondition();
    }

    public String getUser() {
        return user;
    }

    public Local getPosAtual() {
        return new Local(atual);
    }

    public Local getDestino() {
        return new Local(destino);
    }

    public Condition getCond() {
        return cond;
    }
    
    public void block() throws InterruptedException{
        this.cond.await();
    }
    
    public void unblock(){
        this.cond.signal();
    }
    
    
    
    



}
