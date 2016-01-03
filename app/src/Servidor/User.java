package Servidor;

import java.io.Serializable;

/**
 *
 * @author rcamposinhos
 */
public class User implements Serializable{
    
    private String user;
    private String pass;
    private boolean login;
    
    public User(String u, String p) {
        this.user = u;
        this.pass = p;
        this.login=false;
    }
    
    public User(){
        this.user = "";
        this.pass = "";
        this.login=false;
    }

    public User(User p) {
        this.user = p.getUser();
        this.pass = p.getPass();
        this.login = p.getLogin();
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public boolean getLogin(){
        return login;
    }
    
    public void login(){
        this.login = true;
    }
    
    public void logout(){
        this.login = false;
    }
    
    @Override
    public String toString() {
        return "User{" + "nome=" + this.getUser() + ", pass="
                + this.getPass()+ '}';
    }

}
