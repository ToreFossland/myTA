package gui.controllers;

import communication.Message;
import communication.MessageSender;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	Label label_new_message;
	
	public void onClickSendMessage(javafx.event.ActionEvent event) {
		String subject = text_subject.getText();
		String message_content = text_message_content.getText();
		String receiver_email = text_receiver_email.getText();
		User receiver = User.generateUserObject(receiver_email);
		User sender = App.getInstance().getLoggedUser();
		Message message = new Message(sender,receiver, subject, message_content);
		MessageSender.sendMessage(message);
	}
	
	//1 = student, 2 = teacher assistant, 3 = supervisor, 4 = admin
	public void onClickReturn(javafx.event.ActionEvent event) {
		int role = App.getInstance().getLoggedUser().getType();
		if (role == 1) {
			App.getInstance().gotoStudentPage();
		}
		else if (role == 2) {
			App.getInstance().gotoAssistantPage();
		}
		else if (role == 3) {
			App.getInstance().gotoStudentPage();
		}
	}
}
