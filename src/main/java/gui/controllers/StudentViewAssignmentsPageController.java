package gui.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import evaluation.Assignment;
import evaluation.AssignmentInbox;
import evaluation.Evaluation;
import evaluation.EvaluationInbox;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentViewAssignmentsPageController {
	
	@FXML
	TableView<Assignment> messageTable;
	
	@FXML
	TableColumn<Assignment, String> assignmentCol;
	
	@FXML
	TableColumn<Assignment, String> scoreCol;

	
	@FXML
	ChoiceBox<String> course_input;
	
	@FXML
	Button return_handler;
	
	EvaluationInbox inbox;
	
	@FXML
	public void initialize() {
		List<String> relevantCourses = findCoursesWhereStudent();
		course_input.getItems().addAll(relevantCourses);
		
		loadAssignments();
	}
	
	private void loadAssignments() {
		assignmentCol.setCellValueFactory(new PropertyValueFactory<>("senderEmail"));
		scoreCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
		
		inbox = new EvaluationInbox(course_input.getValue());
		ArrayList<Evaluation> evaluations = AssignmentInbox.getEvaluations(course_input.getValue());
		
		Logger.getLogger(App.class.getName()).log(Level.INFO, "Evaluations downloaded");
		
		evaluations = getEvaluationsWhereStudent(evaluations);
		Collections.sort(messages);
		
		messageTable.getItems().addAll(messages);

	}
	
	private ArrayList<Evaluation> getEvaluationsWhereStudent(ArrayList<Evaluation> evaluations) {
		ArrayList<Evaluation> newList = new ArrayList<Evaluation>();
		for (Evaluation evaluation : evaluations)
			if (!evaluation.getEvaluator().getEmail().equals(App.getInstance().getLoggedUser().getEmail()))
				newList.add(evaluation);
		return newList;
	}

	
	
	
	private List<String> findCoursesWhereStudent() {
		Map<String, Integer> allCourses = App.getInstance().getLoggedUser().getMyCourses();
		List<String> relevantCourses = new ArrayList<String>();
		for (Entry<String, Integer> entry : allCourses.entrySet()) {
			String course = entry.getKey();
			Integer role = entry.getValue();
			if (role == 1)
				relevantCourses.add(course);
		}
		return relevantCourses;
	}
	
	public void returnHandler(javafx.event.ActionEvent event) {
		//TODO: App.getInstance().gotoPrevious();
	}

}
