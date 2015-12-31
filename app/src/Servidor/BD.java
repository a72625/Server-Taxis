/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.util.HashMap;

/**
 *
 * @author rcamposinhos atencao aos locks de leitura e escrita
 */
public class BD{

    private HashMap<String,User> users;

    public BD(){
        this.users = new HashMap<>();
    }

    /**
     *
     * @param username
     * @param password
     * @return 
     * @throws Servidor.myException
     */
    
    public Boolean login(String username, String password) throws myException{
        try(Passageiro p = this.passageiros.getPassageiro(username)) {
            if (!p.getPass().equals(password)) {
                throw new myException("login ou password inválidos");
            }
            return true;
        }
    }
    
    @Override
    public Boolean loginCondutor(String username, String password) throws myException {
        return true;
    }

    @Override
    public Boolean addPassageiro(String username, String password) throws myException {
        return true;
    }

    @Override
    public Boolean addCondutor(String username, String password, String mat, String mod) throws myException {
        return true;
    }

    @Override
    public Boolean passageiroExiste(String usern) throws myException {
        return true;
    }

    @Override
    public Boolean condutorExiste(String usern) throws myException {
        return true;
    }
}
