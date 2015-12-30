/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author rcamposinhos
 */
public class Passageiro implements autoClose{
    
    private String user;
    private String pass;
    private Local posicao;
    private Local destino;
    private Rede r;
    private Condition cond;
    
    public Passageiro(String u, String p) {
        this.user = u;
        this.pass = p;
        this.posicao = new Local();
        this.destino = new Local();
        this.r = null;
        this.cond = null;
    }

    public Passageiro(Passageiro p) {
        this.user = p.getUser();
        this.pass = p.getPass();
        this.posicao = p.getPosicao();
        this.destino = p.getDestino();
        //this.lock = p.getLock();
        //this.cond
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public Local getPosicao() {
        return new Local(posicao);
    }

    public Local getDestino() {
        return new Local(destino);
    }
    @Override
    public String toString() {
        return "Passageiro{" + "nome=" + this.getUser() + ", posicao="
                + this.getPosicao() + '}';
    }

    @Override
    public void close() throws myException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
