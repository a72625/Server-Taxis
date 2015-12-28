/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.taxis;

import static java.lang.Integer.MAX_VALUE;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author rcamposinhos
 * atencao aos locks de leitura e escrita
 */

public class BD{
    private HashMap<String, Utilizador> utilizadores;

    public BD(){
        this.utilizadores = new HashMap<>();
    }
    

    public void addPassageiro(Passageiro p) throws UserExistsException{
        if(this.utilizadores.containsKey(p.getUsername())){
            throw new UserExistsException(p.getUsername());
        }
        else{
            this.utilizadores.put(p.getUsername(), new Passageiro(p));
            //TODO
            saveData();
        }
            
    }
    

    public void addCondutor(Condutor c) throws UserExistsException{
        if(this.utilizadores.containsKey(c.getUsername())){
            throw new UserExistsException(c.getUsername());
        }
        else{
            this.utilizadores.put(c.getUsername(), new Condutor(c));
            //TODO
            saveData();
        }
    }
    

    
    public Utilizador getUser(String n){
        Utilizador aux = null;
        aux = utilizadores.get(n);
        return aux; 
    }
    
    public boolean loginCheck(Utilizador u){
        Utilizador aux;
        if(utilizadores.containsKey(u.getUsername())){
            aux = utilizadores.get(u.getUsername()); 
            return (aux.getPass().equals(u.getPass()));
        }
        else{
            return false;
        }   
    }
    
    
    
    
    
    
    
    private void loadData(){
        
    }
    
    private void saveData(){
        
    }
    
}
