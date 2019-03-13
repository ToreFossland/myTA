package gui.controllers;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ViewAssignmentsPageController {
	
	@FXML
	Button return_handler;
	
	public void returnHandler(javafx.event.ActionEvent event) {
		App.getInstance().gotoStudentPage();
	}

}
