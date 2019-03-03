package gui.controllers;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import user.User;

public class StudentPageController {

	@FXML
	Button button_log_out;
	
	@FXML
	Button teacher_assistant;
	
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
	public void gotoTA(javafx.event.ActionEvent event) throws Exception {
		User user = App.getInstance().getLoggedUser();
		String email = user.getEmail();
		if (App.getInstance().isRole(email,2)) {
			App.getInstance().gotoAssistantPage();
		}
		
	}
	public void bookTAHandler() {
		App.getInstance().gotoBookingForStudent();
	}
	public void evaluatingHandler() {
		// switches to ? ikke laget 
	}
	public void addSubjectHandler() {
		App.getInstance().gotoAddStudentSubject();
	}
	public void messagesHandler() {
		// ikke laget
	}
		 
}
