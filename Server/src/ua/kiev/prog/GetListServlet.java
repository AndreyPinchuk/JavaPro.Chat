package ua.kiev.prog;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetListServlet extends HttpServlet {

    private MessageList msgList = MessageList.getInstance();    //ссилочка на общий список

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (Login.seachLogin(req.getParameter("logy"))) {
            String fromStr = req.getParameter("from");        //Извлечения параметра фром!!
            String key = req.getParameter("key");
            String login = req.getParameter("logy");
            int from = 0;
            try {
                from = Integer.parseInt(fromStr);    //конвертация фром в интеджер
                if (from < 0) from = 0;
            } catch (Exception ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            resp.setContentType("application/json");

            String json = msgList.toJSON(from, key,login);        //из списка выбераем сообщения которые поновее
            if (json != null) {                     //если масив не пустой отправка клиенту
                OutputStream os = resp.getOutputStream();
                byte[] buf = json.getBytes(StandardCharsets.UTF_8);
                os.write(buf);

            }
        }

    }
}
