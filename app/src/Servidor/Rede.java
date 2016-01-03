/*
 */
package Servidor;

import java.io.Serializable;
import static java.lang.Integer.MAX_VALUE;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class Rede implements Serializable{
    private long contViagens; //para atribuir codigos
    private HashMap<Long,Viagem> viagens;
    private ArrayList<Passageiro> passageirosQueue;
    private ArrayList<Condutor> condutoresQueue;
    private ReentrantLock l;

    //passageiros acedem a condutoresQueue disponiveis mais proximos
    //condutores acedem a passageirosQueue por ordem da fila de espera FIFO
    public Rede() {
        this.contViagens = 0;
        this.viagens = new HashMap<>();
        this.passageirosQueue = new ArrayList<>();
        this.condutoresQueue = new ArrayList<>();
        l = new ReentrantLock();
    }

    public ReentrantLock getLock() {
        return l;
    }

    public void enqueueDriver(Condutor c){
        l.lock();
        this.condutoresQueue.add(c);
        l.unlock();
        
//        //acorda passageiro eventualmente adormecido
//        Passageiro p = this.nextPassageiro();
//        if(p!= null)
//            p.signal();
//        
//        
//        //adormece ate haver passageiros
//        while(c.getViagem() == null){
//            c.await();
//        }
    }
    
    public Viagem getViagem(long codigo){
        Viagem v = null;
        l.lock();
        v = viagens.get(codigo);
        l.unlock();
        return v;
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
            p.await();
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

    public Passageiro nextPassageiro(Condutor c) throws InterruptedException {
        l.lock();
        Passageiro p = null;
        //entra se a fila estiver vazia e se nao tiver viagem atribuida
        while(this.passageirosQueue.isEmpty() && c.getCodViagem() == -1){
            c.await();
        }
        if(c.getCodViagem() == -1){//cliente na fila de espera
            p = this.passageirosQueue.get(0);
            //acorda passageiro
            p.signal();
        }
        else{//viagem atribuida
            Viagem v = viagens.get(c.getCodViagem());
            p = v.getPassageiro();
        }
        l.unlock();

        return p;

    }

    /**
     * @brief   adicionar uma nova viagem à rede
     *          criterio: é sempre o passageiro a adicionar
     *          o condutor adormece ate que a viagem esteja definida (cod!=-1)
     */
    public Viagem addViagemPassageiro(Condutor c, Passageiro p) throws myException {
        l.lock();
        Viagem v = null;
        //remover das filas
        if(this.dequeueDriver(c) && this.dequeuePassenger(p)){
            this.contViagens++;
            v = new Viagem(c, p,this.contViagens);
            this.viagens.put(this.contViagens,v);
            long codigo = v.getCodigo();
            //passa ref. da viagem ao condutor e
            //acorda thread do condutor para continuar a comunicacao dela
            c.setViagem(codigo);
            p.setViagem(codigo);
            c.signal(); 
        }
        else{
            throw new myException("");
        }
        l.unlock();
        
        return v;
    }
    
    public void condutorWaitViagem(Condutor c) throws InterruptedException{
        l.lock();
        while(c.getCodViagem() == -1){
            c.await();
        }
        l.unlock();
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
