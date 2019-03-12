package communication;

import java.util.ArrayList;

import database.MessageDao;
import gui.App;

public class MessageInbox {
	private ArrayList<Message> inbox;
	
	public ArrayList<Message> refreshInbox(){
		this.inbox =  MessageDao.getAllMessages(App.getInstance().getLoggedUser());
		return getInbox();
	
	}
	public ArrayList<Message> getInbox(){
		return inbox;
		
	}
}
