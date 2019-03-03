package gui.controllers;



import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BookingForStudent {
	
	@FXML
	Button button_return;
	
	@FXML
	Button button_confirm_booking;
	
	public void returnHandler(javafx.event.ActionEvent event){
		App.getInstance().gotoStudentPage();
	}
	
	public void confirmBooking(javafx.event.ActionEvent event){
		// booke tider
		System.out.println("Time is booked.");
	}

}
