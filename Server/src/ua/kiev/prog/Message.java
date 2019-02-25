package ua.kiev.prog;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
	private LocalDateTime date = LocalDateTime.now();
	private String from;
	private String to;
	private String text;
	private String chatkey;

//	public Message(String from, String text) {
//		this.from = from;
//		this.text = text;
//	}


	public Message(String from, String to, String text, String chatkey) {
		this.from = from;
		this.to = to;
		this.text = text;
		this.chatkey = chatkey;
	}

	public String toJSON() {		//ПРЕОБРАЗОВАНИЕ в джсон
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
	
	public static Message fromJSON(String s) {		//ПРЕОБРАЗОВАНИЕ с джсон
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(s, Message.class);
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("[").append(date.format(DateTimeFormatter.ofPattern("d-MMM  HH:mm:ss")))
				.append(", From: ").append(from).append(", To: ").append(to)
				.append("] ").append(text)
                .toString();
	}

	public int send(String url) throws IOException {		//ОТПРАВКА пост запросом сообщения
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection)
				obj.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
	
		OutputStream os = conn.getOutputStream();
		try {
			String json = toJSON();
			os.write(json.getBytes(StandardCharsets.UTF_8));
			return conn.getResponseCode();
		} finally {
			os.close();
		}
	}

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getChatkey() {
		return chatkey;
	}

	public void setChatkey(String chatkey) {
		this.chatkey = chatkey;
	}
}
