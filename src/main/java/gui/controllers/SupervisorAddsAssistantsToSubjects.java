package gui.controllers;


import javafx.scene.control.Button;
import gui.App;
import javafx.fxml.FXML;


public class SupervisorAddsAssistantsToSubjects{
	
	/* Select subject in the scrollplane beneath
	 * and add assistants by email
	 */
	
	@FXML
	Button button_return;
	
	@FXML
	Button button_confirm_assistants;
	
	public void returnHandler(javafx.event.ActionEvent event){
		App.getInstance().gotoSupervisorPage();
	}
	
	public void confirmAssistantsHandler(javafx.event.ActionEvent event){
		
	}

}
