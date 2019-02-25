package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Login user;

    public static void main(String[] args) {

        startMenu();

    }

    public static void startMenu() {
        Scanner scanner = new Scanner(System.in);
        int number;
        try {
            do {
                System.out.printf("Оберіть дію вписавши цифру:\n Створити свій акаунт - 1\n Зайти в свій акаунт - 2 \n Вийти - 3\n");
                number = scanner.nextInt();

            } while (!(number > 0 && number < 4));

            change(number);
        } catch (Exception e) {
            System.out.println("Введіть номер від 1 до 3\n");
            startMenu();
        }
    }

    public static void change(int nuber) {

        StringBuilder sb = new StringBuilder();
        switch (nuber) {
            case 1:
                user = Login.createdUser();
                entrMessage(user.getLogin(), sb.append("@createLogin:").append(user.getLogin()).append(":").append(user.getPassword()), nuber);
                break;

            case 2:
                user = Login.logIn();
                entrMessage(user.getLogin(), sb.append("@seachLogIn:").append(user.getLogin()).append(":").append(user.getPassword()), nuber);
                break;

            case 3:
                break;
        }
    }

    public static void servicAnsver(String login, StringBuilder messege, int number) {
        try {

            Message m0 = new Message(login, "@Server", new String(messege), user.getKey());
            m0.send(Utils.getURL() + "/add");

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!GetThread.isLock()) {
                do {
                    change(number);
                    m0 = new Message(login, "@Server", new String(messege), user.getKey());
                    m0.send(Utils.getURL() + "/add");
                } while (GetThread.isLock());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void entrMessage(String login, StringBuilder messege, int number) {

        Scanner scanner = new Scanner(System.in);
        try {

            Thread th = new Thread(new GetThread());
            th.setDaemon(true);
            th.start();

            servicAnsver(login, messege, number);


            Message m1 = new Message(login, "", login + " в чате", user.getKey());
            m1.send(Utils.getURL() + "/add");

            System.out.println("Enter your message: ");
            while (true) {
                String text = scanner.nextLine();
                if (text.isEmpty()) break;

                int res;

                if (text.indexOf("@@exit") != -1) {
//
                    Message m = new Message(login, "", user.getLogin() + " покинув чат", user.getKey());
                    res = m.send(Utils.getURL() + "/add");
                    break;
                }

                if (text.indexOf("@@") != -1) {
                    Message m = command(text);
                    res = m.send(Utils.getURL() + "/add");
                } else {
                    Message m = new Message(login, "", text, user.getKey());
                    res = m.send(Utils.getURL() + "/add");
                }

                if (res != 200) { // 200 OK
                    System.out.println("HTTP error occured: " + res);
                    return;
                }
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {

                //переделать выход!!!  когда пользователь вылетает????

                Message m = new Message("@Server", "@Server", user.getLogin() + "@@exit:" + login, user.getKey());
                m.send(Utils.getURL() + "/add");
            } catch (IOException e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }

    public static Message command(String s) {
        String[] s1;
        Message m1 = null;
        if (s.indexOf("@@to:") != -1) {
            s1 = s.split(":");
            m1 = new Message(user.getLogin(), s1[1], s1[2], user.getKey());
        } else if (s.indexOf("@@help") != -1) {
            m1 = new Message(user.getLogin(), "@Server", s, user.getKey());
        } else if (s.indexOf("@@listUser") != -1) {
            m1 = new Message(user.getLogin(), "@Server", s, user.getKey());
        } else if (s.indexOf("@@whois") != -1) {
            m1 = new Message(user.getLogin(), "@Server", s, user.getKey());
        } else if (s.indexOf("@@chatkey:") != -1) {
            s1 = s.split(":");
            user.setKey(s1[1]);
            m1 = new Message(user.getLogin(), "@Server", s, user.getKey());
        } else if (s.indexOf("@@@letsgo_chat:") != -1) {
            s1 = s.split(":");
            m1 = new Message(user.getLogin(), s1[1], "Вам запрошення від " + user.getLogin() + " відвідати чат кімнату з ключем= " + user.getKey(), "null:0000");
        } else if (s.indexOf("@@chatnull") != -1) {
            user.setKey("null:0000");
            m1 = new Message(user.getLogin(), "@Server", s, "null:0000");
        }

        return m1;
    }

    public static Login getUser() {
        return user;
    }
}
