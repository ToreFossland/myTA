package gui.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		if (isValidUsernameOrEmail(user_input.getText())) {
			String username = user_input.getText().split("@")[0];
			if (Program.login(username, password_input.getText())) {
				Parent blah = FXMLLoader.load(getClass().getResource("../pages/GenericPage.fxml"));
				Scene scene = new Scene(blah);
				Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				appStage.setScene(scene);
				appStage.show();
			} else {
				wrong_combination_label.setVisible(true);
			}
		}
	}

	@FXML
	public void RegButtonHandler(javafx.event.ActionEvent event) throws Exception {
		// Switches to registerpage.fxml
		Parent blah = FXMLLoader.load(getClass().getResource("../pages/RegisterPage.fxml"));
		Scene scene = new Scene(blah);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.show();
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
}