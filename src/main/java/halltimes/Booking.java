package halltimes;

import java.time.LocalTime;

import gui.App;
import user.User;

public class Booking {
	
	private String emailTA;
	private String courseCode;
	private int week;
	private int day;
	private LocalTime startTime;
	private String emailStudent;

	public Booking(Halltime halltime, User TA) {
		this.emailTA = TA.getEmail();
		this.setStartTime(halltime.getTimeStart());
		this.setWeek(halltime.getWeek());
		this.setDay(halltime.getDay());
		this.courseCode = halltime.getCourseCode();
	}
	
	public Booking(Halltime halltime, String emailTA) {
		this.emailTA = emailTA;
		this.setStartTime(halltime.getTimeStart());
		this.setWeek(halltime.getWeek());
		this.setDay(halltime.getDay());
		this.courseCode = halltime.getCourseCode();
	}
	
	public Booking(Halltime halltime, String emailTA, String emailStudent) {
		this(halltime,emailTA);
		this.emailStudent = emailStudent;		
	}
	
	public Booking(Halltime halltime, User TA, User student) {
		this(halltime,TA);
		this.emailStudent = student.getEmail();		
	}
	
	public String getEmailTA() {
		return emailTA;
	}
	
	public void setEmailTA(String emailTA) {
		this.emailTA = emailTA;
	}
	
	
	public String getEmailStudent() {
		return emailStudent;
	}
	
	public void setEmailStudent(String emailStudent) {
		this.emailStudent = emailStudent;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}
	
}
