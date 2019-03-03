package gui.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import database.DBBooking;
import gui.App;
import halltimes.Booking;
import halltimes.Halltime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

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
	ChoiceBox<String> course_input;

	@FXML
	Button button_confirm_assistant_times;
	
	@FXML
	Label confirm_label;

	CheckBox[][] checkboxes;

	List<Booking> bookings;

	@FXML
	public void initialize() {
		checkboxes = new CheckBox[][] { { checkBox1, checkBox2, checkBox3, checkBox4 },
				{ checkBox5, checkBox6, checkBox7, checkBox8 }, { checkBox9, checkBox10, checkBox11, checkBox12 },
				{ checkBox13, checkBox14, checkBox15, checkBox16 },
				{ checkBox17, checkBox18, checkBox19, checkBox20 } };

		// Fills course choicebox
		Map<String, Integer> allCourses = App.getInstance().getLoggedUser().getMyCourses();
		List<String> relevantCourses = new ArrayList<String>();
		for (Entry<String, Integer> entry : allCourses.entrySet()) {
			String course = entry.getKey();
			Integer role = entry.getValue();
			if (role == 2)
				relevantCourses.add(course);
		}
		course_input.getItems().addAll(relevantCourses);

		bookings = App.getInstance().getDownloadedBookingsTA();

		List<Integer> availableWeeks = App.getInstance().getDownloadedWeeksTA();
		Collections.sort(availableWeeks);

		week_input.getItems().addAll(availableWeeks);

		for (Integer week : availableWeeks) {
			System.out.println(Integer.toString(week));
			if (week >= getCurrentWeek()) {
				week_input.setValue(week);
				break;
			}
		}
		course_input.setValue(relevantCourses.get(0));

		loadAvailableTimes();

	}

	public void loadAvailableTimes() {
		// Disables all checkboxes
		for (CheckBox[] checkboxRow : checkboxes) {
			for (CheckBox checkbox : checkboxRow) {
				checkbox.setDisable(true);
			}
		}
		int week = week_input.getValue();
		// Enables checkbox one by one
		for (Booking booking : bookings) {
			if (booking.getCourseCode().equals(course_input.getValue()) && booking.getWeek() == week
					&& booking.getStartTime().getHour() % 2 == 0 && booking.getStartTime().getMinute() == 0) {
				checkboxes[booking.getDay() - 1][(booking.getStartTime().getHour() - 8) / 2].setDisable(false);
			}
		}
	}

	public void weekInputHandler(ActionEvent event) {
		loadAvailableTimes();
	}

	public void courseInputHandler(ActionEvent event) {
		loadAvailableTimes();
	}

	public void confirmHandler(ActionEvent event) {

		ArrayList<Booking> bookings = new ArrayList<Booking>();
		
		for (int i = 0; i < checkboxes.length; i++) {
			for (int j = 0; j < checkboxes[i].length; j++) {
				if (checkboxes[i][j].isSelected()) {
					LocalTime timeStart = LocalTime.of(8 + j*2, 0, 0);
					LocalTime timeEnd = LocalTime.of(10 + j*2, 0, 0);
					
					Halltime newHT = new Halltime(course_input.getValue(), week_input.getValue(), i+1, timeStart, timeEnd, 0);
					Booking booking = new Booking(newHT, App.getInstance().getLoggedUser().getEmail());
					bookings.addAll(booking.splitIntoMultipleBookings(30));
				}
			}
		}

		try {
			DBBooking.addHalltimeTA(bookings);
			confirm_label.setText("Assistant times added!");
		} catch (Exception e) {
			confirm_label.setText("Adding assistant times failed! :(");
			e.printStackTrace();
		} finally {
			confirm_label.setVisible(true);
		}

	}

	public void returnHandler(ActionEvent event) {
		App.getInstance().gotoAssistantPage();
	}

	private int getCurrentWeek() {
		LocalDate date = LocalDate.now();
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		return date.get(weekFields.weekOfWeekBasedYear());
	}

}
