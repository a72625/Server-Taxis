/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

/**
 *
 * @author rcamposinhos atencao aos locks de leitura e escrita
 */
public class BD implements Facade {

    private Passageiros passageiros;

    public BD(){
        this.passageiros = new Passageiros();
    }

    /**
     *
     * @param username
     * @param password
     * @return 
     * @throws Servidor.myException
     */
    @Override
    public Boolean login(String username, String password) throws myException{
        try (Passageiro p = this.passageiros.getPassageiro(username)) {
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
