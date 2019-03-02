package gui.controllers;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import database.DBBooking;
import gui.App;
import halltimes.Booking;
import halltimes.Halltime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	
	List<Halltime> halltimes;
	

	@FXML
    public void initialize() {
		User user = App.getInstance().getLoggedUser();
		checkboxes = new CheckBox[][] { { checkBox1, checkBox2, checkBox3, checkBox4 },
			{ checkBox5, checkBox6, checkBox7, checkBox8 }, { checkBox9, checkBox10, checkBox11, checkBox12 },
			{ checkBox13, checkBox14, checkBox15, checkBox16 }, { checkBox17, checkBox18, checkBox19, checkBox20 } };
			
		List<Booking> availableHalltimes = user.getAvailableBookings();
        List<Integer> availableWeeks = user.getAvailableWeeks();
        Collections.sort(availableWeeks);
		
		week_input.getItems().addAll(availableWeeks);
		
		if (availableWeeks.get(0) >= getCurrentWeek())
			week_input.setValue(availableWeeks.get(0));
			
    }
	
	public void loadAvailableTimes() {
		//Disables all checkboxes
		for (CheckBox[] checkboxRow : checkboxes) {
			for (CheckBox checkbox : checkboxRow) {
				checkbox.setDisable(true);
			}
		}
		int week = week_input.getValue();
		//Enables checkbox one by one
		for (Halltime halltime : halltimes) {
			if(halltime.getWeek() == week && halltime.getTimeStart().getHour() % 2 == 0 && halltime.getTimeStart().getMinute() == 0) {
				checkboxes[halltime.getTimeStart().getHour()][halltime.getDay()-1].setDisable(false);
			}
		}
	}
	
	public void confirmHandler(ActionEvent event) {
		/*List<Booking> bookings = new ArrayList<Booking>();
		
		for (int i = 0; i < checkboxes.length; i++) {
			for (int j = 0; j < checkboxes[i].length; j++) {
				if(checkboxes[i][j].isSelected()) {
					bookings.add(new Booking(null, null))
				}
			}
		}
		
		DBBooking.addHalltimeTA(bookings);*/
	}
	
	public void returnHandler(ActionEvent event) {
		App.getInstance().gotoProfile();
	}
	
	private int getCurrentWeek() {
	    LocalDate date = LocalDate.now();
	    WeekFields weekFields = WeekFields.of(Locale.getDefault());
	    return date.get(weekFields.weekOfWeekBasedYear());
	}

}
