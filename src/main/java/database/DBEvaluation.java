package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.dbcp2.BasicDataSource;

import evaluation.Assignment;
import evaluation.Evaluation;
import user.User;

public class DBEvaluation{
	
	public static void updateAssignment(Assignment assignment) {
		Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
            con = bds.getConnection();
			statement = con.prepareStatement("SELECT idAssignment, title FROM Assignment");
			result = statement.executeQuery();
			while (result.next()) {
				if (Objects.equals(result.getString("title"), assignment.getAssignmentName())) {
					assignment.setId(result.getInt("idAssignment"));
				}
			}
			// System.out.println(eksisterer);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
	}
	
	public static void insertAssignment(Assignment assignment) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
		    con = bds.getConnection();
		    statement = con.prepareStatement(String.format("REPLACE INTO Assignment(title, timestamp, Student_email, courseCode) "
		    		+ "VALUES('%s', '%s', '%s', '%s')", assignment.getAssignmentName(), LocalDateTime.now(), 
		    		assignment.getDeliveredBy().getEmail(), assignment.getCourseCode()));
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
	}
	
	public static ArrayList<Assignment> getAssignments(String course){
		
		ArrayList<Assignment> assignments = new ArrayList<Assignment>();
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
		    con = bds.getConnection();
		    statement = con.prepareStatement(String.format("SELECT * FROM Assignment WHERE idAssignment NOT IN ("
		    		+ "SELECT Assignment_idAssignment FROM Evaluation) AND courseCode = '%s' ", course));
		    result = statement.executeQuery();
		    while (result.next()) {
		    	String title = result.getString("title");
		    	//String filepath = result.getString("filePath"); kommer neste sprint
		    	LocalDateTime timestamp = result.getTimestamp("timestamp").toLocalDateTime();
		    	String studentEmail = result.getString("Student_email");
		    	User student = User.generateUserObject(studentEmail);		    	
			    Assignment assignment = new Assignment(student, course, title, timestamp);
			    assignments.add(assignment);
		    }
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		return assignments; 
	}
	
	public static void insertEvaluation(Evaluation evaluation) {
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
		    con = bds.getConnection();
		    evaluation.getAssignment().updateId();
		    System.out.println(evaluation.getAssignment().getId());
		    //System.out.println(evaluation.getScore() + " " + evaluation.getNote() + " " + evaluation.getAssignment().getId() + " " + evaluation.getAssignment().getDeliveredBy().getEmail());
		    System.out.println(evaluation.getScore() + " " + evaluation.getNote() + " " + evaluation.getAssignment().getId() + " " + evaluation.getEvaluator() + " " + evaluation.getCourseCode());
		    statement = con.prepareStatement(String.format("REPLACE INTO Evaluation(score, note, Assignment_idAssignment, "
					+ "TA_email, courseCode) VALUES('%s', '%s','%s','%s','%s')",evaluation.getScore(), evaluation.getNote(), evaluation.getAssignment().getId(), evaluation.getEvaluator().getEmail(), evaluation.getCourseCode()));
			statement.execute();
			// System.out.println(eksisterer);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
}
	
	public static Evaluation getEvaluation(String course, User student) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Evaluation evaluation = null;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
		    con = bds.getConnection();
		    statement = con.prepareStatement(String.format("SELECT * FROM Evaluation INNER JOIN Assignment ON "
		    		+ "Evaluation.Assignment_idAssignment = Assignment.idAssignment WHERE Evaluation.courseCode = '%s' "
		    		+ "AND Student_email = '%s'", course, student.getEmail()));
		    result = statement.executeQuery();
		    while (result.next()) {
		    	String title = result.getString("title");
		    	//String filepath = result.getString("filePath"); kommer neste sprint
		    	LocalDateTime timestamp = result.getTimestamp("timestamp").toLocalDateTime();
		    	int score = result.getInt("score");
		    	String note = result.getString("note");
		    	String TaEmail = result.getString("TA_email");
		    	User evaluator = User.generateUserObject(TaEmail);
		    	
			    Assignment assignment = new Assignment(student, course, title, timestamp);
		    	Evaluation eval = new Evaluation(course, score, evaluator, assignment, note);
		    	evaluation = eval;
		    }
		}catch (Exception e) {
			System.out.println(e);
		
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		return evaluation; 
	}
	
	public static Evaluation getEvaluation(int id) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Evaluation evaluation = null;

		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
		    con = bds.getConnection();
		    statement = con.prepareStatement(String.format("SELECT * FROM Evaluation INNER JOIN Assignment ON "
		    		+ "Evaluation.Assignment_idAssignment = Assignment.idAssignment WHERE idEvaluation = '%s'", id));
		    result = statement.executeQuery();
	
		    while (result.next()) {
		    	String title = result.getString("title");
		    	//String filepath = result.getString("filePath"); kommer neste sprint
		    	LocalDateTime timestamp = result.getTimestamp("timestamp").toLocalDateTime();
		    	int score = result.getInt("score");
		    	String note = result.getString("note");
		    	String courseCode = result.getString("courseCode");
		     	String TaEmail = result.getString("TA_email");
		    	String studentEmail = result.getString("Student_email");
		    	
		    	User evaluator = User.generateUserObject(TaEmail);
		    	User student = User.generateUserObject(studentEmail);
		    	
			    Assignment assignment = new Assignment(student, courseCode, title, timestamp);
		    	Evaluation eval = new Evaluation(courseCode, score, evaluator, assignment, note);
		    	evaluation = eval;
		    }
		}catch (Exception e) {
			System.out.println(e);
		
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		return evaluation; 
	}
	
	public static ArrayList<Evaluation> getEvaluations(String course) {
		ArrayList<Evaluation> evaluations = new ArrayList<Evaluation>();
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
		    con = bds.getConnection();
		    statement = con.prepareStatement(String.format("SELECT * FROM Evaluation INNER JOIN Assignment ON "
		    		+ "Evaluation.Assignment_idAssignment = Assignment.idAssignment WHERE courseCode = '%s'", course));
		    result = statement.executeQuery();
		    while (result.next()) {
		    	String title = result.getString("title");
		    	//String filepath = result.getString("filePath"); kommer neste sprint
		    	LocalDateTime timestamp = result.getTimestamp("timestamp").toLocalDateTime();
		    	int score = result.getInt("score");
		    	String note = result.getString("note");
		    	String TaEmail = result.getString("TA_email");
		    	String studentEmail = result.getString("Student_email");
		    	
		    	User student = User.generateUserObject(studentEmail);
		    	User TA = User.generateUserObject(TaEmail);
		    	
			    Assignment assignment = new Assignment(student, course, title, timestamp);
		    	Evaluation evaluation = new Evaluation(course, score, TA, assignment, note);
		    	evaluations.add(evaluation);
		    } 
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		return evaluations; 
		 
	}
	
	public static void updateEvaluation(int id, int score) {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
		    con = bds.getConnection();
		    statement = con.prepareStatement(String.format("UPDATE Evaluation SET score = '%s' "
		    		+ "WHERE idEvaluation = %s", score, id));
			statement.executeUpdate();
			// System.out.println(eksisterer);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
	}
	
	public static void main(String[] args) {
		User user = User.generateUserObject("abc@ntnu.no");
		User user2 = User.generateUserObject("hei@ntnu.no");
		Assignment assignment = new Assignment(user, "TDT4100", "Tittel", LocalDateTime.of(2019, Month.MARCH, 1, 8, 00));
		Evaluation eval = new Evaluation("TDT4100", 0, user, assignment, "hei");
		// insertEvaluation(eval);
		ArrayList<Evaluation> evaluations = new ArrayList<Evaluation>();
		//evals.add(eval);
		//insertAssignment(assignment);
		//System.out.println(getEvaluations("TDT4100"));
		System.out.println(getEvaluation("TDT4100", user));
		//System.out.println(getEvaluation(10));
		//updateEvaluation(10, 10);
		//System.out.println(getAssignments("TDT4100"));
		//insertEvaluation(eval);
	}
}