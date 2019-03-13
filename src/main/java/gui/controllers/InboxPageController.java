package gui.controllers;

import java.util.ArrayList;
import java.util.Collections;

import communication.Message;
import database.MessageDao;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import user.User;

public class InboxPageController {

	@FXML
	TableView<Message> messageTable;
	
	@FXML
	TableColumn<Message, String> fromCol;
	
	@FXML
	TableColumn<Message, String> topicCol;
	
	@FXML
	Button writeMessageButton;
	
	@FXML
	public void initialize() {
		fromCol.setCellValueFactory(new PropertyValueFactory<>("senderEmail"));
		topicCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
		
		ArrayList<Message> messages = MessageDao.getAllMessages(App.getInstance().getLoggedUser());
		
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
	
	@FXML
	public void returnHandler(javafx.event.ActionEvent event) throws Exception {
		// Switches to registerpage.fxml
		App.getInstance().gotoPrevious();
	}
}
