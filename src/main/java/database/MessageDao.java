package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.dbcp2.BasicDataSource;

import communication.Message;

public class MessageDao {
	//Replaces if duplicate
	public static void insertMessages(ArrayList<Message> messages) {
		Connection con = null;
		PreparedStatement statement = null;

		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
			con = bds.getConnection();
			
			statement = con.prepareStatement(
					"REPLACE INTO Message (text,timestamp,Sender,Receiver) VALUES(?,?,?,?)");

			for (Message message : messages) {
				statement.setObject(1, message.getText());
				statement.setObject(2, Timestamp.valueOf(message.getTimestamp()));
				statement.setObject(3, message.getSender().getEmail());
				statement.setObject(4, message.getReceiver().getEmail());

				statement.addBatch();
			}
			
			statement.executeBatch();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}

	}
	
	public static void insertMessage(Message message) {
		insertMessages(new ArrayList<Message>(Arrays.asList(message)));
	}
}
