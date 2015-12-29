/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.taxis;

import java.io.IOException;

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
