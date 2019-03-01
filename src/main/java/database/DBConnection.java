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

public abstract class DBConnection {

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

	protected static Connection conn;

//lager connection til databasen som er noedvendig for aa manipulere den
	public void getConnection() throws Exception {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://mysql.stud.ntnu.no:3306/davidaan_bookingsystem";
			String username = "fs_tdt4140_1_gruppe18";
			String password = "gruppe18";
			Class.forName(driver);

			conn = DriverManager.getConnection(url, username, password);
			// System.out.println("Connected");
		} catch (Exception e) {
			System.out.println(e);
		}
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

//finner siste brukerIden i lista og plusser med 1. Dette gir ny, unik brukerId  //d�d metode as of now
	/*
	 * public static int nyUserId() throws Exception {
	 * 
	 * int nyId = 0; try { Connection con = getConnection(); PreparedStatement
	 * statement =
	 * con.prepareStatement("SELECT * FROM User ORDER BY idUser DESC LIMIT 1");
	 * ResultSet forrigeId = statement.executeQuery(); forrigeId.next(); nyId =
	 * forrigeId.getInt("idUser") + 1; System.out.println(nyId); } catch (Exception
	 * e) { System.out.println(e); } return nyId; }
	 */

}
