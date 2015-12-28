/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.taxis;

/** O objectivo desta interface é definir o que uma UI pode precisar para
 * imprimir ao longo da sua execução
 * Dai tentar retornar sempre tipos simples.
 * Esta interface será tambem usada por as camadas de comunicação
 *
 * @author Diogo Duarte
 */
public interface Facade {
    
    public Boolean loginPass(String username, String password)throws UserExistsException;
    
    public Boolean loginCond(String username, String password)throws UserExistsException;
   
    public Boolean addPass(String username, String password)throws UserExistsException;
   
    public Boolean addCond(String username, String password, String mat, String mod)throws UserExistsException;
}
