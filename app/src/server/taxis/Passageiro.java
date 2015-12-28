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
public class Passageiro extends Utilizador{
    private Local destino;
    private ReentrantLock l;
    private Condition cond;
    

    public Passageiro(String u, String p, Local dest, ReentrantLock lock) {
        super(u, p);
        this.destino = dest.clone();
        this.l = lock;
        this.cond = l.newCondition();
        
    }
    
    public Passageiro(Passageiro p){
        super(p);
        this.getDestino();
        this.l = p.getLock();
        this.cond = l.newCondition();
    }

    public void block() throws InterruptedException {
        cond.await();
    }

    public void unblock() {
        cond.signal();
    }
    
    public ReentrantLock getLock(){
        return this.l;
    }

    public Local getDestino() {
        return this.destino;
    }

    @Override
    public String toString() {
        return "Passageiro{" + "nome=" + this.getUsername() + ", posicao=" +
                this.getPosicao() + '}';
    }

    
    
    
    
    
}
