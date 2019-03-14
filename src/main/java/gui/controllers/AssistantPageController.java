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
	
	@FXML
	Button addAssistantTimes_button;
	
	public void logoutHandler(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoLogin();
	}
	
	public void returnHandler() {
		App.getInstance().gotoStudentPage();
	}
	
	public void addAssistantTimesHandler(javafx.event.ActionEvent event) {
		App.getInstance().gotoAssistantChooseTime();
	}
	
	public void evaluatingHandler() {
		// go to TAViewEvaluationspage.fxml
		App.getInstance().gotoTAViewEval();
		
	}
	public void messagesHandler() {
		// ikke laget
	}

}


