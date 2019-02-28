package halltimes;

import java.time.LocalTime;

import gui.App;
import user.User;

public class BookingTA {
	
	private String emailTA;
	private String courseCode;
	private LocalTime startTime;
	private String emailStudent;

	public BookingTA(Halltime halltime, User TA) {
		this.emailTA = TA.getEmail();
		this.setStartTime(halltime.getTimeStart());
		this.courseCode = halltime.getCourseCode();
	}
	
	public BookingTA(Halltime halltime, User TA, User student) {
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
	
}
