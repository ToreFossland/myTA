package gui.controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.DBConnection;
import gui.App;
import javafx.fxml.FXML;

public class SupervisorAddsAssistantsToSubjects {

	/*
	 * Select subject in the scrollplane beneath and add assistants by email
	 */

	@FXML
	Button button_return;

	@FXML
	TextField email_input;

	@FXML
	Label confirm_label;

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

	public void returnHandler(javafx.event.ActionEvent event) {
		App.getInstance().gotoSupervisorPage();
	}

	public void confirmAssistantsHandler(javafx.event.ActionEvent event) {
		try {
			if (course_list.getSelectionModel().getSelectedItem() == null)
				confirm_label.setText("Select a course");
			else if (!isValidEmail(email_input.getText()))
				confirm_label.setText("Invalid email");
			else if (!DBConnection.userExists(email_input.getText()))
				confirm_label.setText("User does not exist");
			else
			{
				DBConnection.leggTilUserHarCourse(email_input.getText(), course_list.getSelectionModel().getSelectedItem(), 2);
				confirm_label.setText("Assistant added!");
			}
			confirm_label.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
