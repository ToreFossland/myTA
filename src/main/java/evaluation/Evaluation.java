/*
File: DBBooking.java       
Date 	 	Author 	Changes
--------------------------------------------
March 12 19 Tore 	Created
*/


package evaluation;

import java.time.LocalDateTime;

import user.User;

public class Evaluation implements Comparable<Evaluation>{
	private Integer id, score;
	private String note;
	private User evaluator;
	private Assignment assignment;
	private LocalDateTime timestamp;
	
	public Evaluation(Integer score, User evaluator, Assignment assignment){
		this.setScore(score);
		this.setEvaluator(evaluator);
		this.setAssignment(assignment);
		this.setTimestamp(LocalDateTime.now());
	}
	
	public Evaluation(Integer score, User evaluator, Assignment assignment, String note){
		this.setScore(score);
		this.setEvaluator(evaluator);
		this.setAssignment(assignment);
		this.setTimestamp(LocalDateTime.now());
		this.setNote(note);
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getCourseCode() {
		return getAssignment().getCourseCode();
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
	
	//Used by StudentViewAssignmentsPageController
	public String getAssignmentName() {
		return assignment.getAssignmentName();
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
	
	@Override
	public int compareTo(Evaluation evaluation) {
		if(evaluation.getTimestamp().isBefore(this.getTimestamp()))
			return -1;
		else if (evaluation.getTimestamp().isAfter(this.getTimestamp()))
			return 1;
		else
			return 0;
	}

}