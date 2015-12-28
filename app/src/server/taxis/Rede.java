/*
 */
package server.taxis;

import static java.lang.Integer.MAX_VALUE;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */

public class Rede{
    private ArrayList<Viagem> viagens;
    private ArrayList<Passageiro> passageirosQueue;
    private ArrayList<Condutor> condutoresQueue;
    private ReentrantLock l;
    
    //passageiros acedem a condutoresQueue disponiveis mais proximos
    //condutores acedem a passageirosQueue por ordem da fila de espera FIFO
    
    

    public Rede(){
        this.viagens = new ArrayList<>();
        this.passageirosQueue = new ArrayList<>();
        this.condutoresQueue = new ArrayList<>();
        l = new ReentrantLock();
    }
    
    public ReentrantLock getLock() {
        return l;
    }
    
    public void newDriver(Condutor c){
        l.lock();
        this.condutoresQueue.add(c);
        l.unlock();
    }
    
    public void newPassenger(Passageiro p){
        l.lock();
        this.passageirosQueue.add(p);
        l.unlock();
    }
    
    
    private Condutor closestDriver(Passageiro p) throws InterruptedException{
        Condutor aux = null;
        int minDist = MAX_VALUE;
        int auxDist;
        int i=0;
        int pos=0;//index do condutor mais perto
        
        l.lock();
        
        while(this.condutoresQueue.isEmpty()){
            p.block();
        }
        
        for(Condutor c : this.condutoresQueue){
            auxDist = c.getPosicao().manDist(p.getPosicao());
            if(auxDist < minDist){
                pos = i;
                minDist = auxDist;
            }
            i++;
        }
        
        //remover o mais perto da fila
        aux = this.condutoresQueue.remove(pos);
        
        l.unlock();
        
        return aux;       
    }
    
    private Passageiro nextPassageiro(Condutor c){
        
        //while(this.passageirosQueue.isEmpty()) await
        
        return this.passageirosQueue.remove(0);
       
    }
    
    //LOCK!
    public void addViagem(Condutor c, Passageiro p){
        
        this.viagens.add(new Viagem(c, p));
    }
    
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
    
    
}
