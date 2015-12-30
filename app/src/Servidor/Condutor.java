/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;


import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author rcamposinhos
 */
public class Condutor extends Passageiro implements autoClose{
    private String matricula;
    private String modelo;

    public Condutor(String u, String p, String mat, String mod,Local pos) {
        super(u,p);
        this.matricula = mat;
        this.modelo = mod;
    }

    public Condutor(Condutor c) {
        this.user = c.getUser();
        this.pass = c.getPass();
        this.matricula = c.getMatricula();
        this.modelo = c.getModelo();
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
        return this.lock;
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

    @Override
    public void close() throws myException {
        lock.unlock();
    }
}
