package gui.controllers;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import database.DBBooking;
import gui.App;
import halltimes.Booking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MyCalendarController {

	//Hbox
	@FXML
	HBox box1;
	@FXML
	HBox box2;
	
	@FXML
	HBox box3;
	
	@FXML
	HBox box4;
	
	@FXML
	HBox box5;
	
	@FXML
	HBox box6;
	
	@FXML
	HBox box7;
	
	@FXML
	HBox box8;
	
	@FXML
	HBox box9;
	
	@FXML
	HBox box10;
	
	@FXML
	HBox box11;
	
	@FXML
	HBox box12;
	
	@FXML
	HBox box13;
	
	@FXML
	HBox box14;
	
	@FXML
	HBox box15;
	
	@FXML
	HBox box16;
	
	@FXML
	HBox box17;
	
	@FXML
	HBox box18;
	
	@FXML
	HBox box19;
	
	@FXML
	HBox box20;
	
	@FXML
	HBox box21;
	
	@FXML
	HBox box22;
	
	@FXML
	HBox box23;
	
	@FXML
	HBox box24;
	
	@FXML
	HBox box25;
	
	@FXML
	HBox box26;
	
	@FXML
	HBox box27;
	
	@FXML
	HBox box28;
	
	@FXML
	HBox box29;
	
	@FXML
	HBox box30;
	
	@FXML
	HBox box31;
	
	@FXML
	HBox box32;
	
	@FXML
	HBox box33;
	
	@FXML
	HBox box34;
	
	@FXML
	HBox box35;
	
	@FXML
	HBox box36;
	
	@FXML
	HBox box37;
	
	@FXML
	HBox box38;
	
	@FXML
	HBox box39;
	
	@FXML
	HBox box40;
	
	@FXML
	HBox box41;
	
	@FXML
	HBox box42;
	
	@FXML
	HBox box43;
	
	@FXML
	HBox box44;
	
	@FXML
	HBox box45;
	
	@FXML
	HBox box46;
	
	@FXML
	HBox box47;
	
	@FXML
	HBox box48;
	
	@FXML
	HBox box49;
	
	@FXML
	HBox box50;
	
	@FXML
	HBox box51;
	
	@FXML
	HBox box52;
	
	@FXML
	HBox box53;
	
	@FXML
	HBox box54;
	
	@FXML
	HBox box55;
	
	@FXML
	HBox box56;
	
	@FXML
	HBox box57;
	
	@FXML
	HBox box58;
	
	@FXML
	HBox box59;
	
	@FXML
	HBox box60;
	
	@FXML
	HBox box61;
	
	@FXML
	HBox box62;
	
	@FXML
	HBox box63;
	
	@FXML
	HBox box64;
	
	@FXML
	HBox box65;
	
	@FXML
	HBox box66;
	
	@FXML
	HBox box67;
	
	@FXML
	HBox box68;
	
	@FXML
	HBox box69;
	
	@FXML
	HBox box70;
	
	@FXML
	HBox box71;
	
	@FXML
	HBox box72;
	
	@FXML
	HBox box73;
	
	@FXML
	HBox box74;
	
	@FXML
	HBox box75;
	
	@FXML
	HBox box76;
	
	@FXML
	HBox box77;
	
	@FXML
	HBox box78;
	
	@FXML
	HBox box79;
	
	@FXML
	HBox box80;
	
	@FXML
	Text text1;
	
	@FXML
	Text text2;
	
	@FXML
	Text text3;
	
	@FXML
	Text text4;
	
	@FXML
	Text text5;
	
	@FXML
	Text text6;
	
	@FXML
	Text text7;
	
	@FXML
	Text text8;
	
	@FXML
	Text text9;
	
	@FXML
	Text text10;
	
	@FXML
	Text text11;
	
	@FXML
	Text text12;
	
	@FXML
	Text text13;
	
	@FXML
	Text text14;
	
	@FXML
	Text text15;
	
	@FXML
	Text text16;
	
	@FXML
	Text text17;
	
	@FXML
	Text text18;
	
	@FXML
	Text text19;
	
	@FXML
	Text text20;
	
	@FXML
	Text text21;
	
	@FXML
	Text text22;
	
	@FXML
	Text text23;
	
	@FXML
	Text text24;
	
	@FXML
	Text text25;
	
	@FXML
	Text text26;
	
	@FXML
	Text text27;
	
	@FXML
	Text text28;
	
	@FXML
	Text text29;
	
	@FXML
	Text text30;
	
	@FXML
	Text text31;
	
	@FXML
	Text text32;
	
	@FXML
	Text text33;
	
	@FXML
	Text text34;
	
	@FXML
	Text text35;
	
	@FXML
	Text text36;
	
	@FXML
	Text text37;
	
	@FXML
	Text text38;
	
	@FXML
	Text text39;
	
	@FXML
	Text text40;
	
	@FXML
	Text text41;
	
	@FXML
	Text text42;
	
	@FXML
	Text text43;
	
	@FXML
	Text text44;
	
	@FXML
	Text text45;
	
	@FXML
	Text text46;
	
	@FXML
	Text text47;
	
	@FXML
	Text text48;
	
	@FXML
	Text text49;
	
	@FXML
	Text text50;
	
	@FXML
	Text text51;
	
	@FXML
	Text text52;
	
	@FXML
	Text text53;
	
	@FXML
	Text text54;
	
	@FXML
	Text text55;
	
	@FXML
	Text text56;
	
	@FXML
	Text text57;
	
	@FXML
	Text text58;
	
	@FXML
	Text text59;
	
	@FXML
	Text text60;
	
	@FXML
	Text text61;
	
	@FXML
	Text text62;
	
	@FXML
	Text text63;
	
	@FXML
	Text text64;
	
	@FXML
	Text text65;
	
	@FXML
	Text text66;
	
	@FXML
	Text text67;
	
	@FXML
	Text text68;
	
	@FXML
	Text text69;
	
	@FXML
	Text text70;
	
	@FXML
	Text text71;
	
	@FXML
	Text text72;
	
	@FXML
	Text text73;
	
	@FXML
	Text text74;
	
	@FXML
	Text text75;
	
	@FXML
	Text text76;
	
	@FXML
	Text text77;
	
	@FXML
	Text text78;
	
	@FXML
	Text text79;
	
	@FXML
	Text text80;
	
	@FXML
	ComboBox<Integer> week_input;
	
	@FXML
	Button button_return;
	
	HBox[][] boxes;
	Text[][] text;
	
	static final int BOOKINGLENGTH = 30;
	List<Booking> studentBookings;
	List<Booking> taBookings;
	
	private static Booking chosenBooking;
	@FXML
	public void initialize() {
		
		
		HBox[][] boxesInitialized = {
			{
				box1, box2, box3, box4, box5, box6, box7, box8, box9, box10, box11,
				box12,box13,box14,box15,box16},
			
			{   box17, box18,box19,box20,box21,box22,box23,box24,box25,
				box26, box27,box28,box29,box30,box31,box32},
			{
				box33,box34,box35,box36,box37,box38,box39,box40,box41,box42,
				box43, box44, box45, box46, box47, box48},
			{
				box49,box50,box51,box52,box53,box54,box55,box56,box57,box58,box59,box60,
				box61,box62,box63,box64},
			{
				box65,box66,box67,box68,box69,box70,box71,box72,box73,box74,box75,box76,
				box77,box78,box79,box80}
				
			};
		
		Text[][] textInitialized = {
				{
					text1,text2,text3,text4,text5,text6,text7,text8,text9,
					text10,text11,text12,text13,text14,text15,text16},
				{
					text17,text18,text19,text20,text21,text22,text23,text24,
					text25,text26,text27,text28,text29,text30,text31,text32},
				{
					text33,text34,text35,text36,text37,text38,text39,text40,
					text41,text42,text43,text44,text45,text46,text47,text48},
				{
					text49,text50,text51,text52,text53,text54,text55,text56,text57,
					text58,text59,text60,text61,text62,text63,text64},
				{   
					text65,text66,text67,text68,text69,text70,text71,text72,
					text73,text74,text75,text76,text77,text78,text79,text80}
				
				};
		
		boxes = boxesInitialized;
		text = textInitialized;
		//Fiks denne
		List<Integer> weeks = new ArrayList<Integer>();
		int currentWeek = DBBooking.getCurrentWeek();
		DBBooking.refreshBookingWeeks(App.getInstance().getLoggedUser());
		for (Integer week : DBBooking.getWeeksStudent()) {
			if(week >= currentWeek)
				weeks.add(week);
		}
		
		for (Booking booking : DBBooking.getBookingsTA()) {
			if(booking.getEmailStudent() != null && booking.getWeek() >= currentWeek && !weeks.contains(booking.getWeek()))
				weeks.add(booking.getWeek());
		}
		
		Collections.sort(weeks);
		week_input.getItems().addAll(weeks);
	
		if(weeks.size() > 0) {
			week_input.setValue(weeks.get(0));
			loadCalendar();
		}
	}
		
	
	public void loadCalendar() {
		studentBookings = DBBooking.getBookingsStudent();
		taBookings = getBookingsTA();
		DBBooking.refreshBookingWeeks(App.getInstance().getLoggedUser());
		emptyCalendar();
		int week = week_input.getValue();
		int bookingNo=0;
		int index =0;
		for(Booking booking : studentBookings) {
			if (booking.getWeek()==week) {
				bookingNo = (booking.getStartTime().getHour() -8) * 2 + booking.getStartTime().getMinute() / BOOKINGLENGTH; 
				boxes[booking.getDay() - 1][bookingNo].setStyle("-fx-background-color: #FF76AD");
				boxes[booking.getDay() -1][bookingNo].setUserData(("s"+index));
				text[booking.getDay() - 1][bookingNo].setText(booking.getCourseCode());
			}
			index++; 
		}
		index=0;
		for(Booking booking : taBookings) {
			if (booking.getWeek()==week) {
				bookingNo = (booking.getStartTime().getHour() -8) * 2 + booking.getStartTime().getMinute() / BOOKINGLENGTH; 
				boxes[booking.getDay() - 1][bookingNo].setStyle("-fx-background-color: #FFC550");
				//boxes[booking.getDay() -1][bookingNo].setUserData(booking);
				boxes[booking.getDay() -1][bookingNo].setUserData("t"+index);
				text[booking.getDay() - 1][bookingNo].setText(booking.getCourseCode());
			}
			index++;
		}
		
	}
	public void emptyCalendar() {
		for (int i = 0; i < boxes.length; i++) {
			for (int j = 0; j < boxes[i].length; j++) {
				boxes[i][j].setStyle(null);
				text[i][j].setText(null);
			}
		}
	}
	public void weekInputHandler(ActionEvent event) {
		loadCalendar();
		}
		
	
	public static Booking getChosenBooking() {
		return chosenBooking;
	}
	public void setChosenBooking(Booking chosen) {
		chosenBooking = chosen;
	}
	
	public void onClickGetInfo(MouseEvent event) {
		String box = event.getSource().toString();

		int boxnummer=0;
		if (box.length()==13) {
			String numberasstring = Character.toString(box.charAt(11));
			boxnummer = Integer.parseInt(numberasstring);
		}
		else if (box.length()==14) {
			boxnummer = Integer.valueOf(box.substring(11, 13));
		}
		
		System.out.println("Boxnummer= "+ boxnummer);
		
		int i=0;
		int j=0;
		if (boxnummer<=16) {
			i=0;
			j=boxnummer-1;
		}
		else if (boxnummer>16 && boxnummer<=32) {
			i=1;
			j=boxnummer-17;
		}
		else if (boxnummer>32 && boxnummer<=48) {
			i=2;
			j=boxnummer-33;
		}
		else if (boxnummer>48 && boxnummer<=64) {
			i=3;
			j=boxnummer-49;
		}
		else {
			i=4;
			j=boxnummer-65;
		}
		
		if(boxes[i][j].getUserData()!=null) {
			System.out.println(boxes[i][j].getUserData().toString());
			String boxdata = boxes[i][j].getUserData().toString();
			System.out.println(boxdata.charAt(0));
			if(boxdata.charAt(0)=='s') {
				int index = Integer.valueOf(boxdata.substring(1));
				Booking chosen = studentBookings.get(index);
				setChosenBooking(chosen);
				App.getInstance().gotoBookingInfoStudent();
				
			}
			else if(boxdata.charAt(0)=='t') {
				int index= Integer.valueOf(boxdata.substring(1));
				Booking chosen = taBookings.get(index);
				setChosenBooking(chosen);
				App.getInstance().gotoBookingInfoTA();
				
			}
			
		}
		
		
	}
	
	@FXML
	private List<Booking> getBookingsTA(){
		List<Booking> temp = new ArrayList<Booking>();
		
		for (Booking booking : DBBooking.getBookingsTA()) {
			if(booking.getEmailStudent() != null)
				temp.add(booking);
		}
		Collections.sort(temp);
		
		/*System.out.println(taBookings);
		for (Booking booking : temp) {
			System.out.println(booking.getEmailStudent());
			if(booking.getEmailStudent()!=null) {
				taBookings.add(booking);
				System.out.println("ok");
			}
		}
		System.out.println(taBookings);*/
		return temp;
		
	}
	
	public void returnHandler() {
		App.getInstance().gotoPrevious();
	}
	

}
