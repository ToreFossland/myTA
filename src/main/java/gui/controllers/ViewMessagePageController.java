package gui.controllers;

import communication.Message;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class ViewMessagePageController {
	@FXML
	Button button_return;
	
	@FXML
	Text text_receiver_email;
	
	@FXML
	Text text_subject;
	
	@FXML
	Text text_message_content;
	
	@FXML
	Label label_new_message;
	
	@FXML
	public void initialize() {
		Message selectedMessage = Message.getSelectedMessage();
		//selectedMessage
		text_receiver_email.setText(selectedMessage.getSenderEmail());
		text_subject.setText(selectedMessage.getSubject());
		text_message_content.setText(selectedMessage.getText());
	}
	
	public void returnHandler() {
		App.getInstance().gotoPrevious();
	}
}
