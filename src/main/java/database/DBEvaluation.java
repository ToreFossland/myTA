package database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.dbcp2.BasicDataSource;

import evaluation.Assignment;
import evaluation.Evaluation;
import gui.App;
import user.User;

//Database communication related to evaluation/assignment
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
	
	public static void insertAssignment(Assignment assignment) throws Exception {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
		    con = bds.getConnection();
		    
		    FileInputStream fileInput = assignment.getFile() == null ? null : new FileInputStream(assignment.getFile());
		    String fileName = assignment.getFile() == null ? null : assignment.getFile().getName();
		    
		    statement = con.prepareStatement("REPLACE INTO Assignment(title, timestamp, Student_email, courseCode, file, fileName) "
		    		+ "VALUES(?, ?, ?, ?, ?, ?)");
		    statement.setString(1, assignment.getAssignmentName());
		    statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
		    statement.setString(3, assignment.getDeliveredBy().getEmail());
		    statement.setString(4, assignment.getCourseCode());
		    statement.setBinaryStream(5, fileInput);
		    statement.setString(6, fileName);
			statement.executeUpdate();
		} catch (Exception e) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, "Could not insert assignment");
			throw new Exception();
		} finally {
			Logger.getLogger(App.class.getName()).log(Level.INFO, "Assignment inserted");
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
	}
	
	//Files are downloaded separately when needed by calling assignment.downloadFile()
	public static ArrayList<Assignment> getAssignments(String course){
		
		ArrayList<Assignment> assignments = new ArrayList<Assignment>();
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
		    con = bds.getConnection();
		    statement = con.prepareStatement(String.format("SELECT idAssignment, title, timestamp, Student_email, fileName FROM Assignment WHERE idAssignment NOT IN ("
		    		+ "SELECT Assignment_idAssignment FROM Evaluation) AND courseCode = '%s' ", course));
		    result = statement.executeQuery();
		    while (result.next()) {
		    	String title = result.getString("title");
		    	//String filepath = result.getString("filePath"); kommer neste sprint
		    	LocalDateTime timestamp = result.getTimestamp("timestamp").toLocalDateTime();
		    	String studentEmail = result.getString("Student_email");
		    	User student = User.generateUserObject(studentEmail);		
		    	String filename = result.getString("fileName");
			    Assignment assignment = new Assignment(student, course, title, timestamp, filename);
			    assignment.setId(result.getInt("idAssignment"));
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
		    //System.out.println(evaluation.getScore() + " " + evaluation.getNote() + " " + evaluation.getAssignment().getId() + " " + evaluation.getAssignment().getDeliveredBy().getEmail());
		    statement = con.prepareStatement(String.format("REPLACE INTO Evaluation(score, note, Assignment_idAssignment, "
					+ "TA_email) VALUES('%s', '%s','%s','%s')",evaluation.getScore(), evaluation.getNote(), evaluation.getAssignment().getId(), evaluation.getEvaluator().getEmail()));
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
		    	String filename = result.getString("fileName");
		    	
			    Assignment assignment = new Assignment(student, course, title, timestamp, filename);
		    	Evaluation eval = new Evaluation(score, evaluator, assignment, note);
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
		    	String filename = result.getString("fileName");
		    	
		    	User evaluator = User.generateUserObject(TaEmail);
		    	User student = User.generateUserObject(studentEmail);
		    	
			    Assignment assignment = new Assignment(student, courseCode, title, timestamp, filename);
		    	Evaluation eval = new Evaluation(score, evaluator, assignment, note);
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
		    	String filename = result.getString("fileName");
		    	
		    	User student = User.generateUserObject(studentEmail);
		    	User TA = User.generateUserObject(TaEmail);
		    	
			    Assignment assignment = new Assignment(student, course, title, timestamp, filename);
		    	Evaluation evaluation = new Evaluation(score, TA, assignment, note);
		    	
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
}