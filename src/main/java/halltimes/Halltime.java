/*
File: Example.java    
Date 	 	Author 	Changes
--------------------------------------------
Feb 25 19 	David 	Created
*/


package halltimes;

import java.sql.Time;

public class Halltime {
	
	private String courseCode;
	private int week;
	private int day;
	private Time timeStart;
	private Time timeEnd;
	private int availablePlaces;
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public Time getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}
	public Time getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
	}
	public int getAvailablePlaces() {
		return availablePlaces;
	}
	public void setAvailablePlaces(int availablePlaces) {
		this.availablePlaces = availablePlaces;
	}
	//public static void assignHalltime(String courseCode, )
}
