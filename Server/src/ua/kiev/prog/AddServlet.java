package ua.kiev.prog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddServlet extends HttpServlet {

    private MessageList msgList = MessageList.getInstance();        //ссилочка на общий список

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        byte[] buf = requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);    //читаем сообщения и преобразуем в клас меседж
        //десиализируем джсон обект в джава


        Message msg = Message.fromJSON(bufStr);  //пробразования пакета с ДЖСОН в обьект Меседж

        if (msg != null) {
            //My code
            if (msg.getText().indexOf("@@") != -1) {
                String s = Code.toCode(msg.getText());
                msgList.add(new Message("@Server", msg.getFrom(), s, msg.getChatkey()));

            } else if (msg.getText().indexOf("@createLogin") != -1) {
                msgList.add(new Message("@Server", msg.getFrom(), Login.createdUser(msg.getText()), msg.getChatkey()));

            } else if (msg.getText().indexOf("@seachLogIn") != -1) {
                msgList.add(new Message("@Server", msg.getFrom(), Login.logIn(msg.getText()), msg.getChatkey()));

            } else {
                msgList.add(msg);
            }
        } else
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    private byte[] requestBodyToArray(HttpServletRequest req) throws IOException {
        InputStream is = req.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }
}
