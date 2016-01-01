/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author rcamposinhos atencao aos locks de leitura e escrita
 */
public class BD extends HashMap<String,User> implements Serializable{

    private String bdFilepath;
    private ReentrantLock l;

    public BD(){
        super();
        this.bdFilepath = "bd.obj";
        this.l = new ReentrantLock();
    }

    
    public Boolean login(String username, String password){
        boolean flag = false;
        l.lock();
        User u = this.get(username);
        if (u.getUser().equals(username) && u.getPass().equals(password)){
            flag = true;
        }
        l.unlock();
        return flag;
    }
    
    public Boolean registar(String username, String password){
        boolean flag = true;
        l.lock(); 
        this.put(username, new User(username, password));
        if(!this.containsKey(username))
            flag = false;
        else{
            //persistencia de dados
            this.save(this.bdFilepath);
        }
        l.unlock();
        return flag;
    }

    public String getBdFilepath() {
        return bdFilepath;
    }

    public void setBdFilepath(String bdFilepath) {
        this.bdFilepath = bdFilepath;
    }
    
    public void save(String fileName){
         try{
             ObjectOutputStream out = new ObjectOutputStream(
                     new FileOutputStream(fileName));
             out.writeObject(this);
             out.close();
          }catch(IOException e){e.getMessage(); }
    }
    
    public void loadSample(){
        User u1 = new User("rui","123");
        this.put("rui",u1);
        User u2 = new User("miguel","123");
        this.put("miguel",u2);
        User u3 = new User("diogo","123");
        this.put("diogo",u3);
    }
}
