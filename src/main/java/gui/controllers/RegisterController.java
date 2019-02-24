package gui.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Program;

public class RegisterController {

	@FXML
	TextField first_name_input;

	@FXML
	TextField last_name_input;

	@FXML
	TextField email_input;

	@FXML
	Label email_invalid_label;

	@FXML
	TextField password_input;

	@FXML
	TextField retype_password_input;

	@FXML
	Label passwords_not_same_label;

	@FXML
	Button confirm_registration;

	@FXML
	Label regbutton_label;

	@FXML
	public void ConfirmButtonHandler(javafx.event.ActionEvent event) throws Exception {

		// Checks if username and password is valid. Sets to null if not.
		String username = isValidEmail(email_input.getText()) ? email_input.getText().split("@")[0] : null;
		String password = password_input.getText().equals(retype_password_input.getText()) ? password_input.getText()
				: null;

		Boolean validEmail = username != null;
		Boolean validPassword = password != null;
		Boolean validName = isValidName(first_name_input.getText()) && isValidName(last_name_input.getText());

		Boolean allInputsValid = true;
		String label_output = "Invalid input";
		if (!validEmail) {
			label_output = "Invalid email";
			allInputsValid = false;
		} else if (!validPassword) {
			label_output = "Passwords not matching";
			allInputsValid = false;
		} else if (!validName) {
			label_output = "Invalid name";
			allInputsValid = false;
		} else if (DBConnection.userExists(username)) {
			label_output = "User already exists";
			allInputsValid = false;
		}
		regbutton_label.setText(label_output);

		if (allInputsValid) {
			Program.register(email_input.getText(), password, username, first_name_input.getText(),
					last_name_input.getText(), true);
			Parent blah = FXMLLoader.load(getClass().getResource("../pages/GenericPage.fxml"));
			Scene scene = new Scene(blah);
			Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			appStage.setScene(scene);
			appStage.show();
		} else {
			regbutton_label.setVisible(true);
		}

		Boolean userExists = username != null ? DBConnection.userExists(username) : false;
	}

	public void listeners() {
		email_input.textProperty().addListener((obs, oldText, newText) -> {

		});

		retype_password_input.textProperty().addListener((obs, oldText, newText) -> {
		});
	}

	public void checkValidEmailOnInput() {
		String input = email_input.getText();
		email_invalid_label.setVisible(!isValidEmail(input));
	}

	public void checkSamePassword() {
		if (password_input.getText() != null || retype_password_input.getText() != null) {
			passwords_not_same_label.setVisible(!password_input.getText().equals(retype_password_input.getText()));
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

	private boolean isValidName(String input) {
		Pattern pattern = Pattern.compile("^[\\w'\\-,.][^0-9_!¡?÷?¿/\\\\+=@£$|\\[\\]#$%ˆ&*()¤~§{}|~<>;:]{1,}$");
		Matcher matcher = pattern.matcher(input);

		return matcher.find();
	}

}