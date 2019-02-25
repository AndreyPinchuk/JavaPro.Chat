package com.company;

import java.util.Scanner;

public class Login {

    private final String login;
    private final String password;
//    private boolean lock = true;
    private String key = "null:0000";

    public Login(String login, String passord) {
        this.login = login;
        this.password = passord;
    }


//    private void setLock(boolean lock) {
//        this.lock = lock;
//    }

    public static Login vvod() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введіть свій логін: ");
        String log = sc.nextLine();

        System.out.println("Введіть свій пароль: ");
        String pass = sc.nextLine();

        return new Login(log, pass);
    }

    public static Login createdUser() {
        Login log = vvod();
        return log;
    }

    public static Login logIn() {
//        String msg;
        Login log = vvod();

        return log;
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

//    public boolean isLock() {
//        return lock;
//    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
