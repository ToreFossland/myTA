package communication;

import java.time.LocalDateTime;

import gui.App;
import user.User;

public class Message {
	private String courceCode;
	private String subject;
	private String text;
	private User receiver;
	private User sender;
	private LocalDateTime timestamp;

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

}
