package gui.controllers;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AddOrViewStudentPageController {
	
	@FXML
	Button return_handler;
	
	@FXML
	Button view_handler;
	
	@FXML
	Button add_handler;
	
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
