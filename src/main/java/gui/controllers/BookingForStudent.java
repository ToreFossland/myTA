package gui.controllers;

import java.awt.Button;

import gui.App;
import javafx.fxml.FXML;

public class BookingForStudent {
	
	@FXML
	Button button_return;
	
	@FXML
	Button button_confirm_booking;
	
	public void returnHandler(javafx.event.ActionEvent event){
		App.getInstance().gotoProfile();
	}
	
	public void confirmBooking(javafx.event.ActionEvent event){
		// booke tider
		System.out.println("Time is booked.");
	}

}
