package communication;

import java.time.LocalDateTime;
import user.User;

public class Message implements Comparable<Message>{
	private String courceCode;
	private String subject;
	private String text;
	private User receiver;
	private User sender;
	private LocalDateTime timestamp;
	
	private static Message selectedMessage;

	public Message(User sender, User receiver, String subject, String text, LocalDateTime timestamp) {
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.text = text;
		this.timestamp = timestamp;
	}
	
	public Message(User sender, User receiver, String subject, String text) {
		this(sender,receiver,subject, text, LocalDateTime.now());
	}
	
	public String getCourceCode() {
		return courceCode;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public User getSender() {
		return sender;
	}
	
	//Used by InboxPageController
	public String getSenderEmail() {
		return sender.getEmail();
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public void setCourceCode(String courceCode) {
		this.courceCode = courceCode;
	}
	
	public static Message getSelectedMessage() {
		return selectedMessage;
	}
	
	public static void setSelectedMessage(Message message) {
		selectedMessage = message;
	}
	
	@Override
	public String toString() {
		return String.format("From: %s\nTo: %s\nSubject: %s\nText: %s", this.getSender().getEmail(), this.getReceiver().getEmail(), this.getSubject(), this.getText());
	}
	
	@Override
	public int compareTo(Message message) {
		if(message.getTimestamp().isBefore(this.getTimestamp()))
			return -1;
		else if (message.getTimestamp().isAfter(this.getTimestamp()))
			return 1;
		else
			return 0;
	}

}
