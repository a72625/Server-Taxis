/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.IOException;
import server.taxis.Interface;
import server.taxis.myException;

/**
 *
 * @author Diogo Duarte
 */
public class UserMain{
    
    public static void main(String[] args) throws IOException, myException{
        User u = new User();
        Interface ui = new Interface(u);
        ui.start();
    }
}
