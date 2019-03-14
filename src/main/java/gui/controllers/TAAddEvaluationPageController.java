package gui.controllers;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TAAddEvaluationPageController {
	
	@FXML
	Button button_confirm_score;
	
	@FXML
	TextField set_score;
	
	public void confirmHandler(javafx.event.ActionEvent event) throws Exception {
		
	}
	
	public void returnHandler(javafx.event.ActionEvent event) {
		App.getInstance().gotoAssistantPage();
	}
	

}
