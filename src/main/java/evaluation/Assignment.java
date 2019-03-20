package evaluation;

import java.io.File;
import java.time.LocalDateTime;

import database.DBEvaluation;
import database.DBFile;
import user.User;

public class Assignment{
	private int id;
	private User deliveredBy; //Satser p� at bare mail holder, kanskje trenger helt User object? Samme p� eval
	private String courseCode;
	private String assignmentName;
	private Boolean hasFileInDB;
	private File file;
	private LocalDateTime timestamp;
	
	public Assignment(User deliveredBy, String courseCode, String assignmentName, LocalDateTime timestamp){
		this(deliveredBy,courseCode,assignmentName,timestamp,null);
	}
	
	public Assignment(User deliveredBy, String courseCode, String assignmentName, LocalDateTime timestamp, File file){
		this.setDeliveredBy(deliveredBy);
		this.setCourseCode(courseCode);
		this.setAssignmentName(assignmentName);
		this.setTimestamp(timestamp);
		this.setId(-1);
		this.setFile(file);
	}
	
	public void downloadFile() {
		//DBFile.downloadFile(this, filepath);
	}
	
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Boolean getHasFileInDB() {
		return hasFileInDB;
	}

	public void setHasFileInDB(Boolean hasFileInDB) {
		this.hasFileInDB = hasFileInDB;
	}
}