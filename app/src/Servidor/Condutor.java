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
public class Condutor extends Passageiro{

    private String matricula;
    private String modelo;

    public Condutor(String u, String p, String mat, String mod, Local pos) {
        super(u,p,pos);
        this.matricula = mat;
        this.modelo = mod;
    }
    
    public Condutor(Condutor c){
        super(c);
        this.matricula = c.getMatricula();
        this.modelo = c.getModelo();
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
//        return "Condutor{" + "posicao=" + this.getPosicao() + ", matricula="
//                + matricula + ", modelo=" + modelo + '}';
//    }

}
