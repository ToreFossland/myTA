package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddEntry extends DBConnection {
	
	// Legg til kobling i UsereHarCourse
	public void userHasCourse(String email, String coursecode, int role) {
			email = email.toLowerCase();
			String courseCode = coursecode.toUpperCase();

			try {
				PreparedStatement leggInnKobling = conn
						.prepareStatement("REPLACE INTO User_has_Course (User_email,Course_courseCode,role) VALUES('"
								+ email + "','" + courseCode + "','" + role + "')");
				leggInnKobling.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("Lagt inn kobling til brukerId " + email);
	}
	
	//registrerer bruker uten krav til email
		public void registerUser(String emailInput, String password, String userName, String firstName, String lastName) {
			registerUser(emailInput, password, firstName, lastName, false);
		}

		public void registerUser(String emailInput, String password, String firstName, String lastName, boolean skipCheck) {
			try {
				String email = emailInput.toLowerCase();

				// Legger inn bruker med email, username, navn og password dersom email ikke
				// eksisterer
				if (skipCheck) {
					PreparedStatement userToDb = conn.prepareStatement(String.format(
							"REPLACE INTO User (email, password, firstName, lastName) VALUES('%s', '%s', '%s','%s','%s')",
							email, password, firstName, lastName));
					userToDb.executeUpdate();
				} else {
					
					PreparedStatement statement = conn
							.prepareStatement("SELECT * FROM User Where email ='" + email + "'");
					ResultSet rs = statement.executeQuery();
					// If triggered if it didn't find user
					if (!rs.first())
						{

						PreparedStatement userToDb = conn.prepareStatement(String.format(
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
}
