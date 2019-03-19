package gui.controllers;

import gui.App;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class MyCalendarController {
	
	@FXML
	HBox box1;
	
	@FXML
	public void onClickGoToPrevious() {
		App.getInstance().gotoPrevious();
	}
	

}
