package gui.controllers;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AssistantPageController {
	
	@FXML
	Button button_log_out;
	
	@FXML
	Button button_return;
	
	@FXML
	Button button_add_assistant_times;
	
	@FXML
	Button button_evaluating;
	
	@FXML
	Button button_messages;
	
	public void logoutHandler(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoLogin();
	}
	
	public void returnHandler() {
		App.getInstance().gotoStudentPage();
	}
	
	public void addAssistantTimesHandler(javafx.event.ActionEvent event) {
		// go to: AssisantChooseTime.fxml 
	}
	
	public void evaluatingHandler() {
		// switches to ? ikke laget 
	}
	public void messagesHandler() {
		// ikke laget
	}

}


