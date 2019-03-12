package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ViewMessagePageController {
	@FXML
	Button button_new_message;
	
	@FXML
	Button button_return;
	
	@FXML
	Label text_receiver_email;
	
	@FXML
	Label text_subject;
	
	@FXML
	TextField text_message_content;
	
	@FXML
	Label label_new_message;
}
