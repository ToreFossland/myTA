package gui.controllers;

import communication.Message;
import communication.MessageSender;
import database.DBBooking;
import database.DBConnection;
import gui.App;
import halltimes.Booking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import user.User;

public class BookingInfoStudentController {
	@FXML
	Button button_return;
	
	@FXML
	Button delete_booking;
	
	@FXML
	Button Send_message_to_assistant;
	
	@FXML
	Text subject;
	
	@FXML
	Text assistant_name;
	
	@FXML
	Text day_and_time;
	
	@FXML
	Text text_response;
	
	
	
	public void initialize() {
		Booking chosenBooking = MyCalendarController.getChosenBooking();
		String courseCode = chosenBooking.getCourseCode();
		String assistantEmail = chosenBooking.getEmailTA();
		String firstname = DBConnection.returnUserObject(assistantEmail).getFirstName();
		String lastname = DBConnection.returnUserObject(assistantEmail).getLastName();
		subject.setText(courseCode);
		assistant_name.setText(firstname+" "+lastname);
		String week = Integer.toString(chosenBooking.getWeek());
		int day = chosenBooking.getDay();
		String dayname = dayname(day);
		String starttime = chosenBooking.getStartTime().toString();
		String endtime = chosenBooking.getEndTime().toString();
		day_and_time.setText(starttime+" - "+endtime+" "+dayname+" Week: "+ week);
	}
	
	public void returnHandler() {
		App.getInstance().gotoPrevious();
	}
	
	public void deleteBooking() throws Exception {
		Booking chosenBooking = MyCalendarController.getChosenBooking();
		DBBooking.deleteBooking(chosenBooking);
		String assistantEmail = chosenBooking.getEmailTA();
		String firstname = DBConnection.returnUserObject(assistantEmail).getFirstName();
		String lastname = DBConnection.returnUserObject(assistantEmail).getLastName();
		Message response = new Message(App.getInstance().getLoggedUser(),User.generateUserObject(assistantEmail), "Cancelled appointment", "Unfortunately your " +chosenBooking.getCourseCode()+ " appointment with "+firstname + " "+ lastname+" has been cancelled");
		MessageSender.sendMessage(response);
		text_response.setText("Booking deleted.");
		DBBooking.removeBookingStudent(chosenBooking);
		System.out.println("Trykk");
		//MyCalendarController.initialize();
		
	}
	
	public String dayname(int day) {
		String dayname;
		if(day==1) {
			dayname="Monday";
		}
		else if(day==2) {
			dayname="Tuesday";
			
		}
		else if(day==3) {
			dayname="Wednesday";
			
		}
		else if(day==4) {
			dayname="Thursday";
			
		}
		else {
			dayname="Friday";
			
		}
		return dayname;
		
		
	}
	public void gotoSendMessage() {
		Booking chosenBooking = MyCalendarController.getChosenBooking();
		SendPageController.setMessage_receiver(chosenBooking.getEmailTA());
		App.getInstance().gotoSendMessagePage();
	}
}
