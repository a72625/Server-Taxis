/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

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

    public Condutor getCondutor(String cond) throws myException{
        Condutor c = this.condutores.get(cond);
        if (c == null) {
            throw new myException("Condutor não existe");
        }
        return c;
    }
    
    public void addCondutor(Condutor c) throws myException{
        if(!condutores.containsKey(c.getUser())){
            condutores.put(c.getUser(), c);
        }else throw new myException("Condutor já existe");
    }
}
