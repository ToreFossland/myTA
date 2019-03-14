package gui.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import database.DBEvaluation;
import evaluation.Assignment;
import gui.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class StudentAddAssignmentPageController {
	
	@FXML
	Button return_button;
	
	@FXML
	TextField user_input;
	
	@FXML
	Button upload_assignment;
	
	@FXML
	ChoiceBox<String> course_input;
	
	public void initialize() {
		Map<String, Integer> allCourses = App.getInstance().getLoggedUser().getMyCourses();
		List<String> relevantCourses = new ArrayList<String>();
		for (Entry<String, Integer> entry : allCourses.entrySet()) {
			String course = entry.getKey();
			Integer role = entry.getValue();
			if (role == 1)
				relevantCourses.add(course);
		}
		if(!relevantCourses.isEmpty()) {
			course_input.getItems().addAll(relevantCourses);
			course_input.setValue(relevantCourses.get(0));
		}
	}
	
	public void returnHandler(javafx.event.ActionEvent event) {
		App.getInstance().gotoStudentPage();
	}
	
	public void uploadHandler(javafx.event.ActionEvent event) {
		Assignment assignment = new Assignment(App.getInstance().getLoggedUser(), course_input.getValue(), user_input.getText(), LocalDateTime.now());
		DBEvaluation.insertAssignment(assignment);
	}

}
