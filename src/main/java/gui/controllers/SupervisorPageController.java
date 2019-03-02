package gui.controllers;

import gui.App;
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
	
	@FXML
	Button create_assistant_times;
	
	public void logoutHandler(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoLogin();
	}
	public void returnHandler() {
	// Switches to login.fxml ?
	}
	public void subjectsAndAssistantsHandler() {
		// switches to: legg tilassistent
	}
	public void messagesHandler() {
		// ikke laget
	}
	public void createAssistantTimesHandler() {
		App.getInstance().gotoSupervisorCreatesTimes();
	}

}
