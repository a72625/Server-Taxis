/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Servidor.Passageiro;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import sun.util.calendar.Gregorian;

/**
 *
 * @author rcamposinhos
 */
public class Viagem {

    private static final float CUSTOUNIT = (float) 0.005; //custo por metro de percurso
    private static final float BANDEIRADA = 2;// custo por inicio do servico
    private static final int VELOCIDADE = 8;//vel. media 8m/s, 30km/h
    private Condutor c;
    private Passageiro p;
    private Local destino;
    private Local origem;
    private GregorianCalendar data;

    public Viagem(Condutor c, Passageiro p) {
        this.c = c;
        this.p = p;
        this.destino = new Local(p.getDestino());
        this.origem = new Local(p.getPosicao());
        this.data = new GregorianCalendar();
    }

    public Viagem(Viagem v) {
        this.c = v.getCondutor();
        this.p = v.getPassageiro();
        this.destino = v.getDestino();
        this.origem = v.getOrigem();
        this.data = v.getData();
    }

    public float custo() {
        return (BANDEIRADA + CUSTOUNIT * this.origem.manDist(this.destino));
    }

    //tempo em segundos
    public long tempoEspera() {
        return (long) (this.c.getPosicao().manDist(this.origem) / VELOCIDADE);
    }

    //tempo em segundos
    public long tempoViagem() {
        return (long) (this.origem.manDist(this.destino) / VELOCIDADE);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String date = sdf.format(data.getTime());
        return "Viagem{" + "condutor=" + c.getUser() + ", passageiro="
                + p.getUser() + ", destino=" + destino + ", origem=" + origem
                + ", data=" + date + '}';
    }

    public Condutor getCondutor() {
        return new Condutor(c);
    }

    public void setCondutor(Condutor c) {
        this.c = new Condutor(c);
    }

    public Passageiro getPassageiro() {
        return new Passageiro(p);
    }

    public void setPassageiro(Passageiro p) {
        this.p = new Passageiro(p);
    }

    public Local getDestino() {
        return new Local(destino);
    }

    public void setDestino(Local destino) {
        this.destino = new Local(destino);
    }

    public Local getOrigem() {
        return new Local(origem);
    }

    public void setOrigem(Local origem) {
        this.origem = origem;
    }

    public GregorianCalendar getData() {
        return (GregorianCalendar) data.clone();
    }

    public void setDataToNow() {
        this.data = new GregorianCalendar();
    }

    public Viagem clone() {
        return new Viagem(this);
    }

}
