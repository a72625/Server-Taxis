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
public class Condutor{
    private String user;
    private String pass;
    private String matricula;
    private String modelo;
    private Local posicao;
    private ReentrantLock l;
    private Condition cond;

    public Condutor(String u, String p, String mat, String mod) {
        this.user = u;
        this.pass = p;
        this.matricula = mat;
        this.modelo = mod;
    }
    
    public Condutor(Condutor c) {
        this.matricula = c.getMatricula();
        this.modelo = c.getModelo();
        this.l = c.getLock();
        this.cond = c.getLock().newCondition();
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public Local getPosicao() {
        return posicao;
    }
    
    public ReentrantLock getLock(){
        return this.l;
    }
    
    public void setMatricula(String matricula) {
        this.matricula = matricula;
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
    
    
    
    
    
    
}
