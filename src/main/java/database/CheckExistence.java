package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class CheckExistence extends DBConnection {
	// sjekker om faget eksisterer i Coursetabellen
	public boolean course(String courseCode) throws Exception {
		boolean eksisterer = false;
		courseCode = courseCode.toUpperCase();
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT CourseKode FROM Course");

			ResultSet result = statement.executeQuery();

			while (result.next() && eksisterer == false) {
				if (Objects.equals(result.getString("CourseKode"), courseCode)) {
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
	public boolean element(String table, String column, String searchFor) throws Exception {
		boolean exists = false;
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT " + column + " FROM " + table);

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

	public boolean user(String email) throws Exception {
		email = email.toLowerCase();
		return element("User", "email", email);
	}

	// sjekker om email eksisterer i bruker
	public boolean email(String emailInput) throws Exception {
		boolean eksisterer = false;
		try {
			String email = emailInput.toLowerCase();

			PreparedStatement statement = conn.prepareStatement("SELECT email FROM User");

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

	// Sjekker om en bruker med fag og rolle eksisterer i User_has_Course
	public boolean userHasCourse(String email, String coursecode, int role) {
		boolean eksisterer = false;
		try {
			// setter det på riktig format. onsker username i lowercase og coursecode i
			// uppercase
			email = email.toLowerCase();
			String courseCode = coursecode.toUpperCase();

			PreparedStatement findEmail = conn
					.prepareStatement("SELECT User_email, Course_courseCode, role " + "FROM User_has_Course ");
			// PreparedStatement findCourseCode = con.prepareStatement("SELECT
			// Course_courseCode FROM User_has_Course");
			// PreparedStatement findRolle = con.prepareStatement("SELECT role FROM
			// User_has_Course");
			ResultSet rs = findEmail.executeQuery();

			// ResultSet idCourse = finnIdCourse.executeQuery();
			// ResultSet role = finnRolle.executeQuery();

			while (rs.next() && eksisterer == false) {
				if (Objects.equals(rs.getString("User_email"), email)
						&& Objects.equals(rs.getString("Course_courseCode"), courseCode)
						&& Objects.equals(rs.getInt("role"), role)) {
					eksisterer = true;
				}
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		// System.out.println(eksisterer);
		return eksisterer;

	}

	// sjekker om password matcher email. Naturlig godkjenning for innlogging.
	// David: skiftet til email (primærnøkkel) i stedet
	public boolean usernamePasswordMatch(String emailInput, String passwordInput) throws Exception {
		boolean match = false;
		try {
			String email = emailInput.toLowerCase();

			PreparedStatement statement = conn
					.prepareStatement("SELECT password FROM User Where email ='" + email + "'");
			ResultSet password = statement.executeQuery();
			// If triggered if it didn't find user
			if (!password.first())
				System.out.println("User not found");
			else {
				System.out.println("User found");
				String fasit = password.getString("password").toLowerCase();;

				if (Objects.equals(fasit, passwordInput)) {
					match = true;
					System.out.println("Correct password.");
				} else {
					System.out.println("Wrong password.");
				}
			}

		} catch (Exception e) {
			System.out.println(e);

		}
		return match;

	}
}
