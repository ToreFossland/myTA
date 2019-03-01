/*
File: Metoder.java   
Date      Author
Feb 14 19 Kristian   
*/

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import user.User;

public class DBConnection {

	/*
	 * README
	 * 
	 * For aa koble til databasen maa man sette username, password og url i
	 * getConnectio-metoden. Funksjonene er basert på en database med folgende
	 * tabeller og attributter i () (nn) = not null / verdiene her kan ikke vaere
	 * tomme Course(int idCourse (nn),String CourseKode(nn),String CourseNavn)
	 * User(int idUser(nn), String email(nn), String Password(nn))
	 * User_has_Course(int idUser(nn),int idCourse(nn),String Rolle(nn))
	 */

	// TODO: add cache for storing data from frequently used DB queries
	

	
	

//lager connection til databasen som er noedvendig for aa manipulere den
	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://mysql.stud.ntnu.no:3306/davidaan_bookingsystem";
			String username = "fs_tdt4140_1_gruppe18";
			String password = "gruppe18";
			Class.forName(driver);

			Connection conn = DriverManager.getConnection(url, username, password);
			// System.out.println("Connected");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

//setter inn fag med courseCode og fagnavn. Naturlig admin-funksjon
	/*
	 * public static void leggInnCourse(String fagKodeInput, String fagNavnInput)
	 * throws Exception { try { Connection con = getConnection(); // gjoer
	 * courseCoden til store bokstaver String fagKode = fagKodeInput.toUpperCase();
	 * String fagNavn = fagNavnInput.toLowerCase();
	 * 
	 * // Legger inn fag med id,courseCode og fagnavn dersom det ikke allerede
	 * eksisterer if (fagEksisterer(fagKode) == false) { int idCourse =
	 * nyCourseId(); PreparedStatement leggInnCourse = con.
	 * prepareStatement("INSERT INTO Course (idCourse,CourseKode,CourseNavn) VALUES('"
	 * + idCourse + "','" + fagKode + "','" + fagNavn + "')");
	 * leggInnCourse.executeUpdate(); System.out.println("Lagt inn fag " + fagKode);
	 * } // Sjekker om courseCode mangler fagnavn og oppdaterer fagnavn dersom
	 * courseCode // eksisterer. else if (fagEksisterer(fagKode)) {
	 * PreparedStatement statement = con
	 * .prepareStatement("SELECT CourseNavn From Course WHERE CourseKode = '" +
	 * fagKode + "'"); ResultSet fagNavnNull = statement.executeQuery();
	 * fagNavnNull.next(); // System.out.println(fagNavnNull.next()); String
	 * fagNavnType = fagNavnNull.getString("CourseNavn");
	 * 
	 * if (fagNavnType == null) {
	 * 
	 * PreparedStatement leggInnNavn = con.prepareStatement(
	 * "UPDATE Course SET CourseNavn = '" + fagNavn + "' WHERE CourseKode = '" +
	 * fagKode + "'"); leggInnNavn.executeUpdate();
	 * 
	 * System.out.println("Oppdatert navn paa fag" + fagKode + " til " + fagNavn); }
	 * else { System.out.println("Course " + fagKode + " eksisterer"); }
	 * 
	 * } // Gir beskjed om at faget eksisterer else { System.out.println("Course " +
	 * fagKode + " eksisterer"); } } catch (Exception e) { System.out.println(e); }
	 * finally { // System.out.println("Insert Completed"); } }
	 */

//setter inn fag med courseCode. Naturlig admin-funksjon.
	/*
	 * public static void leggInnCourse(String fagKodeInput) throws Exception { try
	 * { Connection con = getConnection(); // gjoer courseCoden til store bokstaver
	 * String fagKode = fagKodeInput.toUpperCase();
	 * 
	 * // legger inn fag om det ikke eksisterer if (fagEksisterer(fagKode) == false)
	 * { int idCourse = nyCourseId(); PreparedStatement leggInnCourse = con
	 * .prepareStatement("INSERT INTO Course (idCourse,CourseKode) VALUES('" +
	 * idCourse + "','" + fagKode + "')"); leggInnCourse.executeUpdate();
	 * System.out.println("Lagt inn fag " + fagKode); } else {
	 * System.out.println("Course " + fagKode + " eksisterer."); } } catch
	 * (Exception e) { System.out.println(e); } finally { //
	 * System.out.println("Insert Completed"); } }
	 */

//sjekker om faget eksisterer i Coursetabellen
	public static boolean fagEksisterer(String fagkode) throws Exception {
		boolean eksisterer = false;
		String fagKode = fagkode.toUpperCase();
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT CourseKode FROM Course");

			ResultSet result = statement.executeQuery();

			while (result.next() && eksisterer == false) {
				if (Objects.equals(result.getString("CourseKode"), fagKode)) {
					eksisterer = true;
				}
			}
			// System.out.println(eksisterer);
		} catch (Exception e) {
			System.out.println(e);

		}
		return eksisterer;

	}

	// checks if element exists in specified column
	public static boolean elementExists(String table, String column, String searchFor) throws Exception {
		boolean exists = false;
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT " + column + " FROM " + table);

			ResultSet result = statement.executeQuery();

			while (result.next() && exists == false) {
				if (Objects.equals(result.getString(column), searchFor)) {
					exists = true;
				}
			}
			// System.out.println(eksisterer);
		} catch (Exception e) {
			System.out.println(e);

		}
		return exists;

	}

	public static boolean userExists(String Email) throws Exception {
		String email = Email.toLowerCase();
		return elementExists("User", "email", email);
	}

