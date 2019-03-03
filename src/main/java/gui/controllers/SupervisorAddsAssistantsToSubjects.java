package gui.controllers;


import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gui.App;
import javafx.fxml.FXML;


public class SupervisorAddsAssistantsToSubjects{
	
	/* Select subject in the scrollplane beneath
	 * and add assistants by email
	 */
	
	@FXML
	Button button_return;
	
	@FXML
	Button button_confirm_assistants;
	
	@FXML
	ListView<String> course_list;
	
	@FXML
	public void initialize() {
		Map<String, Integer> coursesAndRoles = App.getInstance().getLoggedUser().getMyCourses();
		List<String> courses = new ArrayList<String>();
		for (String course : coursesAndRoles.keySet()) {
			courses.add(course);
		}
		
		Collections.sort(courses);
		course_list.getItems().addAll(courses);
	}
	
	public void returnHandler(javafx.event.ActionEvent event){
		App.getInstance().gotoSupervisorPage();
	}
	
	public void confirmAssistantsHandler(javafx.event.ActionEvent event){
		
	}

	private boolean isValidEmail(String email) {
		// matches emails that ends with ntnu.no and has no errounous dots. Returns true
		// if match
		Pattern pattern = Pattern.compile(
				"^(?!\\.)(?!.*\\.$)(?!.*?\\.\\.)([a-zA-Z0-9_.+-])+(@(?!\\.)([a-zA-Z0-9_.+-]+)\\.|@)+ntnu\\.no$");
		Matcher matcher = pattern.matcher(email);

		return matcher.find();
	}
	
}
