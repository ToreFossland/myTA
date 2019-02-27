/*
File: Example.java    
Date 	 	Author 	Changes
--------------------------------------------
Feb 25 19 	David 	Created
Feb 27 19   David	Added Joda
*/


package halltimes;

import java.time.LocalTime;

public class Halltime {
	
	private String courseCode;
	private int week;
	private int day;
	private LocalTime timeStart;
	private LocalTime timeEnd;
	private int availablePlaces;
	
	public Halltime(String courseCode, int week, int day, LocalTime timeStart, LocalTime timeEnd, int availablePlaces) {
		this.courseCode = courseCode;
		this.week = week;
		this.day = day;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.availablePlaces = availablePlaces;
	}
	
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
	public LocalTime getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(LocalTime timeStart) {
		this.timeStart = timeStart;
	}
	public LocalTime getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(LocalTime timeEnd) {
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
