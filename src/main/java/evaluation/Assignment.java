package evaluation;

import java.time.LocalDateTime;

import database.DBEvaluation;
import user.User;

public class Assignment{
	private int id;
	private User deliveredBy; //Satser p� at bare mail holder, kanskje trenger helt User object? Samme p� eval
	private String courseCode;
	private String assignmentName;
	//String file kanskje v�re med i neste sprint?
	private LocalDateTime timestamp;
	
	public Assignment(User deliveredBy, String courseCode, String assignmentName, LocalDateTime timestamp){
		this.setDeliveredBy(deliveredBy);
		this.setCourseCode(courseCode);
		this.setAssignmentName(assignmentName);
		this.setTimestamp(timestamp);
		this.setId(-1);
	}
	
	//Konstrukt�r med fil her
	
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

	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
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