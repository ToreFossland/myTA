package gui.controllers;

import java.time.LocalTime;

import evaluation.Assignment;
import evaluation.AssignmentInbox;
import gui.App;
import halltimes.Booking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class ViewBookingInfoController {

	@FXML
	Button button_return;
	
	@FXML
	Text subject;
	
	@FXML
	Text assistant_name;
	
	@FXML
	Text day_and_time;
	
	@FXML
	Button delete_booking;
	
	@FXML
	Button send_message_to_assistant;
	
	public void returnHandler(javafx.event.ActionEvent event) {
		App.getInstance().gotoAssistantPage();
	}
	
	public void initialize() {
		// for (Booking booking:App.getInstance().getDownloadedBookingsStudent())
			
		Booking selectedBooking = getBooking(); // her må man hente booking fra noe sted

		String course = selectedBooking.getCourseCode();
		String assistantName = selectedBooking.getEmailTA();
		int day = selectedBooking.getDay();
		LocalTime startTime = selectedBooking.getStartTime();
		LocalTime endTime = selectedBooking.getEndTime();
		
		subject.setText(course);
		assistant_name.setText(assistantName);
		day_and_time.setText("Day: " + String.valueOf(day) + "\n" + "Time: from " + startTime.toString() + " to " + endTime.toString() + ".");
	}
	
	public void deleteHandler(javafx.event.ActionEvent event) {
		// selectedBooking.removeBooking();
	}
	
	public void sendMessageHandler(javafx.event.ActionEvent event) {
		App.getInstance().gotoSendMessagePage();
	}
	
	
}
