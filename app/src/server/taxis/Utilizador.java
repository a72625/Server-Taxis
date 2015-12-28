/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.taxis;

/**
 *
 * @author rcamposinhos
 */
public class Utilizador {
    private String user;
    private String pass;
    private Local posicao;

    public Utilizador(String u, String p) {
        this.user = u;
        this.pass = p;
        this.posicao = new Local(-1, -1);
    }
    
    public Utilizador(Utilizador u){
        this.user = u.getUsername();
        this.pass = u.getPass();
        this.posicao = u.getPosicao();
    }

    public String getUsername() {
        return user;
    }

    public void setUsername(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public Local getPosicao() {
        return new Local(posicao);
    }

    public void setPosicao(Local posicao) {
        this.posicao = new Local(posicao);
    }
    
    public void setPosicao(int x, int y){
        this.posicao = new Local(x, y);
    }
    

    
    
    
    
    
}
