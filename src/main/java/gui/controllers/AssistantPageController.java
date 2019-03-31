package gui.controllers;

import database.DBBooking;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AssistantPageController {
	
	@FXML
	Button button_log_out;
	
	@FXML
	Button button_return;
	
	@FXML
	Button button_add_assistant_times;
	
	@FXML
	Button button_evaluating;
	
	@FXML
	Button button_messages;
	
	@FXML
	Button addAssistantTimes_button;
	
	@FXML
	Button button_my_calendar;
	
	public void logoutHandler(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoLogin();
	}
	
	public void returnHandler() {
		App.getInstance().gotoStudentPage();
	}
	
	public void myCalendarHandler(javafx.event.ActionEvent event) throws Exception {
		DBBooking.downloadBookings(App.getInstance().getLoggedUser());
		App.getInstance().gotoMyCalendar();
	}
	
	public void addAssistantTimesHandler(javafx.event.ActionEvent event) {
		App.getInstance().gotoAssistantChooseTime();
	}
	
	public void evaluatingHandler() {
		// go to TAViewEvaluationspage.fxml
		App.getInstance().gotoTAViewEvaluationsPage();
		
	}
	public void messagesHandler() {
		App.getInstance().gotoInboxPage();
	}

}


