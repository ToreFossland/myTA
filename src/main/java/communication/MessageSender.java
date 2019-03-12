package communication;

import java.util.ArrayList;

import database.MessageDao;

public class MessageSender {
	
	public static void sendMessage(Message message) {
		MessageDao.insertMessage(message);
	}
	
	public static void sendMessages(ArrayList<Message> messages) {
		MessageDao.insertMessages(messages);
	}
	
	
}
