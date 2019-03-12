/*
File: DBBooking.java       
Date 	 	Author 	Changes
--------------------------------------------
March 12 19 Tore 	Created
*/


package evaluation;

import java.time.LocalDateTime;

import user.User;

public class Evaluation{
	private int id, score;
	private String courseCode, note;
	private User evaluator;
	private Assignment assignment;
	private LocalDateTime timestamp;
	
	public Evaluation(String courseCode, int score, User evaluator, Assignment assignment){
		this.setCourseCode(courseCode);
		this.setScore(score);
		this.setEvaluator(evaluator);
		this.setAssignment(assignment);
		this.setTimestamp(LocalDateTime.now());
	}
	
	public Evaluation(String courseCode, int score, User evaluator, Assignment assignment, String note){
		this.setCourseCode(courseCode);
		this.setScore(score);
		this.setEvaluator(evaluator);
		this.setAssignment(assignment);
		this.setTimestamp(LocalDateTime.now());
		this.setNote(note);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public User getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(User evaluator) {
		this.evaluator = evaluator;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}