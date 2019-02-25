package ua.kiev.prog;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends HttpServlet {

    private String login;
    private String password;
    private static List<String> only = new ArrayList<>();
    private String key = "null:0000";


    public Login(String login, String passord) {
        this.login = login;
        this.password = passord;
    }


    static final Map<String, String> sicret = new HashMap<String, String>();

    static {
        sicret.put("Dron", "0000");
        sicret.put("user", "1111");
        sicret.put("admin", "qwerty");
    }

    public static Login vvod(String st) {

        String[] s = st.split(":");

        return new Login(s[1], s[2]);
    }

    public static String haveLogin() {
        StringBuilder str = new StringBuilder();
        for (String s : sicret.keySet()) {
            str.append("\n" + "[ " + s + " ]");
        }
        return new String(str);
    }

    public static boolean seachLogin(String login) {
        int a = 0;
        for (String s : sicret.keySet()) {
            if (s.equals(login)) {
                break;
            }
            a++;
        }
        if (sicret.size() == a) {
            return false;
        } else {
            return true;
        }
    }

    public static String createdUser(String st) {     //СОЗДАНИЕ акаунта
        Login log = vvod(st);
        if (!seachLogin(log.login)) {
            sicret.put(log.login, log.password);
            only.add(log.login);
            return "@true Акаунт " + log.login + " створено" + "\nВи успішно ввійшли в чат!";
        } else {
            return "@false Данний логін вже існує! Введіть інший логін";
        }
    }

    public static String logIn(String st) {       //ПРОВЕРКА подлинности
        String msg;
        Login log = vvod(st);

        String t = sicret.get(log.login);
        if (log.password.equals(t)) {
            only.add(log.login);
            msg = "@true Ви успішно ввійшли в чат!";
        } else {
            msg = "@false Помилка паролю чи логіну!";
        }
        return msg;
    }

    public static String whois() {
        StringBuilder in = new StringBuilder();
        List<String> all = new ArrayList<>();
        for (String s : sicret.keySet()) {
            all.add(s);
        }
        for (String on : only) {
            for (String a : all) {
                if (a.equals(on)) {
                    in.append("\n" + a + " Online");
                    continue;
                }
            }
        }
        return new String(in);
    }

    public static String goodbye(String s) {
        String[] st = s.split(":");
        int a=0;
        do {
            for (String s1 : only) {
                if (st[1].equals(s1)) {
                    only.remove(s1);
                    a++;
                }
            }
        }while (a>0);
        return new String(st[1] + " покинув чат");
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLogin() {
        return login;
    }
}
