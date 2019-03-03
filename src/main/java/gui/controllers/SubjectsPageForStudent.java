package gui.controllers;

import database.DBConnection;
import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SubjectsPageForStudent {
 
	@FXML
	TextField add_subjectCode_input;
	
	@FXML
	Button button_add_subject;
	
	@FXML
	Label subject_response;
	
	@FXML
	Button button_return;
	
	//valid om faget  eksisterer
		public boolean isValidSubject(String subject) throws Exception {
			boolean valid = false;
			if (DBConnection.fagEksisterer(subject)){
				valid=true;
			}
			return valid;
		}
		
		public void onClickAddSubject(javafx.event.ActionEvent event) throws Exception {
			if (isValidSubject(add_subjectCode_input.getText())) {
				String new_subject = add_subjectCode_input.getText();
				String email = App.getInstance().getLoggedUser().getEmail();
				DBConnection.leggTilUserHarCourse(email, new_subject, 1);	
				subject_response.setText("Course added");
			}
			else {
				subject_response.setText("Course does not exist");
			}
		}
		
		public void returnHandler(javafx.event.ActionEvent event) throws Exception {
			App.getInstance().gotoStudentPage();;
		}
}
