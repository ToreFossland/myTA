package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class SupervisorCreatesTimesController {
	
	// Handle CheckBoxes events
	// 20 checkbokserleses fra v->h 
	
	@FXML 
	CheckBox checkBox1;
	
	public void handleCheckBox(ActionEvent event) {
		if(checkBox1.isSelected()) {
			System.out.println("Tid er valgt.");
			//her skjer det noe: blir rød og tid velges ???
		}
	}
	
}
