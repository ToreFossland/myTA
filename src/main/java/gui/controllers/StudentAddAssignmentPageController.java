package gui.controllers;

import database.DBEvaluation;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StudentAddAssignmentPageController {
	
	@FXML
	Button return_button;
	
	@FXML
	Button upload_assignment;
	
	
	public void returnHandler(javafx.event.ActionEvent event) {
		App.getInstance().gotoStudentPage();
	}
	
	public void uploadHandler(javafx.event.ActionEvent event) {
		
	}
	

}
