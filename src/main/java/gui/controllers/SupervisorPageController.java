package gui.controllers;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SupervisorPageController {
	
	@FXML
	Button button_log_out;
	
	
	@FXML
	Button button_add_assistants;
	
	@FXML
	Button button_messages;
	
	@FXML
	Button create_assistant_times;
	
	public void logoutHandler(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoLogin();
	}
	
	public void addAssistantsHandler() {
		App.getInstance().gotoSupervisorAddsAssistants();
	}
	public void messagesHandler() {
		App.getInstance().gotoInboxPage();
	}
	public void createAssistantTimesHandler() {
		App.getInstance().gotoSupervisorCreatesTimes();
	}

}
