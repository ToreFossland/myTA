package gui.controllers;

import evaluation.Assignment;
import evaluation.AssignmentInbox;
import evaluation.Evaluation;
import evaluation.EvaluationSender;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import user.User;

public class TAAddEvaluationPageController {
	
	@FXML
	Button button_confirm_score;
	
	@FXML
	TextField set_score;
	
	@FXML
	TextArea set_comment;
	
	@FXML
	Text student_email;
	
	@FXML
	Text assignment_name;
	
	@FXML
	Button button_return;
	
	@FXML
	Text text_response;
	
	@FXML
	Label open_file_label;
	
	@FXML
	Button download_button;
	
	Assignment selectedAssignment;
	
	public void returnHandler(javafx.event.ActionEvent event) {
		App.getInstance().gotoAssistantPage();
	}
	public void initialize() {
		selectedAssignment = AssignmentInbox.getSelectedAssignment();
		if(selectedAssignment.getFileName() != null)
		{
			download_button.setVisible(true);
			open_file_label.setText("File: " + selectedAssignment.getFileName());
			open_file_label.setVisible(true);
			
		}
		String email = selectedAssignment.getDeliveredBy().getEmail();
		String assName = selectedAssignment.getAssignmentName();
		student_email.setText(email);
		assignment_name.setText(assName);
	}
	
	public void downloadButtonHandler(javafx.event.ActionEvent event) {	
		selectedAssignment.downloadFile();
		selectedAssignment.openFile();
	}
	
	public void confirmHandler(javafx.event.ActionEvent event) throws Exception {
		String scoreText = set_score.getText();
		try {
			int score = Integer.parseInt(scoreText);
			if (score>=0 && score<=100) {
				Assignment selectedAssignment = AssignmentInbox.getSelectedAssignment();
				User evaluator = App.getInstance().getLoggedUser();
				if(set_comment.getText() != null) {
					String comment = set_comment.getText();
					Evaluation eval = new Evaluation(score,evaluator,selectedAssignment, comment);
					EvaluationSender.sendEvaluation(eval);
					App.getInstance().gotoTAViewEvaluationsPage();
				}
				else{
					Evaluation eval = new Evaluation(score,evaluator,selectedAssignment);
					EvaluationSender.sendEvaluation(eval);
					App.getInstance().gotoTAViewEvaluationsPage();
					App.getInstance().clearHistory();
				}
			
			}
			text_response.setText("Write a number between 0 and 100");
		}
		catch(NumberFormatException e) {
			text_response.setText("Write a number between 0 and 100");
		}
		
		
	}
	
	

}
