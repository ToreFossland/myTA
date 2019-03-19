package gui.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import communication.Message;
import communication.MessageInbox;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InboxPageController {

	@FXML
	TableView<Message> messageTable;
	
	@FXML
	TableColumn<Message, String> fromCol;
	
	@FXML
	TableColumn<Message, String> topicCol;
	
	@FXML
	Button button_new_message;
	
	@FXML
	public void initialize() {
		fromCol.setCellValueFactory(new PropertyValueFactory<>("senderEmail"));
		topicCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
		
		MessageInbox.refreshInbox();
		ArrayList<Message> messages = MessageInbox.getInbox();
		
		Logger.getLogger(App.class.getName()).log(Level.INFO, "Messages downloaded");
		
		messages = removeMessagesWhereSender(messages);
		Collections.sort(messages);
		
		messageTable.getItems().addAll(messages);
	}

	private ArrayList<Message> removeMessagesWhereSender(ArrayList<Message> messages) {
		ArrayList<Message> newList = new ArrayList<Message>();
		for (Message message : messages)
			if (!message.getSender().getEmail().equals(App.getInstance().getLoggedUser().getEmail()))
				newList.add(message);
		return newList;
	}
	
	public void onClickRead() {
		Message selectedMessage = messageTable.getSelectionModel().getSelectedItem();
		Message.setSelectedMessage(selectedMessage);
		if (selectedMessage !=null)
			App.getInstance().gotoReadMessagePage();
	}
	
	public void onClickNewMessage() {
		App.getInstance().gotoSendMessagePage();
	}
	
	@FXML
	public void returnHandler(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoPrevious();
	}
}
