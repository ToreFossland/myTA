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
			
		    statement = con.prepareStatement(String.format("INSERT INTO Evaluation(score, note, Assignment_idAssignment, "
					+ "User_email, courseCode) VALUES('%s', '%s','%s','%s','%s')",evaluation.getScore(), evaluation.getNote(), evaluation.getAssignment().getId(), evaluation.getAssignment().getDeliveredBy().getEmail(), evaluation.getCourseCode()));
		
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
		Map<String, Integer> coursesAndRoles  = new HashMap<String, Integer>();
		User user = User.generateUserObject("abc@ntnu.no", "hei", "hei", coursesAndRoles);
		Assignment assignment = new Assignment(user, "TDT4100", "Tittel", LocalDateTime.of(2019, Month.MARCH, 1, 8, 00));
		Evaluation eval = new Evaluation("TDT4100", 0, user, assignment, "hei");
		// insertEvaluation(eval);
		HashMap<String, ArrayList<Evaluation>> evaluations = new HashMap<String, ArrayList<Evaluation>>();
		getEvaluations(evaluations, "TDT4100");
		
	}
	
	public static void getEvaluations(HashMap<String, ArrayList<Evaluation>> evaluations, String course) {
		
		HashMap<String, ArrayList<Evaluation>> evaluationsInDB = new HashMap<String, ArrayList<Evaluation>>();
		
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
		    con = bds.getConnection();
		    statement = con.prepareStatement(String.format("SELECT * FROM Evaluation WHERE courseCode = '%s'", course));
		    result = statement.executeQuery();
		    while (result.next()) {
		    System.out.println(result.getString("courseCode"));
		    System.out.println(result.getString("User_email"));
		    }
		    con.close();
		    
		} catch (Exception e) {
			System.out.println(e);
		
		} finally {
		    try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		} 
		 
	}
}