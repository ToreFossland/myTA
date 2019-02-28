package gui.controllers;

import java.awt.Button;

import database.DBBooking;
import gui.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class SupervisorCreatesTimesController {
	
	// Handle CheckBoxes events
	// 20 checkbokserleses fra v->h 
	
	@FXML 
	Button button_return;
	
	@FXML 
	CheckBox checkBox1;
	
	
	public void handleCheckBox(ActionEvent event) {
		if(checkBox1.isSelected()) {
			System.out.println("Time is chosen.");
			//her skjer det noe: blir rød og tid velges ???
			// DBBooking.supervisorAddHalltime(halltime, interval);
		}
	}
	public void returnHandler(ActionEvent event) {
		App.getInstance().gotoProfile();
	}
	
}
