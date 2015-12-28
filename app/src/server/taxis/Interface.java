package server.taxis;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Scanner;

/**
 *
 * @author Diogo Duarte
 *
 */
public class Interface {///implements Facede{

    public Menu menulogreg,menulogop;
    private String user;
    private Facade f;

    public Interface(Facade f) {
        this.f = f;
    }

    public void start() {
        carregarMenus();

        do {
            menulogreg.executa();
            switch (menulogreg.getOpcao()) {
                case 1:
                    login();
                    break;
                case 2:
                    registar();
                    break;
            }
        } while (menulogreg.getOpcao() != 0);
    }
    
    protected void login() {
        do {
            menulogop.executa();
            switch (menulogop.getOpcao()) {
                case 1:
                    loginPass();
                    break;
                case 2:
                    loginCond();
                    break;
            }
        } while (menulogreg.getOpcao() != 0);
    }

    protected void loginPass() {
        Scanner is = new Scanner(System.in);
        String pass;
        
        do {
            System.out.print("Username: ");
            user = is.nextLine();
            System.out.print("Password: ");
            pass = is.nextLine();
        } while (!f.login(user, pass));

        /*try {
            menumain.executa();
            switch (menumain.getOpcao()) {
                case 1:
                    menuuser(user);
                    break;
                case 2:
                    menuwareh();
                    break;
                case 3:
                    menuamigos(user);
            }
        }
        while (menumain.getOpcao() != 0);*/
    }
    
    protected void loginCond() {
        Scanner is = new Scanner(System.in);
        String pass;
        
        do {
            System.out.print("Username: ");
            user = is.nextLine();
            System.out.print("Password: ");
            pass = is.nextLine();
        } while (!f.login(user, pass));

        /*try {
            menumain.executa();
            switch (menumain.getOpcao()) {
                case 1:
                    menuuser(user);
                    break;
                case 2:
                    menuwareh();
                    break;
                case 3:
                    menuamigos(user);
            }
        }
        while (menumain.getOpcao() != 0);*/
    }
    
    protected void registar(){
        Scanner is = new Scanner(System.in);
        boolean sair = false;
        String usern, pass;

        do {
            System.out.print("Username: ");
            usern = is.nextLine();

            if (userExiste(usern)) {
                System.out.println("Nome de Utilizador ja existente!\n");
            } else {
                sair = true;
            }
        } while (!sair);

        System.out.print("Password: ");
        pass = is.nextLine();

        Client novo = new Client(usern, pass);

        f.addUser(usern, pass);
    }

    protected void carregarMenus() {
        String[] logreg = {"Login", //login
            "Registar"}; //addUser
        
        String [] logop = {"Condutor",
            "Passageiro"};
        
        menulogreg = new Menu(logreg);
        menulogop = new Menu(logop);
    }
}
