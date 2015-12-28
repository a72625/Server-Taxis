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
public class Condutores {

    private HashMap<String, Condutor> condutores;

    public Condutores() {
        this.condutores = new HashMap<>();
    }

    public Condutor getCondutor(String cond) throws UserExistsException{
        Condutor c = this.condutores.get(cond);
        if (c == null) {
            throw new UserExistsException();
        }
        return c;
    }
    
    public void addCondutor(Condutor c){
        condutores.put(c.getUser(), c);
    }
}
