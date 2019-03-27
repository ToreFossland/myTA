package halltimes;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.ArrayList;

import user.User;

public class Booking implements Comparable<Booking>{

	private String emailTA;
	private String courseCode;
	private int week;
	private int day;
	private LocalTime startTime;
	private LocalTime endTime;
	private String emailStudent;

	public Booking(Halltime halltime, String emailTA) {
		this.emailTA = emailTA;
		this.setStartTime(halltime.getTimeStart());
		this.setEndTime(halltime.getTimeEnd());
		this.setWeek(halltime.getWeek());
		this.setDay(halltime.getDay());
		this.courseCode = halltime.getCourseCode();
	}
	
	public Booking(Halltime halltime) {
		this(halltime, (String) null);
	}

	public Booking(Halltime halltime, User TA) {
		this(halltime, TA.getEmail());
	}

	public Booking(Halltime halltime, String emailTA, String emailStudent) {
		this(halltime, emailTA);
		this.emailStudent = emailStudent;
	}

	public Booking(Halltime halltime, User TA, User student) {
		this(halltime, TA);
		this.emailStudent = student.getEmail();
	}

	// Interval: minutes
	public ArrayList<Booking> splitIntoMultipleBookings(int interval) {
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		LocalTime tempStartTime = getStartTime();

		if (startTime.until(endTime, MINUTES) % interval != 0)
			throw new IllegalArgumentException("Timespan not divisible by interval");
		else {
			// General statement for inserting halltime if it doesn't already exist

			while (endTime.isAfter(tempStartTime.plusMinutes(interval - 1))) {
				// Specify question marks and add insert to the batch
				bookings.add(new Booking(new Halltime(courseCode, week, day, tempStartTime, tempStartTime.plusMinutes(interval), 0), emailTA, emailStudent));
				tempStartTime = tempStartTime.plusMinutes(interval);
			}
		}
		return bookings;
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

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public int compareTo(Booking o) {
		if(this.getCourseCode().equals(o.getCourseCode()) && this.getDay() == o.getDay() &&
				this.getWeek() == o.getWeek() 
				&& this.getStartTime().equals(o.getStartTime())) {
			return 1;
		}
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public String toString() {
		return String.format("\n{TA: %s, Student: %s, Course: %s, Week: %s, Day: %s}", getEmailTA(), getEmailStudent(), getCourseCode(), getWeek(), getDay());
	}
	
}
