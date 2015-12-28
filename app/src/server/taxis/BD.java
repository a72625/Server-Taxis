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

    @Override
    public Boolean loginPass(String username, String password) throws UserExistsException {
        Passageiro p = this.passageiros.getPassageiro(username);
        if (p == null) {
            if (!p.getPass().equals(password)) {
                throw new UserExistsException();
            }
            return true;
        }
        throw new UserExistsException();
    }

    @Override
    public Boolean loginCond(String username, String password) throws UserExistsException {
        Condutor c = this.condutores.getCondutor(username);
        if (c == null) {
            if (!c.getPass().equals(password)) {
                throw new UserExistsException();
            }
            return true;
        }
        throw new UserExistsException();
    }

    @Override
    public Boolean addPass(String username, String password) throws UserExistsException {
        Passageiro p = new Passageiro(username, password);
        passageiros.addPassageiro(p);
        return true;
    }

    @Override
    public Boolean addCond(String username, String password, String mat, String mod) throws UserExistsException {
        Condutor c = new Condutor(username, password, mat, mod);
        condutores.addCondutor(c);
        return true;
    }

}
