package evaluation;

import java.time.LocalDateTime;

import database.DBEvaluation;
import user.User;

public class Assignment{
	private int id;
	private User deliveredBy; //Satser på at bare mail holder, kanskje trenger helt User object? Samme på eval
	private String courseCode;
	private String assigmentName;
	//String file kanskje være med i neste sprint?
	private LocalDateTime timestamp;
	
	public Assignment(User deliveredBy, String courseCode, String assignmentName, LocalDateTime timestamp){
		this.setDeliveredBy(deliveredBy);
		this.setCourseCode(courseCode);
		this.setAssigmentName(assignmentName);
		this.setTimestamp(timestamp);
		this.setId(-1);
	}
	
	//Konstruktør med fil her
	
	public void updateId() {
		DBEvaluation.updateAssignment(this);
	}

	public User getDeliveredBy() {
		return deliveredBy;
	}

	public void setDeliveredBy(User deliveredBy) {
		this.deliveredBy = deliveredBy;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getAssigmentName() {
		return assigmentName;
	}

	public void setAssigmentName(String assigmentName) {
		this.assigmentName = assigmentName;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}