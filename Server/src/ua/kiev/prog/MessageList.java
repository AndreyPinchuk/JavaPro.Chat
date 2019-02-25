package ua.kiev.prog;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageList {
    private static final MessageList msgList = new MessageList();

    private final Gson gson;
    private final List<Message> list = new LinkedList<>();
    private List<Message> listChat;


    public static MessageList getInstance() {
        return msgList;
    }

    private MessageList() {
        gson = new GsonBuilder().create();
    }


    public synchronized void add(Message m) {

        if ("@Server".equals(m.getFrom()) || Login.seachLogin(m.getFrom()))
            list.add(m);
    }

    public synchronized String toJSON(int n, String key,String login) {
        if (n >= list.size()) return null;
 /*       ///
        listChat = new LinkedList<>();
        for (Message message : list) {
            if (key.equals(message.getChatkey()) || login.equals(message.getTo())) {
                listChat.add(message);
            } else {
                listChat.add(null);
            }
        }
        ////*/
        return gson.toJson(new JsonMessages(list, n));
    }

}
