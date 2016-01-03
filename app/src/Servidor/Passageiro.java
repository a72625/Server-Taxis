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
    private ReentrantLock l;
    private Condition cond;
    private long codigoViagem;
    
    public Passageiro(String u, Rede r) {
        this.user = u;
        this.atual = new Local();
        this.destino = new Local();
        this.l = r.getLock();
        this.cond = l.newCondition();
        this.codigoViagem = -1;
    }
    
    public Passageiro(String u, Local a, Local d, Rede r) {
        this.user = u;
        this.atual = new Local(a);
        this.destino = new Local(d);
        this.l = r.getLock();
        this.cond = l.newCondition();
        this.codigoViagem = -1;
    }
    
    public Passageiro(String u, Local a, Rede r){
        this.user = u;
        this.atual = new Local(a);
        this.destino = new Local();
        this.l = r.getLock();
        this.cond = l.newCondition();
        this.codigoViagem = -1;
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
    
    public void await() throws InterruptedException{
        this.l.lock();
        this.cond.await();
        this.l.unlock();
    }
    
    public void signal(){
        this.l.lock();
        this.cond.signal();
        this.l.unlock();
    }
    
    public long getCodViagem() {
        return codigoViagem;
    }

    public void setViagem(long viagem) {
        this.codigoViagem = viagem;
    }
}
