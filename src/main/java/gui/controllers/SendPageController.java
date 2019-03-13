package gui.controllers;

import communication.Message;
import communication.MessageSender;
import database.DBConnection;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import user.User;

public class SendPageController {
	@FXML
	Button button_send;
	
	@FXML
	Button button_return;
	
	@FXML
	TextField text_receiver_email;
	
	@FXML
	TextField text_subject;
	
	@FXML
	TextArea text_message_content;
	
	@FXML
	Text text_response;
	
	public void onClickSendMessage(javafx.event.ActionEvent event) throws Exception {
		String subject = text_subject.getText();
		String message_content = text_message_content.getText();
		String receiver_email = text_receiver_email.getText();
		if(DBConnection.userExists(receiver_email)){
			User receiver = User.generateUserObject(receiver_email);
			User sender = App.getInstance().getLoggedUser();
			Message message = new Message(sender,receiver, subject, message_content);
			MessageSender.sendMessage(message);
			text_response.setText("Message sent");
		}
		else {
			text_response.setText("The email does not exist.");
		}
		
	}
	
	//1 = student, 2 = teacher assistant, 3 = supervisor, 4 = admin
	public void onClickReturn(javafx.event.ActionEvent event) {
		App.getInstance().gotoPrevious();
	}
}
