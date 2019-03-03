package gui.controllers;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
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


public class BookingForStudent {

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
	CheckBox checkBox21;
	@FXML
	CheckBox checkBox22;
	@FXML
	CheckBox checkBox23;
	@FXML
	CheckBox checkBox24;
	@FXML
	CheckBox checkBox25;
	@FXML
	CheckBox checkBox26;
	@FXML
	CheckBox checkBox27;
	@FXML
	CheckBox checkBox28;
	@FXML
	CheckBox checkBox29;
	@FXML
	CheckBox checkBox30;
	@FXML
	CheckBox checkBox31;
	@FXML
	CheckBox checkBox32;
	@FXML
	CheckBox checkBox33;
	@FXML
	CheckBox checkBox34;
	@FXML
	CheckBox checkBox35;
	@FXML
	CheckBox checkBox36;
	@FXML
	CheckBox checkBox37;
	@FXML
	CheckBox checkBox38;
	@FXML
	CheckBox checkBox39;
	@FXML
	CheckBox checkBox40;
	@FXML
	CheckBox checkBox41;
	@FXML
	CheckBox checkBox42;
	@FXML
	CheckBox checkBox43;
	@FXML
	CheckBox checkBox44;
	@FXML
	CheckBox checkBox45;
	@FXML
	CheckBox checkBox46;
	@FXML
	CheckBox checkBox47;
	@FXML
	CheckBox checkBox48;
	@FXML
	CheckBox checkBox49;
	@FXML
	CheckBox checkBox50;
	@FXML
	CheckBox checkBox51;
	@FXML
	CheckBox checkBox52;
	@FXML
	CheckBox checkBox53;
	@FXML
	CheckBox checkBox54;
	@FXML
	CheckBox checkBox55;
	@FXML
	CheckBox checkBox56;
	@FXML
	CheckBox checkBox57;
	@FXML
	CheckBox checkBox58;
	@FXML
	CheckBox checkBox59;
	@FXML
	CheckBox checkBox60;
	@FXML
	CheckBox checkBox61;
	@FXML
	CheckBox checkBox62;
	@FXML
	CheckBox checkBox63;
	@FXML
	CheckBox checkBox64;
	@FXML
	CheckBox checkBox65;
	@FXML
	CheckBox checkBox66;
	@FXML
	CheckBox checkBox67;
	@FXML
	CheckBox checkBox68;
	@FXML
	CheckBox checkBox69;
	@FXML
	CheckBox checkBox70;
	@FXML
	CheckBox checkBox71;
	@FXML
	CheckBox checkBox72;
	@FXML
	CheckBox checkBox73;
	@FXML
	CheckBox checkBox74;
	@FXML
	CheckBox checkBox75;
	@FXML
	CheckBox checkBox76;
	@FXML
	CheckBox checkBox77;
	@FXML
	CheckBox checkBox78;
	@FXML
	CheckBox checkBox79;
	@FXML
	CheckBox checkBox80;

	@FXML
	Button button_return;

	@FXML
	Button button_confirm_booking;

	@FXML
	ChoiceBox<String> course_input;

	@FXML
	ChoiceBox<Integer> week_input;
	
	@FXML
	Label confirm_label;

	CheckBox[][] checkboxes;
	
	List<Booking> bookings;
	
	static final int BOOKINGLENGTH = 30;

	@FXML
	public void initialize() {
		CheckBox[][] checkBoxesInitialized = {
				{ checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9,
						checkBox10, checkBox11, checkBox12, checkBox13, checkBox14, checkBox15, checkBox16 },
				{ checkBox17, checkBox18, checkBox19, checkBox20, checkBox21, checkBox22, checkBox23, checkBox24,
						checkBox25, checkBox26, checkBox27, checkBox28, checkBox29, checkBox30, checkBox31,
						checkBox32 },
				{ checkBox33, checkBox34, checkBox35, checkBox36, checkBox37, checkBox38, checkBox39, checkBox40,
						checkBox41, checkBox42, checkBox43, checkBox44, checkBox45, checkBox46, checkBox47,
						checkBox48 },
				{ checkBox49, checkBox50, checkBox51, checkBox52, checkBox53, checkBox54, checkBox55, checkBox56,
						checkBox57, checkBox58, checkBox59, checkBox60, checkBox61, checkBox62, checkBox63,
						checkBox64 },
				{ checkBox65, checkBox66, checkBox67, checkBox68, checkBox69, checkBox70, checkBox71, checkBox72,
						checkBox73, checkBox74, checkBox75, checkBox76, checkBox77, checkBox78, checkBox79,
						checkBox80 } };
		checkboxes = checkBoxesInitialized;
		
		Map<String, Integer> allCourses = App.getInstance().getLoggedUser().getMyCourses();
		List<String> relevantCourses = new ArrayList<String>();
		for (Entry<String, Integer> entry : allCourses.entrySet()) {
			String course = entry.getKey();
			Integer role = entry.getValue();
			if (role == 1)
				relevantCourses.add(course);
		}
		course_input.getItems().addAll(relevantCourses);

		bookings = App.getInstance().getDownloadedBookingsStudent();

		List<Integer> availableWeeks = App.getInstance().getDownloadedWeeksStudent();
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
	public void returnHandler(javafx.event.ActionEvent event){
		App.getInstance().gotoStudentPage();
	}
	
	public void loadAvailableTimes() {
		// Disables all checkboxes
		for (CheckBox[] checkboxRow : checkboxes) {
			for (CheckBox checkbox : checkboxRow) {
				checkbox.setDisable(true);
			}
		}
		int week = week_input.getValue();
		int bookingNo = 0;
		// Enables checkbox one by one
		for (Booking booking : bookings) {
			if (booking.getCourseCode().equals(course_input.getValue()) && booking.getWeek() == week) {
				bookingNo = (booking.getStartTime().getHour() -8) * 2 + booking.getStartTime().getMinute() / BOOKINGLENGTH; 
				checkboxes[booking.getDay() - 1][bookingNo].setDisable(false);
			}
		}
	}

	public void weekInputHandler(ActionEvent event) {
		loadAvailableTimes();
	}

	public void courseInputHandler(ActionEvent event) {
		loadAvailableTimes();
	}
	
	public void confirmBooking(javafx.event.ActionEvent event) {

		ArrayList<Booking> bookings = new ArrayList<Booking>();
		
		for (int i = 0; i < checkboxes.length; i++) {
			for (int j = 0; j < checkboxes[i].length; j++) {
				if (checkboxes[i][j].isSelected()) {
					LocalTime timeStart = LocalTime.of(8 + j / 2, ((j) % 2)*BOOKINGLENGTH, 0);
					LocalTime timeEnd = timeStart.plusMinutes(BOOKINGLENGTH);
					
					Halltime newHT = new Halltime(course_input.getValue(), week_input.getValue(), i+1, timeStart, timeEnd, 0);
					Booking booking = new Booking(newHT, null, App.getInstance().getLoggedUser().getEmail());
					bookings.add(booking);
					System.out.println(booking.getStartTime());
				}
			}
		}

		try {
			DBBooking.addHalltimeStudent(bookings);
			confirm_label.setText("Assistant times booked!");
		} catch (Exception e) {
			confirm_label.setText("Booking failed! :(");
			e.printStackTrace();
		} finally {
			confirm_label.setVisible(true);
		}
	}
	
	private int getCurrentWeek() {
		LocalDate date = LocalDate.now();
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		return date.get(weekFields.weekOfWeekBasedYear());
	}

}