//sjekker om email eksisterer i bruker
	public static boolean emailEksisterer(String emailInput) throws Exception {
		boolean eksisterer = false;
		try {
			Connection con = getConnection();
			String email = emailInput.toLowerCase();
			
			PreparedStatement statement = con.prepareStatement("SELECT email FROM User");

			ResultSet result = statement.executeQuery();

			while (result.next() && eksisterer == false) {
				if (Objects.equals(result.getString("email"), email)) {
					eksisterer = true;
				}
			}
			// System.out.println(eksisterer);
		} catch (Exception e) {
			System.out.println(e);

		}
		return eksisterer;

	}

//sjekker om password matcher email. Naturlig godkjenning for innlogging.
// skiftet til email (primærnøkkel) i stedet
	public static boolean usernamePasswordMatch(String emailInput, String passwordInput) throws Exception {
		boolean match = false;
		try {
			Connection con = getConnection();
			String email = emailInput.toLowerCase();
			
			if (elementExists("User", "email", email)) {
				PreparedStatement statement = con
						.prepareStatement("SELECT password FROM User Where email ='" + email + "'");
				ResultSet password = statement.executeQuery();
				password.next();
				String fasit = password.getString("Password").toLowerCase();

				if (Objects.equals(fasit, passwordInput)) {
					match = true;
					System.out.println("Passwordet er korrekt.");
				} else {
					System.out.println("Passwordet er feil.");
				}

			} else {
				System.out.println("E-post eksisterer ikke");
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return match;

	}



	public static void registerUser(String emailInput, String password, String firstName,
			String lastName, boolean skipCheck) {
		try {
			Connection con = getConnection();
			String email = emailInput.toLowerCase();

			// Legger inn bruker med email, navn og password dersom email ikke
			// eksisterer
			if (skipCheck) {
				PreparedStatement userToDb = con.prepareStatement(String.format(
						"INSERT INTO User (email, password, firstName, lastName) VALUES('%s', '%s','%s','%s')",
						email, password, firstName, lastName));
				userToDb.executeUpdate();
			} else {
				if (emailEksisterer(email) == false) {

					PreparedStatement userToDb = con.prepareStatement(String.format(
							"INSERT INTO User (email, password, firstName, lastName) VALUES('%s', '%s','%s','%s')",
							email, password, firstName, lastName));
					userToDb.executeUpdate();
					System.out.println("Added user: " + email);
				}
				// Gir beskjed om at brukeren eksisterer
				else {
					System.out.println("The user " + email + " exists. ");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

//finner siste brukerIden i lista og plusser med 1. Dette gir ny, unik brukerId  //d�d metode as of now
	public static int nyUserId() throws Exception {

		int nyId = 0;
		try {
			Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement("SELECT * FROM User ORDER BY idUser DESC LIMIT 1");
			ResultSet forrigeId = statement.executeQuery();
			forrigeId.next();
			nyId = forrigeId.getInt("idUser") + 1;
			System.out.println(nyId);
		} catch (Exception e) {
			System.out.println(e);
		}
		return nyId;
	}

//Sjekker om en bruker med fag og rolle eksisterer i User_has_Course
	public static boolean brukerHarCourseEksisterer(String Email, String coursecode, int role) {
		boolean eksisterer = false;
		try {
			Connection con = getConnection();
			//setter det på riktig format. onsker username i lowercase og coursecode i uppercase
			String email = Email.toLowerCase();
			String courseCode = coursecode.toUpperCase();
			
			PreparedStatement findUsername = con.prepareStatement("SELECT User_email, Course_courseCode, role "
					+ "FROM User_has_Course ");
			//PreparedStatement findCourseCode = con.prepareStatement("SELECT Course_courseCode FROM User_has_Course");
			//PreparedStatement findRolle = con.prepareStatement("SELECT role FROM User_has_Course");
			ResultSet rs = findUsername.executeQuery();
			
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

		}
		//System.out.println(eksisterer);
		return eksisterer;
		

	}
	
	
	

//Legg til kobling i UsereHarCourse
	public static void leggTilUserHarCourse(String Email, String coursecode, int role) {
		try {
			Connection con = getConnection();
			String email = Email.toLowerCase();
			String courseCode = coursecode.toUpperCase();

			// legger inn kobling om det ikke eksisterer
			if (brukerHarCourseEksisterer(email, courseCode, role) == false) {

				PreparedStatement leggInnKobling = con
						.prepareStatement("INSERT INTO User_has_Course (User_email,Course_courseCode,role) VALUES('" + email + "','"
								+ courseCode + "','" + role + "')");
				leggInnKobling.executeUpdate();
				System.out.println("Lagt inn kobling til email " + email);
			} else {
				System.out.println("The course for " + email + " exists.");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			// System.out.println("Insert Completed");
		}

	}

	// collects all relevant information about user (used when loggin in).
	// Return Array and Dictionary of courses
	public static User returnUserObject(String email) {
		try {
			Connection con = getConnection();
			String query = String.format("SELECT * FROM User WHERE email = '%s'", email);
			PreparedStatement st = con.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			rs.next();

			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String password = rs.getString("password");
			

			query = String.format("SELECT Course_courseCode, role FROM User_has_Course WHERE User_email = '%s'",
					email);
			st = con.prepareStatement(query);
			rs = st.executeQuery();

			Map<String, Integer> coursesAndRoles = new HashMap<String, Integer>();
			while (rs.next()) {
				coursesAndRoles.put(rs.getString("Course_courseCode"), rs.getInt("role"));
			}

			return User.generateUserObject(firstName, lastName, email, password, coursesAndRoles);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	//Obtains highest permission that the user has. Assumes it's a student if no permissions has been set in the database (returns 1)
	/*public static int getUserPermission(String username) {
		try {
			PreparedStatement findRole = con.prepareStatement("SELECT DISTINCT role FROM User_has_Course WHERE User_username = %s");
			ResultSet roles = findRole.executeQuery();
			
			int permission = 1;
			while(roles.next()) {
				permission = roles.getInt("role") > permission ? roles.getInt("role") : permission;
			}
			
			return permission;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 1;
	}*/

	public static void main(String[] args) {
		leggTilUserHarCourse("abc@ntnu.no", "tdt4145",3);
	}
}
