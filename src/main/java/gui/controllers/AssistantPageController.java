package gui.controllers;

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
	
	public void logoutHandler(javafx.event.ActionEvent event) {
		// log out, go to login.fxml
			// App.getInstance().gotoLogin();; ?
	}
	public void returnHandler() {
		// Switches to login.fxml ?
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
