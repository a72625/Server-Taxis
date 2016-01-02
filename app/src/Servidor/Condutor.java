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
public class Condutor implements Serializable{

    private String matricula;
    private String modelo;
    private Viagem viagem;
    private Local atual;
    private Local destino;
    private ReentrantLock l;
    private Condition cond;
    private String user;

    public Condutor(String u, Rede r, String mat, String mod) {
        this.user = u;
        this.atual = new Local();
        this.destino = new Local();
        this.l = r.getLock();
        this.cond = l.newCondition();
        this.matricula = mat;
        this.modelo = mod;
        this.viagem = null;
    }
    
    public Condutor(String u, Local a, Rede r,String mat, String mod){
        super(u,a,r);
        this.matricula = mat;
        this.modelo = mod;
        this.viagem = null;
    }
    
    public Condutor(String u, Local a, Local d, Rede r, String mat, String mod){
        super(u,a,d,r);
        this.matricula = mat;
        this.modelo = mod;
        this.viagem = null;
    }


    public String getMatricula() {
        return matricula;
    }

    public String getModelo() {
        return modelo;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    
    
 
//    public String toString() {
//        return "Condutor{" + "posicao=" + this.getPosAtual() + ", matricula="
//                + matricula + ", modelo=" + modelo + '}';
//    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

}
