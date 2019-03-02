package gui.controllers;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminPageController {
	
	@FXML
	Button button_log_out;
	
	
	@FXML
	TextField add_subjectCode_input;
	// Type subject code 
	
	@FXML
	TextField add_subjectName_input;
	// Type subject name
	
	@FXML 
	Button button_add_subject;
	
	@FXML 
	TextField choose_supervisor_subject_input;
	// Type subjectcode
	
	@FXML
	TextField supervisor_email_input;
	// Type email
	
	@FXML
	Button button_add_supervisor;
	
	public void checkValidSubject() {
		// Kan dette
	}
	
	public boolean isValidSubject(String subejct) {
		//
		return true;
	}
	
	public void onClickAddSubject(javafx.event.ActionEvent event) throws Exception {
		if (isValidSubject(add_subjectCode_input.getText())) {
			String new_subject = add_subjectCode_input.getText();
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
	
	public void onClickAddSupervisor(javafx.event.ActionEvent event) {
		// adder supervisor til fag
	}
	
	public void logoutHandler(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoLogin();
	}
	
	

}
