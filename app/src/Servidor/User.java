/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.Serializable;

/**
 *
 * @author rcamposinhos
 */
public class User implements Serializable{
    
    private String user;
    private String pass;
    
    public User(String u, String p) {
        this.user = u;
        this.pass = p;
    }

    public User(User p) {
        this.user = p.getUser();
        this.pass = p.getPass();
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    @Override
    public String toString() {
        return "User{" + "nome=" + this.getUser() + ", pass="
                + this.getPass()+ '}';
    }

}
