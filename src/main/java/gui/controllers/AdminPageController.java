package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminPageController {
	
	@FXML
	Button button_log_out;
	
	@FXML
	Button button_return;
	
	@FXML
	TextField create_subject_input;
	// Type subjectcode and name -
	
	@FXML 
	Button button_add;
	
	@FXML 
	TextField choose_subject_input;
	// Type subjectcode and name
	
	@FXML
	TextField add_supervisor_input;
	// Type email
	
	@FXML
	Button button_confirm;
	
	public void checkValidSubject() {
		// Kan dette
	}
	
	public boolean isValidSubject(String subejct) {
		//
		return true;
	}
	
	public void onClickAdd(javafx.event.ActionEvent event) throws Exception {
		if (isValidSubject(create_subject_input.getText())) {
			String new_subject = create_subject_input.getText();
		}
		//adder fag til DB
	}
	
	public void checkSubjectExists() {
		// Sjekker om fag er i DB
	}
	
	public boolean isValidSupervisor(String supervisor) {
		// sjekk om supervisor er i DB
		return true;
	}
	public void checkValidSupervisor() {
		//Sjekkerom supervisor er valid
	}
	
	public void onClickConfirm(javafx.event.ActionEvent event) {
		// adder supervisor til fag
	}
	
	public void logoutHandler(javafx.event.ActionEvent event) {
	// log out, go to login.fxml
	// App.getInstance().gotoLogin();; ?
	}
	public void returnHandler() {
	// Switches to login.fxml ?
	}
	

}
