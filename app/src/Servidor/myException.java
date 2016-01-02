/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.Serializable;

/**
 *
 * @author Diogo Duarte
 */
public class myException extends Exception implements Serializable{

    protected String myMessage;

    public myException(String myMessage) {
        super(myMessage);
        this.myMessage = myMessage;
    }

    @Override
    public String getMessage() {
        return myMessage;
    }

    public String getMyMessage() {
        return myMessage;
    }
}
