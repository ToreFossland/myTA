package gui.controllers;

import java.net.URL;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class FrontPageController {
	@FXML
	Button button_go_to_login;
	
	@FXML
	ImageView front_page_image;
	
	
	
	public void goToLoginHandler() {
		App.getInstance().gotoLogin();
	}
	
	
	
}
