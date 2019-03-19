package gui.controllers;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StudentAddOrViewPageController {
	
	@FXML
	Button return_button;
	
	@FXML
	Button view_button;
	
	@FXML
	Button add_button;
	
	public void onClickViewAssignments(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoViewAssignments();
		
	}
	
	public void onClickAddAssignment(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoAddAssignment();
	}
	
	public void returnHandler(javafx.event.ActionEvent event) {
		App.getInstance().gotoStudentPage();
	}
	

}
