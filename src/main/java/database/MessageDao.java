package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.dbcp2.BasicDataSource;

import communication.Message;
import gui.App;
import user.User;

public class MessageDao {
	// Replaces if duplicate
	public static void insertMessages(ArrayList<Message> messages) {
		Connection con = null;
		PreparedStatement statement = null;

		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
			con = bds.getConnection();

			statement = con.prepareStatement(
					"REPLACE INTO Message (subject,text,Sender,Receiver,timestamp) VALUES(?,?,?,?,?)");

			for (Message message : messages) {
				statement.setObject(1, message.getSubject());
				statement.setObject(2, message.getText());
				statement.setObject(3, message.getSender().getEmail());
				statement.setObject(4, message.getReceiver().getEmail());
				statement.setObject(5, Timestamp.valueOf(message.getTimestamp()));

				statement.addBatch();
			}

			statement.executeBatch();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
			}
			;
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
			}
			;
		}

	}

	public static void insertMessage(Message message) {
		insertMessages(new ArrayList<Message>(Arrays.asList(message)));
	}

	public static ArrayList<Message> getAllMessages(User user) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;

		ArrayList<Message> messages = new ArrayList<Message>();
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
			con = bds.getConnection();
			statement = con.prepareStatement("SELECT * FROM Message WHERE Sender = ? OR Receiver = ?");
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getEmail());

			result = statement.executeQuery();

			while (result.next())
				messages.add(generateMessageObjectFromResultSet(result, user));

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try { if (result != null) result.close(); } catch (Exception e) {};
			try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		return messages;
	}

	private static Message generateMessageObjectFromResultSet(ResultSet result, User loggedUser) throws SQLException {
		String subject = result.getString("subject");
		String text = result.getString("text");
		User sender = result.getString("Sender").equals(loggedUser.getEmail())
				? loggedUser : User.generateUserObject(result.getString("Sender"));

		User receiver = result.getString("Receiver").equals(loggedUser.getEmail())
				? loggedUser : User.generateUserObject(result.getString("Receiver"));
		LocalDateTime timestamp = result.getTimestamp("timestamp").toLocalDateTime();

		return new Message(sender, receiver, subject, text, timestamp);
	}
	/*
	public static void main(String[] args) {
		Message test = new Message(User.generateUserObject("abc@ntnu.no"), User.generateUserObject("davidaan@stud.ntnu.no"),"hallo", "står til?");
		//insertMessage(test);
		System.out.println(getAllMessages(User.generateUserObject("abc@ntnu.no")));
	}*/
}
