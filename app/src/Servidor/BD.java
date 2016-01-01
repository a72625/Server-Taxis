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
import java.util.HashMap;

/**
 *
 * @author rcamposinhos atencao aos locks de leitura e escrita
 */
public class BD extends HashMap<String,User>{

    private HashMap<String,User> users;
    private String bdFilepath;

    public BD(){
        this.users = new HashMap<>();
    }
    
    public Boolean login(String username, String password){
        boolean flag = false;
        User u = this.users.get(username);
        if (!u.getPass().equals(password)){
            flag = true;
        }
        return flag;
    }

    public String getBdFilepath() {
        return bdFilepath;
    }

    public void setBdFilepath(String bdFilepath) {
        this.bdFilepath = bdFilepath;
    }
    
    public void load(String file) throws Exception{
        Object read;
        try{
           ObjectInputStream in = 
                   new ObjectInputStream(new FileInputStream(file));
           read = in.readObject(); 
           if(read instanceof BD){
               users = (BD) read;
           }
           this.setBdFilepath(file);
           in.close();
        }catch(IOException | ClassNotFoundException i){
           i.getMessage();
        }
    }
    
    public void save(){
         try{
             ObjectOutputStream out = new ObjectOutputStream(
                     new FileOutputStream(bdFilepath));
             out.writeObject(users);
             out.close();
          }catch(IOException e){e.getMessage(); }
    }
    
    public void loadSample(){
        User u1 = new User("rui","123");
        users.put("rui",u1);
        User u2 = new User("miguel","123");
        users.put("miguel",u2);
        User u3 = new User("diogo","123");
        users.put("diogo",u3);
    }
}
