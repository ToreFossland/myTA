package gui.controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mockito.Mockito;

import database.DBBooking;
import gui.App;
import halltimes.Halltime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class SupervisorCreatesTimesController {

	// Handle CheckBoxes events
	// 20 checkbokserleses fra v->h

	@FXML
	Button button_confirm;

	@FXML
	CheckBox checkBox1;

	@FXML
	CheckBox checkBox2;

	@FXML
	CheckBox checkBox3;

	@FXML
	CheckBox checkBox4;

	@FXML
	CheckBox checkBox5;

	@FXML
	CheckBox checkBox6;

	@FXML
	CheckBox checkBox7;

	@FXML
	CheckBox checkBox8;

	@FXML
	CheckBox checkBox9;

	@FXML
	CheckBox checkBox10;

	@FXML
	CheckBox checkBox11;

	@FXML
	CheckBox checkBox12;

	@FXML
	CheckBox checkBox13;

	@FXML
	CheckBox checkBox14;

	@FXML
	CheckBox checkBox15;

	@FXML
	CheckBox checkBox16;

	@FXML
	CheckBox checkBox17;

	@FXML
	CheckBox checkBox18;

	@FXML
	CheckBox checkBox19;

	@FXML
	CheckBox checkBox20;

	@FXML
	ChoiceBox course_input;
	
	@FXML
	TextField course_input2;

	@FXML
	TextField week_input;

	@FXML
	TextField availablePlaces_input;
	
	@FXML
    public void initialize() {
		//Acts as user courses
		/*HashMap<String, Integer> mockMap = new HashMap<String, Integer>();
		
		mockMap.put("TDT4140", 3);
		mockMap.put("TDT4145", 3);
		mockMap.put("TMA2000", 3);
		*/
		//Requires that user is logged inn
		Map<String,Integer> coursesFromUser = App.getInstance().getLoggedUser().getMyCourses();
		List<String> courses = new ArrayList<String>();
		
		for (String course : coursesFromUser.keySet()) {
			courses.add(course);
		}
		
		//Populates choicebox with courses in which logged in user participates
        course_input.getItems().addAll(courses);
    }
	

	private ArrayList<Halltime> createHalltimes() {
		CheckBox[][] checkboxes = new CheckBox[][] { { checkBox1, checkBox2, checkBox3, checkBox4 },
			{ checkBox5, checkBox6, checkBox7, checkBox8 }, { checkBox9, checkBox10, checkBox11, checkBox12 },
			{ checkBox13, checkBox14, checkBox15, checkBox16 }, { checkBox17, checkBox18, checkBox19, checkBox20 } };
			
		ArrayList<Halltime> Halltimes = new ArrayList<Halltime>();
		LocalTime timeStart;
		LocalTime timeEnd;
		int week = Integer.parseInt(week_input.getText());
		int availablePlaces = Integer.parseInt(availablePlaces_input.getText());
		String course = ((String) course_input.getValue()).toUpperCase();
		for (int i = 0; i < checkboxes.length; i++) {
			for (int j = 0; j < checkboxes[i].length; j++) {
				if (checkboxes[i][j].isSelected()) {
					timeStart = LocalTime.of(8 + j * 2, 0, 0);
					timeEnd = LocalTime.of(10 + j * 2, 0, 0);
					Halltimes.add(new Halltime(course, week, i + 1, timeStart, timeEnd, availablePlaces));
				}
			}
		}

		return Halltimes;
	}

	public void returnHandler(ActionEvent event) {
		// TODO
	}

	public void handleCheckBox(ActionEvent event) {
		if (checkBox1.isSelected()) {
			System.out.println("Time is chosen.");
			// her skjer det noe: blir rød og tid velges ???
			// DBBooking.supervisorAddHalltime(halltime, interval);
		}
	}

	@FXML
	public void onClickConfirm(javafx.event.ActionEvent event) throws Exception {
		DBBooking.supervisorAddHalltime(createHalltimes(), 30);
		System.out.println("Halltimes added");
	}

}
