package communication;

import java.util.ArrayList;

import database.MessageDao;
import gui.App;

public class MessageInbox {
	
	
	private static ArrayList<Message> inbox;
	
	public static ArrayList<Message> refreshInbox(){
		inbox =  MessageDao.getAllMessages(App.getInstance().getLoggedUser());
		return getInbox();
	
	}
	public static ArrayList<Message> getInbox(){
		return inbox;
		
	}
}
