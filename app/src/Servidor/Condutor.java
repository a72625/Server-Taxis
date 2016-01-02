/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;


import java.io.Serializable;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author rcamposinhos
 */
public class Condutor extends Passageiro implements Serializable{

    private String matricula;
    private String modelo;

    public Condutor(String u, Rede r, String mat, String mod) {
        super(u,r);
        this.matricula = mat;
        this.modelo = mod;
    }
    
    public Condutor(String u, Local a, Rede r,String mat, String mod){
        super(u,a,r);
        this.matricula = mat;
        this.modelo = mod;
    }
    
    public Condutor(String u, Local a, Local d, Rede r, String mat, String mod){
        super(u,a,d,r);
        this.matricula = mat;
        this.modelo = mod;
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

}
