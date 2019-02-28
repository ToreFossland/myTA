package gui.controllers;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StudentPageController {

	@FXML
	Button button_log_out;
	
	@FXML
	Button button_return;
	
	@FXML
	Button button_book_TA;
	
	@FXML
	Button button_evaluating;
	
	@FXML
	Button button_add_subject;
	
	@FXML
	Button button_messages;
	
	public void logoutHandler(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoLogin();;
	}
	public void returnHandler(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoProfile();;
		
	}
	public void bookTAHandler() {
		// switched to BookingForStudent.fxml - ikke lagt inn FXML
	}
	public void evaluatingHandler() {
		// switches to ? ikke laget 
	}
	public void addSubjectHandler() {
		// switches to ? ikke laget
	}
	public void messagesHandler() {
		// ikke laget
	}
		 
}
