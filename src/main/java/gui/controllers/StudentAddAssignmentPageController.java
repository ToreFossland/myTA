package gui.controllers;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import database.DBEvaluation;
import evaluation.Assignment;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class StudentAddAssignmentPageController {
	
	@FXML
	Button return_button;
	
	@FXML
	TextField user_input;
	
	@FXML
	Button choose_file;
	
	@FXML
	Label file_name;
	
	@FXML
	Label response_label;
	
	@FXML
	ChoiceBox<String> course_input;
	
	File file;
	
	public void initialize() {
		file = null;
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
	
	public void chooseFileHandler(javafx.event.ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(App.getInstance().getStage());
        if (file != null)
        	file_name.setText(file.getName());
	}
	
	public void uploadHandler(javafx.event.ActionEvent event) {
		Assignment assignment = new Assignment(App.getInstance().getLoggedUser(), course_input.getValue(), user_input.getText(), LocalDateTime.now(), file);
		if(assignment.getFile().length() > 16777215)
			response_label.setText("File size cannot exceed 16 MB");
		else {
			try {
				DBEvaluation.insertAssignment(assignment);
				response_label.setText("Assignment inserted!");
			} catch(Exception e) {
				response_label.setText("Adding assignment failed");
			}
		}
		
		response_label.setVisible(true);
	}

}
