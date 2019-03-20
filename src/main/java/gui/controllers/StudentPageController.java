package gui.controllers;

import java.util.Iterator;
import java.util.Map;

import database.DBBooking;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import user.User;

public class StudentPageController {

	@FXML
	Button button_log_out;
	
	@FXML
	Button teacher_assistant;
	
	@FXML
	Button button_book_TA;
	
	@FXML
	Button button_evaluating;
	
	@FXML
	Button button_add_subject;
	
	@FXML
	Button button_messages;
	
	@FXML
	Label booking_response;
	
	@FXML
	Button button_my_calendar;
	
	public void logoutHandler(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoLogin();;
	}
	public void gotoTA(javafx.event.ActionEvent event) throws Exception {
		User user = App.getInstance().getLoggedUser();
		String email = user.getEmail();
		if (App.getInstance().isRole(email,2)) {
			App.getInstance().gotoAssistantPage();
		}
		
	}
	
	public void myCalendarHandler(javafx.event.ActionEvent event) {
		App.getInstance().gotoMyCalendar();
	}
	
	public boolean subjectsExist(){
		Map<String,Integer> liste = App.getInstance().getLoggedUser().getMyCourses();
		Iterator<Map.Entry<String, Integer>> itr = liste.entrySet().iterator();
		boolean hasSubjects=false;
		System.out.println(liste);
		while(itr.hasNext()) {
			Map.Entry<String, Integer> entry = itr.next();
			if (entry.getValue().equals(1)){
				hasSubjects=true;
				break;
			}
		}
		return hasSubjects;
	}
	public void bookTAHandler() throws Exception {
		DBBooking.downloadBookings();
		if(subjectsExist()) {
			App.getInstance().gotoBookingForStudent();
		}
		else {
			booking_response.setText("Add subjects.");
		}
	}
	public void evaluatingHandler() {
		// go to StudentAddOrViewPage.fxml
		App.getInstance().gotoStudentAddOrView();
		
	}
	
	public void addSubjectHandler() {
		App.getInstance().gotoAddStudentSubject();
	}
	public void messagesHandler() {
		App.getInstance().gotoInboxPage();
	}
	
		 
}
