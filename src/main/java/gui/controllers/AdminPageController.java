package gui.controllers;

import database.DBConnection;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	
	@FXML
	Label subject_response;
	
	@FXML
	Label supervisor_response;
	
	//valid om faget ikke eksisterer
	public boolean isValidSubject(String subject) throws Exception {
		boolean valid = true;
		if (DBConnection.fagEksisterer(subject)){
			valid=false;
		}
		return valid;
	}
	
	public void onClickAddSubject(javafx.event.ActionEvent event) throws Exception {
		if (isValidSubject(add_subjectCode_input.getText())) {
			String new_subject = add_subjectCode_input.getText();
			String new_name = add_subjectName_input.getText();
			DBConnection.leggTilCourse(new_subject, new_name);	
			subject_response.setText("Course added");
		}
		else {
			subject_response.setText("Course already exists");
		}
		//adder fag til DB
	}
	
	public boolean isValidSupervisor(String email, String course) throws Exception {
		boolean valid =true;
		if(DBConnection.brukerHarCourseEksisterer(email, course, 3) || isValidSubject(course) || !checkUserExists(email)) {
			valid=false;
		}
		return valid;
	}
	public boolean checkUserExists(String email) throws Exception {
		return DBConnection.userExists(email);
		
	}
	
	public void onClickAddSupervisor(javafx.event.ActionEvent event) throws Exception {
		String email = supervisor_email_input.getText();
		String course = choose_supervisor_subject_input.getText();
		if (isValidSupervisor(email, course)) {
			DBConnection.leggTilUserHarCourse(email,course,3);
			supervisor_response.setText("Supervisor has been added to " + course + " .");
		}
		else {
			supervisor_response.setText("Could not add supervisor to subject.");
		}
	}
	
	public void logoutHandler(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoLogin();
	}
	
	

}
