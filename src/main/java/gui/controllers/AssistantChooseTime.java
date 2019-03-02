package gui.controllers;

import java.awt.Button;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gui.App;
import halltimes.Halltime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import user.User;

public class AssistantChooseTime {
	
	@FXML
	Button button_return;
	
	@FXML
	CheckBox checkBox1;

	@FXML
	CheckBox checkBox2;

	@FXML
	CheckBox checkBox3;

	@FXML
	CheckBox checkBox4;

	@FXML
	CheckBox checkBox5;

	@FXML
	CheckBox checkBox6;

	@FXML
	CheckBox checkBox7;

	@FXML
	CheckBox checkBox8;

	@FXML
	CheckBox checkBox9;

	@FXML
	CheckBox checkBox10;

	@FXML
	CheckBox checkBox11;

	@FXML
	CheckBox checkBox12;

	@FXML
	CheckBox checkBox13;

	@FXML
	CheckBox checkBox14;

	@FXML
	CheckBox checkBox15;

	@FXML
	CheckBox checkBox16;

	@FXML
	CheckBox checkBox17;

	@FXML
	CheckBox checkBox18;

	@FXML
	CheckBox checkBox19;

	@FXML
	CheckBox checkBox20;
	
	@FXML
	ChoiceBox<Integer> week_input;
	
	@FXML
	Button button_confirm_assistant_times;
	
	CheckBox[][] checkboxes;
	

	@FXML
    public void initialize() {
		User user = App.getInstance().getLoggedUser();
		checkboxes = new CheckBox[][] { { checkBox1, checkBox2, checkBox3, checkBox4 },
			{ checkBox5, checkBox6, checkBox7, checkBox8 }, { checkBox9, checkBox10, checkBox11, checkBox12 },
			{ checkBox13, checkBox14, checkBox15, checkBox16 }, { checkBox17, checkBox18, checkBox19, checkBox20 } };
			
		List<Halltime> availableHalltimes = user.getAvailableBookings();
        List<Integer> availableWeeks = 
		
		week_input.getItems().addAll(courses);
    }
	
	public void confirmHandler(ActionEvent event) {
		// confirm tider
	}
	
	public void returnHandler(ActionEvent event) {
		App.getInstance().gotoProfile();
	}

}
