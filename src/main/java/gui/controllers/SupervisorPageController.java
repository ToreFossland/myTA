package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SupervisorPageController {
	
	@FXML
	Button button_log_out;
	
	@FXML
	Button button_return;
	
	@FXML
	Button button_subjects_and_assistants;
	
	@FXML
	Button button_messages;
	
	public void logoutHandler(javafx.event.ActionEvent event) {
	// log out, go to login.fxml
	// App.getInstance().gotoLogin();; ?
	}
	public void returnHandler() {
	// Switches to login.fxml ?
	}
	public void subjectsAndAssistantsHandler() {
		// switches to ? ikke laget
	}
	public void messagesHandler() {
		// ikke laget
	}

}
