package gui.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import user.User;

public class LoginController {

	// Login page:
	@FXML 
	TextField user_input;

	@FXML
	TextField password_input;

	@FXML
	Button button_log;

	@FXML
	Button button_reg;

	@FXML
	Label invalid_username_label;

	@FXML
	Label wrong_combination_label;

	@FXML
	public void OnClickLog(javafx.event.ActionEvent event) throws Exception {
		if (isValidEmail(user_input.getText())) {
			String email = user_input.getText();
			if (App.getInstance().userLogin(email, password_input.getText()) && App.getInstance().isRole(email,3)) {
				App.getInstance().gotoSupervisorPage();
			} 
			else if (App.getInstance().userLogin(email, password_input.getText()) && App.getInstance().isRole(email,4)) {
				App.getInstance().gotoAdminPage();
				
			}
			else if (App.getInstance().userLogin(email, password_input.getText())){
				App.getInstance().gotoStudentPage();
				
			}
				
			else {
				wrong_combination_label.setVisible(true);
			}
		}
	}
	
	/*@FXML
	public void gotoPage(String path) throws Exception {
		Parent blah = FXMLLoader.load(getClass().getResource(path));
		Scene scene = new Scene(blah);
	}*/

	@FXML
	public void RegButtonHandler(javafx.event.ActionEvent event) throws Exception {
		// Switches to registerpage.fxml
		App.getInstance().gotoRegistration();;
	}

	public void listeners() {
		user_input.textProperty().addListener((obs, oldText, newText) -> {

		});
	}

	public void checkValidUsernameOrEmailOnInput() {
		String input = user_input.getText();
		invalid_username_label.setVisible(!isValidUsernameOrEmail(input));
	}

	private boolean isValidUsernameOrEmail(String email) {
		// matches emails that ends with ntnu.no and has no errounous dots. Returns true
		// if match
		Pattern pattern = Pattern.compile(
				"^(?!\\.)(?!.*\\.$)(?!.*?\\.\\.)([a-zA-Z0-9_.+-])+((@(?!\\.)([a-zA-Z0-9_.+-]+)\\.|@)+ntnu\\.no)?$");
		Matcher matcher = pattern.matcher(email);

		return matcher.find();
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

