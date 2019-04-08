/*
File: Metoder.java   
Date      Author
Feb 14 19 Kristian   
*/

package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.dbcp2.BasicDataSource;

import gui.App;
import user.User;

/* 
 * Database communication related to user and courses. 
 * Some things are written in Norwegian since everyone weren't aware that we were writing in English
 */
public class DBConnection {

//sjekker om faget eksisterer i Coursetabellen
	public static boolean fagEksisterer(String fagkode) throws Exception {
		Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;
		
		boolean eksisterer = false;
		String fagKode = fagkode.toUpperCase();
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
            con = bds.getConnection();
			statement = con.prepareStatement("SELECT courseCode FROM Course");

			result = statement.executeQuery();

			while (result.next() && eksisterer == false) {
				if (Objects.equals(result.getString("courseCode"), fagKode)) {
					eksisterer = true;
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
		return eksisterer;
	}
	

	// Legg til fag
	public static void leggTilCourse(String courseCode, String navn, String description) {

		Connection con = null;
        PreparedStatement statement = null;
		
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
            con = bds.getConnection();

			// legger inn kobling om det ikke eksisterer

			if (fagEksisterer(courseCode) == false) {

				statement = con
						.prepareStatement("INSERT INTO Course (courseCode,name,description) VALUES('" + courseCode
								+ "','" + navn + "','" + description + "')");
				statement.executeUpdate();
			} else {
				System.out.println("Course " + courseCode + " eksisterer.");
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}

	}

	// Legg til fag med kode og navn
	public static void leggTilCourse(String courseCode, String navn) {
		leggTilCourse(courseCode,navn,null);

	}

	// Legg til fag med kode
	public static void leggTilCourse(String courseCode) {
			leggTilCourse(courseCode, null, null);
		}	

	

	// checks if element exists in specified column
	public static boolean elementExists(String table, String column, String searchFor) throws Exception {
		Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        
		
		boolean exists = false;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
	        con = bds.getConnection();
	        
			statement = con.prepareStatement("SELECT " + column + " FROM " + table);

			result = statement.executeQuery();

			while (result.next() && exists == false) {
				if (Objects.equals(result.getString(column), searchFor)) {
					exists = true;
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
		return exists;

	}

	public static boolean userExists(String Email) throws Exception {
		String email = Email.toLowerCase();
		return elementExists("User", "email", email);
	}

	public static boolean emailEksisterer(String emailInput) throws Exception {
		return userExists(emailInput);

	}

//sjekker om password matcher email. Naturlig godkjenning for innlogging.
// skiftet til email (primærnøkkel) i stedet
	public static boolean usernamePasswordMatch(String emailInput, String passwordInput) throws Exception {
		Connection con = null;
        PreparedStatement statement = null;
        ResultSet result = null;
		
		boolean match = false;
		try {
	        BasicDataSource bds = DataSource.getInstance().getBds();
	        con = bds.getConnection();
			String email = emailInput.toLowerCase();
			
			if (elementExists("User", "email", email)) {
				statement = con
						.prepareStatement("SELECT password FROM User Where email ='" + email + "'");
				result = statement.executeQuery();
				result.next();
				String fasit = result.getString("Password").toLowerCase();

				if (Objects.equals(fasit, passwordInput)) {
					match = true;
					Logger.getLogger(App.class.getName()).log(Level.INFO, "Correct password");
				} else {
					Logger.getLogger(App.class.getName()).log(Level.INFO, "Incorrect password");
				}

			} else {
				Logger.getLogger(App.class.getName()).log(Level.INFO, "Email doesn't exist");
			}

		} catch (Exception e) {
			System.out.println(e);

		} finally {
	        try { if (result != null) result.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		return match;

	}



	public static void registerUser(String emailInput, String password, String firstName,
			String lastName, boolean skipCheck) {
		
		Connection con = null;
        PreparedStatement statement = null;
		
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
	        con = bds.getConnection();
			String email = emailInput.toLowerCase();

			// Legger inn bruker med email, navn og password dersom email ikke
			// eksisterer
			if (skipCheck) {
				insertUser(password, firstName, lastName, con, email);
			} else {
				if (emailEksisterer(email) == false) {

					insertUser(password, firstName, lastName, con, email);
					System.out.println("Added user: " + email);
				}
				else {
					System.out.println("The user " + email + " exists. ");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
	}


	private static void insertUser(String password, String firstName, String lastName, Connection con, String email)
			throws SQLException {
		PreparedStatement statement;
		statement = con.prepareStatement(String.format(
				"INSERT INTO User (email, password, firstName, lastName) VALUES('%s', '%s','%s','%s')",
				email, password, firstName, lastName));
		statement.executeUpdate();
	}

//Sjekker om en bruker med fag og rolle eksisterer i User_has_Course
	public static boolean brukerHarCourseEksisterer(String Email, String coursecode, int role) {
		Connection con = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
		
		boolean eksisterer = false;
		try {
			BasicDataSource bds = DataSource.getInstance().getBds();
	        con = bds.getConnection();
			//setter det på riktig format. onsker username i lowercase og coursecode i uppercase
			String email = Email.toLowerCase();
			String courseCode = coursecode.toUpperCase();
			
			statement = con.prepareStatement("SELECT User_email, Course_courseCode, role "
					+ "FROM User_has_Course ");
			//PreparedStatement findCourseCode = con.prepareStatement("SELECT Course_courseCode FROM User_has_Course");
			//PreparedStatement findRolle = con.prepareStatement("SELECT role FROM User_has_Course");
			rs = statement.executeQuery();
			
			// ResultSet idCourse = finnIdCourse.executeQuery();
			// ResultSet role = finnRolle.executeQuery();
			
			

			while (rs.next() && eksisterer == false) {
				if (Objects.equals(rs.getString("User_email"), email) && Objects.equals(rs.getString("Course_courseCode"), courseCode) && Objects.equals(rs.getInt("role"),role)) {
					eksisterer = true;
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);

		} finally {
	        try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (statement != null) statement.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		//System.out.println(eksisterer);
		return eksisterer;
		

	}
	
	
	

//Legg til kobling i UsereHarCourse
	public static void leggTilUserHarCourse(String Email, String coursecode, int role) {
		Connection con = null;
        PreparedStatement addRelation = null;
		
		try {
	        BasicDataSource bds = DataSource.getInstance().getBds();
	        con = bds.getConnection();
			String email = Email.toLowerCase();
			String courseCode = coursecode.toUpperCase();

			// legger inn kobling om det ikke eksisterer
			if (brukerHarCourseEksisterer(email, courseCode, role) == false) {

				addRelation = con
						.prepareStatement("REPLACE INTO User_has_Course (User_email,Course_courseCode,role) VALUES('" + email + "','"
								+ courseCode + "','" + role + "')");
				addRelation.executeUpdate();
				System.out.println("Lagt inn kobling til email " + email);
			} else {
				System.out.println("The course for " + email + " exists.");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		    try { if (addRelation != null) addRelation.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}

	}

	// collects all relevant information about user (used when loggin in).
	// Return Array and Dictionary of courses
	public static User returnUserObject(String email) {
		Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
		
             		
		try {
	        BasicDataSource bds = DataSource.getInstance().getBds();
	        con = bds.getConnection();
			String query = String.format("SELECT * FROM User WHERE email = '%s'", email);
			st = con.prepareStatement(query);
			rs = st.executeQuery();
			rs.next();

			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			query = String.format("SELECT Course_courseCode, role FROM User_has_Course WHERE User_email = '%s'",
					email);
			st.close();
			rs.close();
			
			st = con.prepareStatement(query);
			rs = st.executeQuery();

			Map<String, Integer> coursesAndRoles = new HashMap<String, Integer>();
			while (rs.next()) {
				coursesAndRoles.put(rs.getString("Course_courseCode"), rs.getInt("role"));
			}

			return User.generateUserObject(email, firstName, lastName, coursesAndRoles);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        try { if (rs != null) rs.close(); } catch (Exception e) {};
		    try { if (st != null) st.close(); } catch (Exception e) {};
		    try { if (con != null) con.close(); } catch (Exception e) {};
		}
		return null;
	}
}
