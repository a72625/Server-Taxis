/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.taxis;

/**
 *
 * @author rcamposinhos atencao aos locks de leitura e escrita
 */
public class BD implements Facade {

    private Condutores condutores;
    private Passageiros passageiros;

    public BD() {
        this.condutores = new Condutores();
        this.passageiros = new Passageiros();
    }

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws myException
     */
    @Override
    public Boolean loginPassageiro(String username, String password) throws myException {
        try (Passageiro p = this.passageiros.getPassageiro(username)) {
            if (!p.getPass().equals(password)) {
                throw new myException("login ou password inválidos");
            }
            return true;
        }
    }

    @Override
    public Boolean loginCondutor(String username, String password) throws myException {
        try (Condutor c = this.condutores.getCondutor(username)) {
            if (!c.getPass().equals(password)) {
                throw new myException("login ou password inválidos");
            }
            return true;
        }
    }

    @Override
    public Boolean addPassageiro(String username, String password) throws myException {
        Passageiro p = new Passageiro(username, password);
        passageiros.addPassageiro(p);
        return true;
    }

    @Override
    public Boolean addCondutor(String username, String password, String mat, String mod) throws myException {
        Condutor c = new Condutor(username, password, mat, mod);
        condutores.addCondutor(c);
        return true;
    }
}
