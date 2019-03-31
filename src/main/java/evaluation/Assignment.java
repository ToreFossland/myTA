
package evaluation;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import database.DBEvaluation;
import database.DBFile;
import user.User;

public class Assignment{
	private int id;
	private User deliveredBy; //Satser på at bare mail holder, kanskje trenger helt User object? Samme på eval
	private String courseCode;
	private String assignmentName;
	private File file;
	//File name on DB. File variable above stores local file path.
	private String fileName;
	private LocalDateTime timestamp;
	
	public Assignment(User deliveredBy, String courseCode, String assignmentName, LocalDateTime timestamp, String filename){
		this.setDeliveredBy(deliveredBy);
		this.setCourseCode(courseCode);
		this.setAssignmentName(assignmentName);
		this.setTimestamp(timestamp);
		this.setId(-1);
		this.fileName = filename;
	}
	
	public Assignment(User deliveredBy, String courseCode, String assignmentName, LocalDateTime timestamp, File file){
		this(deliveredBy,courseCode,assignmentName,timestamp,file == null ? null : file.getName());
		this.setFile(file);
	}
	
	public void downloadFile() {
		if(this.fileName != null)
			this.file = DBFile.downloadFile(this);
		else
			throw new IllegalStateException("According to local information, this assignment does not have a file associated to it");
	}
	
	public void openFile() {
		if (file == null) {
			throw new NullPointerException("Assignment does not have a file. Try downloading first");
		} else {
        //first check if Desktop is supported by Platform or not
	        if(!Desktop.isDesktopSupported()){
	            System.out.println("Desktop is not supported");
	            return;
	        }
	        
	        Desktop desktop = Desktop.getDesktop();
	        if(file.exists())
				try {
					desktop.open(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getStudentEmail() {
		return deliveredBy.getEmail();
	}
}