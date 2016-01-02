/*
 */
package Servidor;

import java.io.Serializable;
import static java.lang.Integer.MAX_VALUE;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class Rede implements Serializable{

    private ArrayList<Viagem> viagens;
    private ArrayList<Passageiro> passageirosQueue;
    private ArrayList<Condutor> condutoresQueue;
    private ReentrantLock l;

    //passageiros acedem a condutoresQueue disponiveis mais proximos
    //condutores acedem a passageirosQueue por ordem da fila de espera FIFO
    public Rede() {
        this.viagens = new ArrayList<>();
        this.passageirosQueue = new ArrayList<>();
        this.condutoresQueue = new ArrayList<>();
        l = new ReentrantLock();
    }

    public ReentrantLock getLock() {
        return l;
    }

    public void enqueueDriver(Condutor c) throws InterruptedException {
        l.lock();
        this.condutoresQueue.add(c);
        l.unlock();
        //adormece ate haver passageiros a criar viagens
        c.block();
    }
    
    public boolean dequeueDriver(Condutor c){
        l.lock();
        boolean aux = this.condutoresQueue.remove(c);
        l.unlock();
        return aux;
    }

    public void enqueuePassenger(Passageiro p) {
        l.lock();
        this.passageirosQueue.add(p);
        l.unlock();
    }
    
    public boolean dequeuePassenger(Passageiro p){
        l.lock();
        boolean aux = this.passageirosQueue.remove(p);
        l.unlock();
        return aux;
    }

    public Condutor closestDriver(Passageiro p) throws InterruptedException {
        Condutor aux = null;
        int minDist = MAX_VALUE;
        int auxDist;
        int i = 0;
        int pos = 0;//index do condutor mais perto

        l.lock();

        while(this.condutoresQueue.isEmpty()) {
            //nao ha condutores -> adormece passageiro 
            p.block();
        }

        for(Condutor c : this.condutoresQueue) {
            auxDist = c.getPosAtual().manDist(p.getPosAtual());
            if (auxDist < minDist) {
                pos = i;
                minDist = auxDist;
            }
            i++;
        }

        //obter o mais perto da fila - so remove depois de criar a viagem
        aux = this.condutoresQueue.get(pos);

        l.unlock();

        return aux;
    }

    private Passageiro nextPassageiro(Condutor c) {

        //while(this.passageirosQueue.isEmpty()) await
        return this.passageirosQueue.get(0);

    }

    public Viagem addViagem(Condutor c, Passageiro p) throws myException {
        Viagem v = new Viagem(c, p);
        l.lock();
        this.viagens.add(v);
        l.unlock();
        
        //remover das filas
        if(this.dequeueDriver(c) && this.dequeuePassenger(p)){
            //acorda thread do condutor para continuar a comunicacao dela
            c.unblock(); 
        }
        else{
            throw new myException("");
        }
        
        return v;
    }

    /*
     public ArrayList<Viagem> listViagensByUser(Utilizador u){
     ArrayList<Viagem> aux = new ArrayList<>();
        
     if(u.getClass().getName().equals("Passageiro")){
     for(Viagem v : this.viagens){
     if(v.getPassageiro().getUsername().equals(u.getUsername()))
     aux.add(v.clone());
     }
     }
     else if(u.getClass().getName().equals("Condutor")){
     for(Viagem v : this.viagens){
     if(v.getCondutor().getUsername().equals(u.getUsername()))
     aux.add(v.clone());
     }
     }
     return aux;
        
     }
     */
}
