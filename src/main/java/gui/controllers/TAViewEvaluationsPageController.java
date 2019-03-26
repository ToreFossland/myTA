package gui.controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;


import evaluation.Assignment;
import evaluation.AssignmentInbox;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TAViewEvaluationsPageController {
	@FXML
	ComboBox<String> chooseSubject;
	
	
	@FXML
	TableView<Assignment> assignmentTable;
	
	@FXML
	TableColumn<Assignment, String> assCol;
	
	@FXML
	TableColumn<Assignment, String> delbyCol;
	
	@FXML
	TableColumn<Assignment, String> timeCol;
	
	
	@FXML
	Button button_return;
	
	String chosenSubject;
	
	@FXML
	public void initialize() {
		//Fills choicebox
		Map<String, Integer> allCourses = App.getInstance().getLoggedUser().getMyCourses();
		List<String> relevantCourses = new ArrayList<String>();
		for (Entry<String, Integer> entry : allCourses.entrySet()) {
			String course = entry.getKey();
			Integer role = entry.getValue();
			if (role == 2)
				relevantCourses.add(course);
		}
		chooseSubject.getItems().addAll(relevantCourses);
		if(!relevantCourses.isEmpty()) {
			chooseSubject.setValue(relevantCourses.get(0));;
		}
	}
	
	public void onSubjectChoice() {
		chosenSubject = chooseSubject.getValue();
		updateTable();
	}
	@FXML
	public void updateTable() {
		assignmentTable.getItems().clear();
		assCol.setCellValueFactory(new PropertyValueFactory<>("assignmentName"));
		delbyCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
		timeCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
		
		
		ArrayList<Assignment> assignments = AssignmentInbox.getAssignments(chosenSubject);
		
		assignmentTable.getItems().addAll(assignments);
	}

	
	public void onClickChooseAssignment() {
		Assignment selectedAssignment = assignmentTable.getSelectionModel().getSelectedItem();
		AssignmentInbox.setSelectedAssignment(selectedAssignment);
		if (selectedAssignment !=null)
			App.getInstance().gotoTAAddEvaluationPage();
	}
	
	
	@FXML
	public void returnHandler(javafx.event.ActionEvent event) throws Exception {
		App.getInstance().gotoPrevious();
	}
}
