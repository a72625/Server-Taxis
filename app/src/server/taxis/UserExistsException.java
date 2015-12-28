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
public class UserExistsException extends Exception {
    public UserExistsException(){
        super();
    }
    
    public UserExistsException(String name) {
        super(name);
    }
    
}
