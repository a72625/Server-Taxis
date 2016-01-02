/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Toshiba
 */
public class Passageiros implements Serializable{

    private HashMap<String, Passageiro> passageiros;

    public Passageiros() {
        this.passageiros = new HashMap<>();
    }

    public Passageiro getPassageiro(String user) throws myException {
        Passageiro p = this.passageiros.get(user);
        if (p == null) {
            throw new myException("Passageiro não existe");
        }
        return p;
    }
    
    public void addPassageiro(Passageiro p) throws myException{
        passageiros.put(p.getUser(), p);
    }
    
    public boolean passageiroExiste(String username) throws myException{
        return passageiros.containsKey(username);
    }
}
