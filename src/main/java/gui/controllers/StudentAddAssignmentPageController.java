package gui.controllers;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StudentAddAssignmentPageController {
	
	@FXML
	Button return_handler;
	
	@FXML
	Button upload_assignment;
	
	@FXML 
	TextField course_code;
	
	@FXML 
	TextField assignment_name;
	
	@FXML 
	Button add_file_handler;
	
	public void returnHandler(javafx.event.ActionEvent event) {
		App.getInstance().gotoStudentPage();
	}
	

}
