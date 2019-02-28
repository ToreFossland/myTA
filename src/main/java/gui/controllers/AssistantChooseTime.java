package gui.controllers;

import java.awt.Button;

import gui.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class AssistantChooseTime {
	
	@FXML
	Button button_return;
	
	@FXML
	CheckBox checkBox1;
	
	@FXML
	Button button_confirm_assistant_times;
	
	public void handleCheckBox1(ActionEvent event) {
		if(checkBox1.isSelected()) {
			// velger tid
			System.out.println("Time is chosen.");}
			
		}
	public void confirmHandler(ActionEvent event) {
		// confirm tider
	}
	
	public void returnHandler(ActionEvent event) {
		App.getInstance().gotoProfile();
	}

}
