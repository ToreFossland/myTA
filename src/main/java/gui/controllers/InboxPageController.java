package gui.controllers;

import communication.Message;
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

		Message message = new Message(User.generateUserObject("abc@ntnu.no"),User.generateUserObject("davidaan@stud.ntnu.no"),"Viktig beskjed","hallo");
		messageTable.getItems().add(message);
	}
}
