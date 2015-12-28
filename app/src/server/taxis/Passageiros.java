/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.taxis;

import java.util.HashMap;

/**
 *
 * @author Toshiba
 */
public class Passageiros {
    
    private HashMap<String, Passageiro> passageiros;

    public Passageiros() {
        this.passageiros = new HashMap<>();
    }

    public Passageiro getPassageiro(String user) throws UserExistsException{
        Passageiro p = this.passageiros.get(user);
        return p;
    }
    
    public void addPassageiro(Passageiro p) throws UserExistsException{
        if(!passageiros.containsKey(p.getUser())){
            passageiros.put(p.getUser(), p);
        }else throw new UserExistsException();
    }
}
