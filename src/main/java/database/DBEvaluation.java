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
				if (Objects.equals(result.getString("title"), assignment.getAssigmentName())) {
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
			
		    statement = con.prepareStatement(String.format("REPLACE INTO Evaluation(score, note, Assignment_idAssignment, "
					+ "TA_email) VALUES('%s', '%s','%s','%s','%s')",evaluation.getScore(), evaluation.getNote(), evaluation.getAssignment().getId(), evaluation.getEvaluator()));
		
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
	
	
	public static HashMap<String, ArrayList<Evaluation>> getEvaluations(String course) {
		
		HashMap<String, ArrayList<Evaluation>> evaluations = new HashMap<String, ArrayList<Evaluation>>();
		ArrayList<Evaluation> evaluationsInDB = new ArrayList<Evaluation>();
		
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
		    	int assignmentID = result.getInt("idAssignment");
		    	String title = result.getString("title");
		    	//String filepath = result.getString("filePath"); kommer neste sprint
		    	LocalDateTime timestamp = result.getTimestamp("timestamp").toLocalDateTime();
		    	int id = result.getInt("idEvaluation");
		    	int score = result.getInt("score");
		    	String note = result.getString("note");
		    	int idAssignment = result.getInt("Assignment_idAssignment");
		    	String TaEmail = result.getString("TA_email");
		    	String studentEmail = result.getString("Student_email");
		    	
		    	
		    	User student = User.generateUserObject(studentEmail);
		    	User TA = User.generateUserObject(TaEmail);
		    	
			    Assignment assignment = new Assignment(student, course, title, timestamp);
		    	Evaluation evaluation = new Evaluation(course, score, TA, assignment, note);
		    	
		    	evaluationsInDB.add(evaluation);
		    }
		    
		   
		    
		} catch (Exception e) {
			System.out.println(e);
		
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		 evaluations.put(course, evaluationsInDB);
		return evaluations; 
		 
	}
	
	public static void main(String[] args) {
		User user = User.generateUserObject("abc@ntnu.no");
		Assignment assignment = new Assignment(user, "TDT4100", "Tittel", LocalDateTime.of(2019, Month.MARCH, 1, 8, 00));
		Evaluation eval = new Evaluation("TDT4100", 0, user, assignment, "hei");
		// insertEvaluation(eval);
		HashMap<String, ArrayList<Evaluation>> evaluations = new HashMap<String, ArrayList<Evaluation>>();
		ArrayList<Evaluation> evals = new ArrayList<Evaluation>();
		evals.add(eval);
	
		System.out.println(getEvaluations("TDT4100"));
		
		
		
	}
	
	
	
	
	
	
	
}