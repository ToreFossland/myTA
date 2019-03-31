package gui.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import evaluation.Evaluation;
import evaluation.EvaluationInbox;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentViewAssignmentsPageController {
	
	@FXML
	ComboBox<String> chooseSubject;
	
	
	@FXML
	TableView<Evaluation> evaluationTable;
	
	@FXML
	TableColumn<Evaluation, String> assignmentCol;
	
	@FXML
	TableColumn<Evaluation, Integer> scoreCol;
	
	@FXML
	TableColumn<Evaluation, String> noteCol;
	
	
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
		noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
		
		inbox = new EvaluationInbox(chosenSubject);
		ArrayList<Evaluation> evaluations = inbox.getEvaluations();
		
		evaluationTable.getItems().addAll(evaluations);
	}

	
	@FXML
	public void returnHandler(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoPrevious();
	}

}
