package gui.controllers;

import java.util.ArrayList;
import java.util.Collections;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentViewAssignmentsPageController {
/*
	@FXML
	TableView<Evaluation> evaluationTable;

	@FXML
	TableColumn<Evaluation, String> assignmentCol;

	@FXML
	TableColumn<Evaluation, String> scoreCol;

	@FXML
	ChoiceBox<String> course_input;

	@FXML
	Button return_handler;

	EvaluationInbox inbox;

	@FXML
	public void initialize() {
		List<String> relevantCourses = findCoursesWhereStudent();
		if (!relevantCourses.isEmpty()) {
			course_input.getItems().addAll(relevantCourses);
			course_input.setValue(relevantCourses.get(0));
			loadAssignments();
		}
	}

	private void loadAssignments() {
		if (!course_input.getValue().isBlank()) {
			assignmentCol.setCellValueFactory(new PropertyValueFactory<>("assignmentName"));
			scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

			inbox = new EvaluationInbox(course_input.getValue());
			ArrayList<Evaluation> evaluations = inbox.getEvaluations();

			Logger.getLogger(App.class.getName()).log(Level.INFO, "Evaluations downloaded");

			evaluations = getEvaluationsWhereStudent(evaluations);
			Collections.sort(evaluations);

			evaluationTable.getItems().addAll(evaluations);
		}

	}
	
	public void courseInputHandler(ActionEvent event) {
		loadAssignments();
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
		// TODO: App.getInstance().gotoPrevious();
	}*/
	
	@FXML
	ComboBox<String> chooseSubject;
	
	
	@FXML
	TableView<Evaluation> evaluationTable;
	
	@FXML
	TableColumn<Evaluation, String> assignmentCol;
	
	@FXML
	TableColumn<Evaluation, Integer> scoreCol;
	
	
	@FXML
	Button button_return;
	
	String chosenSubject;
	
	EvaluationInbox inbox;
	
	@FXML
	public void initialize() {
		//Fills choicebox
		Map<String, Integer> allCourses = App.getInstance().getLoggedUser().getMyCourses();
		List<String> relevantCourses = new ArrayList<String>();
		for (Entry<String, Integer> entry : allCourses.entrySet()) {
			String course = entry.getKey();
			Integer role = entry.getValue();
			if (role == 1)
				relevantCourses.add(course);
		}
		chooseSubject.getItems().addAll(relevantCourses);
	}
	
	public void onSubjectChoice() {
		chosenSubject = chooseSubject.getValue();
		updateTable();
	}
	@FXML
	public void updateTable() {
		evaluationTable.getItems().clear();
		assignmentCol.setCellValueFactory(new PropertyValueFactory<>("assignmentName"));
		scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
		
		inbox = new EvaluationInbox(chosenSubject);
		ArrayList<Evaluation> evaluations = inbox.getEvaluations();
		
		evaluationTable.getItems().addAll(evaluations);
	}

	
	@FXML
	public void returnHandler(javafx.event.ActionEvent event) throws Exception {
		//App.getInstance().gotoPrevious();
	}

}
