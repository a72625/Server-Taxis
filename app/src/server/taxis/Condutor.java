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
public class Condutor extends Utilizador{
    private String matricula;
    private String modelo;
    private ReentrantLock l;
    private Condition cond;

    public Condutor(String u, String p, String mat, String mod, ReentrantLock l) {
        super(u, p);
        this.matricula = mat;
        this.modelo = mod;
        this.l = l;
        this.cond = l.newCondition();
    }
    
    public Condutor(String u, String p, ReentrantLock l){
        super(u, p);
        this.matricula = "";
        this.modelo = "";
        this.cond = l.newCondition();
    }

    public Condutor(Condutor c) {
        super(c);
        this.matricula = c.getMatricula();
        this.modelo = c.getModelo();
        this.l = c.getLock();
        this.cond = c.getLock().newCondition();
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "Condutor{" + "posicao=" + this.getPosicao() + ", matricula=" +
                matricula + ", modelo=" + modelo + '}';
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
    
    
    
    
}
