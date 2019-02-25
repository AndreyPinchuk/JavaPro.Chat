package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetThread implements Runnable {    //тред спрашивающий есть ли новые сообщения
    private final Gson gson;
    private int n;
    private int n2;
//    private static String login;
    private static boolean lock;

    public GetThread() {
        gson = new GsonBuilder().create();
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {



                URL url = new URL(Utils.getURL() + "/get?from=" + n+"&logy="+Main.getUser().getLogin()+"&key="+Main.getUser().getKey());       //запрос через параметр from
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                InputStream is = http.getInputStream();     //делаем запрос на сервер
                try {
                    byte[] buf = requestBodyToArray(is);
                    String strBuf = new String(buf, StandardCharsets.UTF_8);    //десиализируем то что нам пришло

                    JsonMessages list = gson.fromJson(strBuf, JsonMessages.class);
                    if (list != null) {
                        for (Message m : list.getList()) {

                            if (m.getTo().equals("") || m.getTo().equals(Main.getUser().getLogin()) || m.getFrom().equals(Main.getUser().getLogin())) {  //даем пользователю только общие и его сообщения
                                System.out.println(m);

                                if(m.getFrom().equals("@Server")){
                                    lock = answer(m.getText());
                                }
                            }
                            n++;
                        }
                    }
                } finally {
                    is.close();
                }


                Thread.sleep(500);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private byte[] requestBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }

//    public static void setLogin(String login) {
//        GetThread.login = login;
//    }

    public static boolean answer(String msg) {
        if (msg.indexOf("@true") != -1) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isLock() {
        return lock;
    }
}
